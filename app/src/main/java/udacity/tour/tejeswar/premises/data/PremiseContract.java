package udacity.tour.tejeswar.tourism.data;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by tejeswar on 10/15/2016.
 */

public class PremiseContract
{
    public static final String CONTENT_AUTHORITY = "udacity.tour.tejeswar.tourism";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TOURISM ="tourism";

    public static final String PATH_LOCATION = "location";

    public static long normalizeDate()

    {

        Time time = new Time();

        time.set(startDate);

        int julianDay=Time.getJulianDay(startDate,time.gmtoff);

        return time.setJulianDay(julianDay);

    }

    public static final class NearBySearch extends BaseColumns

    {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_LOCATION;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_LOCATION;

        public static final String TABLE_NAME ="nearby";

        public static final String COLUMN_LOCATION = "coord_lat_long";

        //public static final String COLUMN_COORD_LAT = "coord_lat";

        //public static final String COLUMN_COORD_LONG = "coord_long";

        public static final String COLUMN_RADIUS ="radius"

        public static final String COLUMN_TYPE = "type"

        public static Uri buildLocationUri(long id)

        {

            return ContentUris.withAppendedId(CONTENT_URI,id);

        }

    }

/**    public static final class TourismEntry extends BaseColumns

    {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOURISM).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_URI+"/"+PATH_TOURISM;

        public static final String TABLE_NAME = "tourism";

        public static final String COLUMN_LOC_KEY = "location_id";

        public static final String COLUMN_TOURISM_ID ="tourism_id";

        public static final String COLUMN_DESC = "desc";

        public static final String COLUMN


    }**/



}
