package com.example.leand.bilanztracker.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelpe extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelpe";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "annualBalanceSheet";

    // Table Names
    private static final String TABLE_PROFILE = "profile";
    private static final String TABLE_COST = "cost";
    private static final String TABLE_INCOME = "income";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_PROFILE_ID = "profile_id";

    // PROFILE Table - column nmaes
    private static final String KEY_PROFILES = "profiles";

    // COST Table - column names
    private static final String KEY_COST_TYPE = "cost_type";
    private static final String KEY_PRICE_YEAR = "price_year";

    // INCOME Table - column names
    private static final String KEY_INCOME_TYPE = "income_type";
    private static final String KEY_INCOME_YEAR = "income_year";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + TABLE_PROFILE + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILES + " TEXT NOT NULL" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_COST = "CREATE TABLE "
            + TABLE_COST + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE_ID + " INTEGER NOT NULL,"
            + KEY_COST_TYPE + " TEXT NOT NULL,"
            + KEY_PRICE_YEAR + " INTEGER NOT NULL"  + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_INCOME = "CREATE TABLE "
            + TABLE_INCOME + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE_ID + " INTEGER NOT NULL,"
            + KEY_INCOME_TYPE + " INTEGER NOT NULL,"
            + KEY_INCOME_YEAR + " INTEGER NOT NULL" + ")";

    public DatabaseHelpe(Context context) {
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);

        // create new tables
        onCreate(db);
    }

    // create Database
    //----------------------------------------------------------------------------------------------
    // create column

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelpe myDBHelper;
    private SQLiteDatabase db;

    // definitions
    //-----------------------------------------------------------------------------------------------------------------------------------------
    // Initialize, open, close Database


    public DatabaseHelpe(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelpe(context);
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
    //-----------------------------------------------------------------------------------------------------------------------------------------
    // Insert Rows

    private static class 

    // create column
    //----------------------------------------------------------------------------------------------
    // End

}
