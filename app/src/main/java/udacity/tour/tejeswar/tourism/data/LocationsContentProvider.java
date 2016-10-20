package udacity.tour.tejeswar.tourism.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

/**
 * Created by tejeswar on 10/16/2016.
 */

public class LocationsContentProvider extends ContentProvider
{

    public static final String PROVIDER_NAME = "udacity.tour.tejeswar.tourism";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME );

    public static final String PATH_LOCATION = "locations";

    private static final int LOCATIONS = 1;

    private static final UriMatcher uriMatcher ;

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

    static

    {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PROVIDER_NAME, PATH_LOCATION , LOCATIONS);

    }

    LocationsDB mLocationsDB;

    @Override

    public boolean onCreate()

    {

        mLocationsDB = new LocationsDB(getContext());

        return true;

    }

    @Override

    public Uri insert(Uri uri, ContentValues values)

    {

        long rowID = mLocationsDB.insert(values);

        Uri _uri=null;

        if(rowID>0)

        {

            _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);

        }
        else
        {

            try

            {

                throw new SQLException("Failed to insert : " + uri);

            }
            catch (SQLException e)
            {

                e.printStackTrace();

            }

        }

        return _uri;

    }

    @Override

    public int update(Uri uri, ContentValues values, String selection ,String[] selectionArgs)

    {

        return 0;

    }

    @Override

    public int delete(Uri uri, String selection, String[] selectionArgs)

    {

        int cnt = 0;

        cnt = mLocationsDB.del();

        return cnt;

    }

    /** A callback method which is invoked by default content uri */

    @Override

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)

    {

        if(uriMatcher.match(uri)==LOCATIONS)

        {

            return mLocationsDB.getAllLocations();

        }

        return null;

    }

    @Override
    public String getType(Uri uri)

    {

        return null;

    }

}