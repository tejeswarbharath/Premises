package udacity.tour.tejeswar.premises.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import udacity.tour.tejeswar.premises.PremiseContract.NearBySearchEntry;
import udacity.tour.tejeswar.premises.PremiseContract.AutoCompleteEntry;

/**
 * Created by tejeswar on 10/16/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper

{

    public static final String LOG = "DatabaseHelper";

    public static final int DATABASE_VERSION =2;

    private static final String DATABASE_NAME = "premises.db";

    public DatabaseHandler(Context context)

    {

        super(context,DATABASE_NAME, null , DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db)

    {

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + NearBySearchEntry.TABLE_NAME +
                " (" +
                NearBySearchEntry._ID + " INTEGER PRIMARY KEY," +
                NearBySearchEntry.COLUMN_LOCATION + " TEXT UNIQUE NOT NULL, " +
                //NearBySearchEntry.COLUMN_COORD_LAT + " REAL NOT NULL, " +
                //NearBySearchEntry.COLUMN_COORD_LONG + " REAL NOT NULL " +
                NearBySearchEntry.COLUMN_RADIUS + " TEXT NOT NULL, " +
                NearBySearchEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                NearBySearchEntry.COLUMN_KEYWORD + " TEXT NOT NULL, " +
                " );" ;

        final String SQL_CREATE_AUTOCOMPLETE_TABLE = "CREATE TABLE " + AutoCompleteEntry.TABLE_NAME +
                " (" +
                AutoCompleteEntry._ID + " INTEGER PRIMARY KEY," +
                AutoCompleteEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, " +
                AutoCompleteEntry.COLUMN_INPUT + " TEXT UNIQUE NOT NULL, " +
                AutoCompleteEntry.COLUMN_TYPES + " TEXT NOT NULL, " +
                AutoCompleteEntry.COLUMN_LANGUAGE + " REAL NOT NULL, " +
                " FOREIGN KEY (" + AutoCompleteEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                NearBySearchEntry.TABLE_NAME + " (" + NearBySearchEntry._ID + "), " +
                " );";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);

        db.execSQL(SQL_CREATE_AUTOCOMPLETE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

    {

        db.execSQL("DROP TABLE IF EXISTS " + NearBySearchEntry.TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS " + AutoCompleteEntry.TABLE_NAME);

        onCreate(db);

    }

}
