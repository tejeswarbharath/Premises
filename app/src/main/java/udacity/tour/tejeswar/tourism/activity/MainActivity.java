package udacity.tour.tejeswar.tourism.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import udacity.tour.tejeswar.tourism.R;
import udacity.tour.tejeswar.tourism.data.LocationsContentProvider;
import udacity.tour.tejeswar.tourism.data.LocationsDB;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>

{

    GoogleMap googleMap;

    private static final String TAG = MainActivity.class.getSimpleName();

    private void init()
    {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        inflater.inflate(R.layout.activity_result, this);

    }

    @Override

    protected void onCreate(Bundle savedInstanceState)

    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Getting Google Play availability status

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status

        if(status!=ConnectionResult.SUCCESS)

        {

            int requestCode = 10;

            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);

            dialog.show();

        }

        else

        {

            // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml

            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            googleMap = fm.getMap();

            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED)
            {

                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            }

            else

            {

                Log.d(TAG, "error: %%%%%%%%%%%%%%%" + R.string.error_permission_map);

            }

            getSupportLoaderManager().initLoader(0, null, this);

            handleIntent(getIntent());

        }

        googleMap.setOnMapClickListener(new OnMapClickListener()

        {

            @Override

            public void onMapClick(LatLng point)

            {

                // Drawing marker on the map
                drawMarker(point);

                // Creating an instance of ContentValues
                ContentValues contentValues = new ContentValues();

                // Setting latitude in ContentValues
                contentValues.put(LocationsDB.FIELD_LAT, point.latitude);

                // Setting longitude in ContentValues
                contentValues.put(LocationsDB.FIELD_LNG, point.longitude);

                // Setting zoom in ContentValues
                contentValues.put(LocationsDB.FIELD_ZOOM, googleMap.getCameraPosition().zoom);

                // Creating an instance of LocationInsertTask
                LocationInsertTask insertTask = new LocationInsertTask();

                // Storing the latitude, longitude and zoom level to SQLite database
                insertTask.execute(contentValues);

                Toast.makeText(getBaseContext(), "Marker is added to the Map" , Toast.LENGTH_SHORT).show();

            }

        });

        googleMap.setOnMapLongClickListener(new OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng point) {

                // Removing all markers from the Google Map
                googleMap.clear();

                // Creating an instance of LocationDeleteTask
                LocationDeleteTask deleteTask = new LocationDeleteTask();

                // Deleting all the rows from SQLite database table
                deleteTask.execute();

                Toast.makeText(getBaseContext(), "All markers are removed", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void handleIntent(Intent intent)

    {

        if(intent.getAction().equals(Intent.ACTION_SEARCH))
        {

            doSearch(intent.getStringExtra(SearchManager.QUERY));

        }

        else if(intent.getAction().equals(Intent.ACTION_VIEW))

        {

            getPlace(intent.getStringExtra(SearchManager.EXTRA_DATA_KEY));

        }

    }

    @Override
    protected void onNewIntent(Intent intent)

    {

        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);

    }

    private void doSearch(String query)
    {

        Bundle data = new Bundle();
        data.putString("query", query);
        getSupportLoaderManager().restartLoader(0, data, this);

    }

    private void getPlace(String query)
    {

        Bundle data = new Bundle();
        data.putString("query", query);
        getSupportLoaderManager().restartLoader(1, data, this);

    }


    private void drawMarker(LatLng point)

    {

        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        googleMap.addMarker(markerOptions);

    }

    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void>

    {

        @Override
        protected Void doInBackground(ContentValues... contentValues)
        {

            /** Setting up values to insert the clicked location into SQLite database */

            getContentResolver().insert(LocationsContentProvider.BASE_SEARCH_URI, contentValues[0]);

            return null;

        }

    }

    private class LocationDeleteTask extends AsyncTask<Void, Void, Void>

    {

        @Override
        protected Void doInBackground(Void... params)

        {

            /** Deleting all the locations stored in SQLite database */

            getContentResolver().delete(LocationsContentProvider.BASE_SEARCH_URI, null, null);

            return null;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)

    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.Action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        MenuItemCompat.setOnActionExpandListener(searchItem, new SearchViewExpandListener(this));
        MenuItemCompat.setActionView(searchItem, searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, "You searched " + s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    private class SearchViewExpandListener implements MenuItemCompat.OnActionExpandListener
    {

        private Context context;

        public SearchViewExpandListener (Context c) {
            context = c;
        }

        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(true);
            return false;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(false);
            return false;
        }

    }

    @Override

    public Loader<Cursor> onCreateLoader(int arg0,Bundle query)

    {

        CursorLoader cLoader = null;

        if(arg0==0)

            cLoader = new CursorLoader(getBaseContext(), LocationsContentProvider.BASE_SEARCH_URI, null, null, new String[]{ query.getString("query") }, null);

        else if(arg0==1)

            cLoader = new CursorLoader(getBaseContext(), LocationsContentProvider.BASE_DETAILS_URI, null, null, new String[]{ query.getString("query") }, null);

        return cLoader;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0,Cursor arg1)

    {

        int locationCount = 0;
        double lat=0;
        double lng=0;
        float zoom=0;

        // Number of locations available in the SQLite database table
        locationCount = arg1.getCount();

        // Move the current record pointer to the first row of the table
        arg1.moveToFirst();

        for(int i=0;i<locationCount;i++)

        {

            // Get the latitude
            lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LAT));

            // Get the longitude
            lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LNG));

            // Get the zoom level
            zoom = arg1.getFloat(arg1.getColumnIndex(LocationsDB.FIELD_ZOOM));

            // Creating an instance of LatLng to plot the location in Google Maps
            LatLng location = new LatLng(lat, lng);

            // Drawing the marker in the Google Maps
            drawMarker(location);

            // Traverse the pointer to the next row
            arg1.moveToNext();

        }

        if(locationCount>0)

        {

            // Moving CameraPosition to last clicked position

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));

            // Setting the zoom level in the map on last position  is clicked

            googleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));

        }

    }

    @Override

    public void onLoaderReset(Loader<Cursor> arg0)
    {
        // TODO Auto-generated method stub
    }

}