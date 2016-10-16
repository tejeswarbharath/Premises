package udacity.tour.tejeswar.premises.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by tejeswar on 10/16/2016.
 */

public class PremiseProvider extends ContentProvider

{

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHandler mOpenHelper;

    static final int AUTOCOMPLETE = 1;
    static final int AUTOCOMPLETE_WITH_LOCATION = 12;
    static final int LOCATION = 3;

    private static final SQLiteQueryBuilder sAutoCompleteWithLocationQueryBuilder;

    static

    {

        sAutoCompleteWithLocationQueryBuilder = new SQLiteQueryBuilder();

        //inner join
        sAutoCompleteWithLocationQueryBuilder.setTables(
                PremiseContract.AutoCompleteEntry.TABLE_NAME + " INNER JOIN " +
                        PremiseContract.NearBySearchEntry.TABLE_NAME +
                        " ON " + PremiseContract.NearBySearchEntry.TABLE_NAME +
                        "." + PremiseContract.AutoCompleteEntry.COLUMN_LOC_KEY +
                        " = " + PremiseContract.NearBySearchEntry.TABLE_NAME +
                        "." + PremiseContract.NearBySearchEntry._ID );

    }

    //location.location_setting = ?

    private static final String sLocation =
            PremiseContract.NearBySearchEntry.TABLE_NAME+
                    "." + PermiseContract.NearBySearchEntry.COLUMN_LOCATION + " = ? ";

    private Cursor getAutoCompleteWithLocation(Uri uri, String[] projection, String sortOrder)

    {

        String locationSetting = PremiseContract.AutoCompleteEntry.getLocationFromUri(uri);

        String[] selectionArgs;

        String selection;

        selection = sLocation;

        selectionArgs = new String[]{locationSetting};

        return sAutoCompleteByLocationSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

    }

    static UriMatcher buildUriMatcher()

    {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = PremiseContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PremiseContract.PATH_AUTOCOMPLETE, AUTOCOMPLETE);

        matcher.addURI(authority, PremiseContract.PATH_AUTOCOMPLETE + "/*", AUTOCOMPLETE_WITH_LOCATION);

        matcher.addURI(authority, PremiseContract.PATH_LOCATION, LOCATION);

        return matcher;

    }


    @Override

    public boolean onCreate()

    {

        mOpenHelper = new DatabaseHandler(getContext());

        return true;

    }

    @Nullable
    @Override
    public String getType(Uri uri)

    {

        final int match = sUriMatcher.match(uri);

        switch (match)
        {

            case AUTOCOMPLETE_WITH_LOCATION:

                return PremiseContract.AutoCompleteEntry.CONTENT_TYPE;

            case AUTOCOMPLETE:

                return PremiseContract.AutoCompleteEntry.CONTENT_TYPE;

            case LOCATION:

                return PremiseContract.NearBySearchEntry.CONTENT_TYPE;

            default:

                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

    }

    @Override

    public int delete(Uri uri, String selection, String[] selectionArgs)

    {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        int rowsDeleted;

        if ( null == selection ) selection = "1";

        switch (match)
        {

            case AUTOCOMPLETE:
                rowsDeleted = db.delete(PremiseContract.AutoCompleteEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case LOCATION:
                rowsDeleted = db.delete(PremiseContract.NearBySearchEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0)
        {

            getContext().getContentResolver().notifyChange(uri, null);

        }

        return rowsDeleted;

    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {

        Cursor retCursor;

        switch (sUriMatcher.match(uri))

        {

            // "AutoComplete with location"
            case AUTOCOMPLETE_WITH_LOCATION:
            {
                retCursor = getAutoCompleteWithLocation(uri, projection, sortOrder);

                break;

            }

            // "AutoComplete"
            case AUTOCOMPLETE:
            {

                retCursor = mOpenHelper.getReadableDatabase().query(
                        PremiseContract.AutoCompleteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;

            }

            // "NearBySearch"
            case LOCATION:
            {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PremiseContract.NearBySearchEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;
            }

            default:

                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override

    public Uri insert(Uri uri, ContentValues values)
    {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match)
        {
            case AUTOCOMPLETE:
            {

                normalizeDate(values);

                long _id = db.insert(PremiseContract.AutoCompleteEntry.TABLE_NAME, null, values);

                if ( _id > 0 )

                    returnUri = PremiseContract.AutoCompleteEntry.buildAutoCompleteUri(_id);

                else

                    throw new android.database.SQLException("Failed to insert row into " + uri);

                break;

            }
            case LOCATION:
            {

                long _id = db.insert(PremiseContract.NearBySearchEntry.TABLE_NAME, null, values);

                if ( _id > 0 )

                    returnUri = PremiseContract.NearBySearchEntry.buildLocationUri(_id);

                else

                    throw new android.database.SQLException("Failed to insert row into " + uri);

                break;

            }

            default:

                 throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)

    {

        @Override

        public int bulkInsert(Uri uri, ContentValues[] values)
        {

            final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

            final int match = sUriMatcher.match(uri);

            switch (match)
            {

                case AUTOCOMPLETE:

                    db.beginTransaction();

                    int returnCount = 0;

                    try
                    {

                        for (ContentValues value : values)
                        {

                            normalizeDate(value);

                            long _id = db.insert(PremiseContract.AutoCompleteEntry.TABLE_NAME, null, value);

                            if (_id != -1)
                            {

                                returnCount++;

                            }

                        }

                        db.setTransactionSuccessful();
                    }
                    finally
                    {

                        db.endTransaction();

                    }

                    getContext().getContentResolver().notifyChange(uri, null);

                    return returnCount;

                default:

                    return super.bulkInsert(uri, values);

            }

        }

    }

    @Override

    public int bulkInsert(Uri uri, ContentValues[] values)

    {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        switch (match)
        {

            case AUTOCOMPLETE:

                db.beginTransaction();

                int returnCount = 0;

                try {
                    for (ContentValues value : values)
                    {
                        normalizeDate(value);

                        long _id = db.insert(PremiseContract.AutoCompleteEntry.TABLE_NAME, null, value);

                        if (_id != -1)
                        {
                            returnCount++;
                        }

                    }
                    db.setTransactionSuccessful();
                }

                finally
                {

                    db.endTransaction();

                }

                getContext().getContentResolver().notifyChange(uri, null);

                return returnCount;

            default:

                return super.bulkInsert(uri, values);


        }

    }

}