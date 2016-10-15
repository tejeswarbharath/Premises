package udacity.tour.tejeswar.premises.data;

/**
 * Created by tejeswar on 10/16/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper

{

    public static final String LOG = "DatabaseHelper";

    public static final int DATABASE_VERSION =1;

    private static final String DATABASE_NAME = "ContractManager";

    private static final String

    private static final String TABLE_NEARBY ="nearby";

    private static final String TABLE_AUTO ="auto";

    private static final String TABLE_NEARBY_AUTO = "nearby_auto";

    //Common Column
    private static final String KEY_ID = "id";

    //first table
    private static final String KEY_NAME = "nearby";
    private static final String KEY_TIMINGS = "timings";

    //second table
    private static final String KEY_ = "description";

    //first-second combined table



}
