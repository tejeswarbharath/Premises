package udacity.tour.tejeswar.tourism.data;

/**
 * Created by tejeswar on 10/15/2016.
 */

public class PremiseContract

{

  /**  public static final String CONTENT_AUTHORITY = "udacity.tour.tejeswar.premises";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LOCATION = "search";

    public static final class NearBySearchEntry extends BaseColumns

    {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_LOCATION;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_LOCATION;

        public static final String TABLE_NAME ="Search";

        public static final String COLUMN_COORD_LAT = "coord_lat";

        public static final String COLUMN_COORD_LONG = "coord_long";

        public static final String COLUMN_RADIUS ="radius"

        public static final String COLUMN_TYPE = "type"

        public static final String COLUMN_KEYWORD = "keyword"

        public static Uri buildLocationUri(long id)

        {

            return ContentUris.withAppendedId(CONTENT_URI,id);

        }

    }**/

}
