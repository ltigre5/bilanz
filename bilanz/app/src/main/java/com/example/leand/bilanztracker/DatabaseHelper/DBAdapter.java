package com.example.leand.bilanztracker.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

    // Logcat tag
    private static final String LOG = "DBAdapter";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "annualBalanceSheet";

    // Table Names
    private static final String TABLE_PROFILE = "profile";
    private static final String TABLE_COST = "cost";
    private static final String TABLE_SALARY = "income";
    private static final String TABLE_SALARY_DEDUCTIONS = "salary_deductions";


    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_PROFILE_ID = "profile_id";

    // PROFILE Table - column nmaes
    private static final String KEY_PROFILE = "profile";

    // COST Table - column names
    private static final String KEY_COST_TYPE = "cost_type";
    private static final String KEY_COST_YEAR = "cost_year";

    // INCOME Table - column names
    private static final String KEY_INCOME_TYPE = "income_type";
    private static final String KEY_INCOME_YEAR = "income_year";

    public static final String[] ALL_KEYS_PROFILE = new String[]{KEY_PROFILE_ID, KEY_PROFILE};
    public static final String[] ALL_KEYS_COST = new String[]{KEY_ID, KEY_PROFILE_ID, KEY_COST_TYPE, KEY_COST_YEAR};
    public static final String[] ALL_KEYS_INCOME = new String[]{KEY_ID, KEY_PROFILE_ID, KEY_INCOME_TYPE, KEY_INCOME_YEAR};


    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + TABLE_PROFILE + "("
            + KEY_PROFILE_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE + " TEXT NOT NULL" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_COST = "CREATE TABLE "
            + TABLE_COST + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE_ID + " INTEGER NOT NULL,"
            + KEY_COST_TYPE + " TEXT NOT NULL,"
            + KEY_COST_YEAR + " INTEGER NOT NULL" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_INCOME = "CREATE TABLE "
            + TABLE_SALARY + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE_ID + " INTEGER NOT NULL,"
            + KEY_INCOME_TYPE + " INTEGER NOT NULL,"
            + KEY_INCOME_YEAR + " INTEGER NOT NULL" + ")";

    // Database definitions
    //----------------------------------------------------------------------------------------------
    // Initialize, open, close Database

    // Context of application who uses us.
    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public void open() {
        db = myDBHelper.getWritableDatabase();
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Initialize, open, close Database
    //----------------------------------------------------------------------------------------------
    // Database Helper

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // creating required tables
            db.execSQL(CREATE_TABLE_PROFILE);
            db.execSQL(CREATE_TABLE_COST);
            db.execSQL(CREATE_TABLE_INCOME);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COST);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALARY);

            // create new tables
            onCreate(db);
        }
    }

    // Database Helper
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    // insert rows

    /**
     * Add a new profile
     *
     * @param profile profile name in string
     */
    public void insertRowProfile(String profile) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE, profile);

        // Insert it into the database.
        db.insert(TABLE_PROFILE, null, initialValues);
    }

    /**
     * Add a new cost
     *
     * @param profileID ID of the profile in long
     * @param costType  name of cost in string
     * @param costYear  cost in year in double
     */
    public void insertRowCost(long profileID, String costType, double costYear) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE_ID, profileID);
        initialValues.put(KEY_COST_TYPE, costType);
        initialValues.put(KEY_COST_YEAR, costYear);

        // Insert it into the database.
        db.insert(TABLE_COST, null, initialValues);
    }

    /**
     * Add a new income
     *
     * @param profileID  ID of the profile in long
     * @param incomeType name of income in string
     * @param incomeYear income in year in double
     */
    public void insertRowIncome(long profileID, String incomeType, double incomeYear) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE_ID, profileID);
        initialValues.put(KEY_COST_TYPE, incomeType);
        initialValues.put(KEY_COST_YEAR, incomeYear);

        // Insert it into the database.
        db.insert(TABLE_SALARY, null, initialValues);
    }

    // insert rows
    //----------------------------------------------------------------------------------------------
    // delete rows

    /**
     * delete a Profile with profileID
     *
     * @param profileId ID of the Profile in long
     */
    public void deleteProfile(long profileId) {
        String where = KEY_PROFILE_ID + "=" + profileId;
        db.delete(TABLE_PROFILE, where, null);
        db.delete(TABLE_COST, where, null);
        db.delete(TABLE_SALARY, where, null);
    }

    /**
     * delete a cost with rowID of the cost
     *
     * @param costId ID of the cost in long
     */
    public void deleteCost(long costId) {
        String where = KEY_ID + "=" + costId;
        db.delete(TABLE_COST, where, null);
    }

    /**
     * delete a income with rowID of the income
     *
     * @param incomeId ID of the income in long
     */
    public void deleteIncome(long incomeId) {
        String where = KEY_ID + "=" + incomeId;
        db.delete(TABLE_SALARY, where, null);
    }

    // delete rows
    //----------------------------------------------------------------------------------------------
    // update row

    /**
     * Change profile name
     *
     * @param profileId id of profile in long
     * @param profile new profile name
     */
    public void updateRowProfile(long profileId, String profile) {
        String where = KEY_PROFILE_ID + "=" + profileId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_PROFILE, profile);

        // Insert it into the database.
        db.update(TABLE_PROFILE, newValues, where, null);
    }

    /**
     * update the cost
     *
     * @param row_Id id of cost in long
     * @param costType name of cost in string
     * @param costYear cost in year in double
     */
    public void updateRowCost(long row_Id, String costType, double costYear) {
        String where = KEY_PROFILE_ID + "=" + row_Id;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_COST_TYPE, costType);
        newValues.put(KEY_COST_YEAR, costYear);

        // Insert it into the database.
        db.update(TABLE_COST, newValues, where, null);
    }

    /**
     *  update the income
     *
     * @param row_Id id of income in long
     * @param incomeType name of income in string
     * @param incomeYear income in year in double
     */
    public void updateRowIncome(long row_Id, String incomeType, double incomeYear) {
        String where = KEY_PROFILE_ID + "=" + row_Id;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_INCOME_TYPE, incomeType);
        newValues.put(KEY_INCOME_YEAR, incomeYear);

        // Insert it into the database.
        db.update(TABLE_SALARY, newValues, where, null);
    }

    // update row
    //----------------------------------------------------------------------------------------------
    // end

}
