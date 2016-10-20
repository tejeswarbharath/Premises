package udacity.tour.tejeswar.tourism.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by tejeswar on 10/16/2016.
 */

//http://wptrafficanalyzer.in/blog/storing-and-retrieving-locations-in-sqlite-from-google-maps-android-api-v2
//https://www.simplifiedcoding.net/android-google-maps-tutorial-google-maps-android-api
//https://code.tutsplus.com/articles/google-play-services-using-the-places-api--cms-23715
//http://karnshah8890.blogspot.in/2013/03/google-places-api-tutorial.html

public class LocationsDB extends SQLiteOpenHelper

{

    private static String DBNAME = "locationmarkersqlite";

    private static int VERSION = 1;

    public static final String FIELD_ROW_ID = "_id";

    public static final String FIELD_LAT = "lat";

    public static final String FIELD_LNG = "lng";

    public static final String FIELD_ZOOM = "zom";

    private static final String DATABASE_TABLE = "locations";

    private SQLiteDatabase mDB;

    public LocationsDB(Context context)
    {

        super(context, DBNAME, null, VERSION);

        this.mDB = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db)

    {

        String sql = "create table " + DATABASE_TABLE + " ( " +
                FIELD_ROW_ID + " integer primary key autoincrement , " +
                FIELD_LNG + " double , " +
                FIELD_LAT + " double , " +
                FIELD_ZOOM + " text " +
                " ) ";

        db.execSQL(sql);

    }

    /** Inserts a new location to the table locations */
    public long insert(ContentValues contentValues)
    {

        long rowID = mDB.insert(DATABASE_TABLE, null, contentValues);
        return rowID;

    }

    /** Deletes all locations from the table */
    public int del()
    {

        int cnt = mDB.delete(DATABASE_TABLE, null , null);

        return cnt;

    }

    /** Returns all the locations from the table */

    public Cursor getAllLocations()
    {

        return mDB.query(DATABASE_TABLE, new String[] { FIELD_ROW_ID,  FIELD_LAT , FIELD_LNG, FIELD_ZOOM } , null, null, null, null, null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

}
