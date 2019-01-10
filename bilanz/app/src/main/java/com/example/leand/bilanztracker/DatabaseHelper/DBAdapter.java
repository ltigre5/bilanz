package com.example.leand.bilanztracker.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.leand.bilanztracker.Activitys.EditIncomeActivity;
import com.example.leand.bilanztracker.Activitys.ExpenseActivity;
import com.example.leand.bilanztracker.Activitys.IncomeActivity;
import com.example.leand.bilanztracker.Activitys.MainActivity;

public class DBAdapter {
    public final String SALARY = "salary";
    public final String OTHER_INCOME = "other income";
    public final String SEARCH_SALARY = "'salary'";
    public final String SEARCH_OTHER_INCOME = "'other income'";

    // Logcat tag
    private static final String LOG = "DBAdapter";

    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "annualBalanceSheet";

    // Table Names
    private static final String TABLE_PROFILE = "profile";
    private static final String TABLE_EXPENSE = "expense";
    private static final String TABLE_INCOME = "income";
    private static final String TABLE_DEDUCTION = "deduction";
    private static final String TABLE_CURRENCY = "currency";

    // Common column names
    public static final String KEY_ID = "_id";
    public static final String KEY_PROFILE_ID = "profile_id";
    public static final String KEY_INCOME_ID = "income_id";

    // Profile Table - column names
    public static final String KEY_PROFILE = "profile";

    // Cost Table - column names
    public static final String KEY_EXPENSE_TYPE = "expense_type";
    public static final String KEY_EXPENSE_YEAR = "expense_year";

    // Income Table - column names
    public static final String KEY_INCOME_TYPE = "income_type";
    public static final String KEY_INCOME_YEAR_GROSS = "income_year_gross";
    public static final String KEY_INCOME_YEAR_NET = "income_year_net";

    // Deduction Table - column names
    public static final String KEY_DEDUCTION_TYPE = "deduction_type";
    public static final String KEY_PERCENTAGE = "percentage";

    // Currency Table - column names
    public static final String KEY_CURRENCY = "currency";

    public static final String[] ALL_KEYS_PROFILE = new String[]{KEY_ID, KEY_PROFILE};
    public static final String[] ALL_KEYS_EXPENSE = new String[]{KEY_ID, KEY_PROFILE_ID, KEY_EXPENSE_TYPE, KEY_EXPENSE_YEAR};
    public static final String[] ALL_KEYS_INCOME = new String[]{KEY_ID, KEY_PROFILE_ID, KEY_INCOME_TYPE, KEY_INCOME_YEAR_GROSS, KEY_INCOME_YEAR_NET};
    public static final String[] ALL_KEYS_DEDUCTION = new String[]{KEY_ID, KEY_PROFILE_ID, KEY_INCOME_ID, KEY_DEDUCTION_TYPE, KEY_PERCENTAGE};
    public static final String[] ALL_KEYS_CURRENCY = new String[]{KEY_ID, KEY_CURRENCY};

    // Profile Table Create Statements
    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + TABLE_PROFILE + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE + " TEXT NOT NULL " + ")";

    // Expense table create statement
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE "
            + TABLE_EXPENSE + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE_ID + " INTEGER NOT NULL,"
            + KEY_EXPENSE_TYPE + " TEXT NOT NULL,"
            + KEY_EXPENSE_YEAR + " INTEGER NOT NULL" + ")";

    // Income table create statement
    private static final String CREATE_TABLE_INCOME = "CREATE TABLE "
            + TABLE_INCOME + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE_ID + " INTEGER NOT NULL,"
            + KEY_INCOME_TYPE + " INTEGER NOT NULL,"
            + KEY_INCOME_YEAR_GROSS + " INTEGER NOT NULL, "
            + KEY_INCOME_YEAR_NET + " INTEGER NOT NULL " + ")";

    // Deduction table create statement
    private static final String CREATE_TABLE_DEDUCTION = "CREATE TABLE "
            + TABLE_DEDUCTION + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE_ID + " INTEGER NOT NULL,"
            + KEY_INCOME_ID + " INTEGER NOT NULL,"
            + KEY_DEDUCTION_TYPE + " INTEGER NOT NULL,"
            + KEY_PERCENTAGE + " INTEGER NOT NULL" + ")";

    // Currency table create statement
    private static final String CREATE_TABLE_CURRENCY = "CREATE TABLE "
            + TABLE_CURRENCY + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_CURRENCY + " TEXT NOT NULL" + ")";

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
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // creating required tables
            db.execSQL(CREATE_TABLE_PROFILE);
            db.execSQL(CREATE_TABLE_EXPENSE);
            db.execSQL(CREATE_TABLE_INCOME);
            db.execSQL(CREATE_TABLE_DEDUCTION);
            db.execSQL(CREATE_TABLE_CURRENCY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEDUCTION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY);

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
     * Add a new profile with a name and actualizes MainActivity.long_ProfileId
     *
     * @param profileName name of the new Profile
     */
    public void insertRowProfile(String profileName) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE, profileName);

        // Insert it into the database.
        MainActivity.long_ProfileId = db.insert(TABLE_PROFILE, null, initialValues);
    }

    /**
     * Add a new profile with default name and actualizes MainActivity.long_ProfileId
     */
    public void insertRowProfile() {
        insertRowProfile("new Profile");
    }

    /**
     * Add a new expense and actualizes ExpenseActivity.long_ExpenseId
     *
     * @param expenseType name of expense in string
     * @param expenseYear expense in year in double
     */
    public void insertRowExpense(String expenseType, double expenseYear) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE_ID, MainActivity.long_ProfileId);
        initialValues.put(KEY_EXPENSE_TYPE, expenseType);
        initialValues.put(KEY_EXPENSE_YEAR, expenseYear);

        // Insert it into the database.
        ExpenseActivity.long_ExpenseId = db.insert(TABLE_EXPENSE, null, initialValues);
    }

    /**
     * Add a new income and actualizes IncomeActivity.long_IncomeId
     *
     * @param incomeType      name of income in string
     * @param incomeYearGross income gross in year in double
     * @param incomeYearNet income net in year in double
     */
    public void insertRowIncome(String incomeType, double incomeYearGross, double incomeYearNet) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE_ID, MainActivity.long_ProfileId);
        initialValues.put(KEY_INCOME_TYPE, incomeType);
        initialValues.put(KEY_INCOME_YEAR_GROSS, incomeYearGross);
        initialValues.put(KEY_INCOME_YEAR_NET, incomeYearNet);

        // Insert it into the database.
        IncomeActivity.long_IncomeId = db.insert(TABLE_INCOME, null, initialValues);
    }

    /**
     * Add a new income and actualizes IncomeActivity.long_IncomeId
     *
     * @param incomeType      name of income in string
     * @param incomeYearGross income gross in year in double
     */
    public void insertRowIncome(String incomeType, double incomeYearGross) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE_ID, MainActivity.long_ProfileId);
        initialValues.put(KEY_INCOME_TYPE, incomeType);
        initialValues.put(KEY_INCOME_YEAR_GROSS, incomeYearGross);
        initialValues.put(KEY_INCOME_YEAR_NET, incomeYearGross);

        // Insert it into the database.
        IncomeActivity.long_IncomeId = db.insert(TABLE_INCOME, null, initialValues);
    }


    /**
     * Add a new deduction
     *
     * @param deductionType name of deduction in string
     * @param percentage    deduction in double
     */
    public void insertRowDeduction(String deductionType, double percentage) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE_ID, MainActivity.long_ProfileId);
        initialValues.put(KEY_INCOME_ID, IncomeActivity.long_IncomeId);
        initialValues.put(KEY_DEDUCTION_TYPE, deductionType);
        initialValues.put(KEY_PERCENTAGE, percentage);

        // Insert it into the database.
        db.insert(TABLE_DEDUCTION, null, initialValues);

        updateRowIncomeNet();
    }

    /**
     * Add a new currency
     *
     * @param currency currency
     */
    public void insertCurrency(String currency) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CURRENCY, currency);

        // Insert it into the database.
        db.insert(TABLE_CURRENCY, null, initialValues);
    }

    // insert rows
    //----------------------------------------------------------------------------------------------
    // update row

    /**
     * Change profile name
     *
     * @param profileTitle new profile name
     */
    public void updateProfileTitle(String profileTitle) {
        String where = KEY_ID + "=" + MainActivity.long_ProfileId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_PROFILE, profileTitle);

        // Insert it into the database.
        db.update(TABLE_PROFILE, newValues, where, null);

        MainActivity.string_actualProfile = "Actual Profile: " + profileTitle;
    }


    /**
     * update the income
     *
     * @param incomeType      name of income in string
     * @param incomeYearGross income gross in year in double
     */
    public void updateRowIncome(String incomeType, double incomeYearGross) {
        double totalDeduction = 0.00;

        if (checkDeductionExists()) {
            Cursor cursor = getAllRowsDeduction();
            do {
                totalDeduction += cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_PERCENTAGE));
            } while (cursor.moveToNext());
            cursor.close();
        }

        double incomeYearNet = incomeYearGross - (incomeYearGross * totalDeduction / 100);

        String where = KEY_PROFILE_ID + "=" + MainActivity.long_ProfileId + " AND " + KEY_ID + "=" + IncomeActivity.long_IncomeId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_INCOME_TYPE, incomeType);
        newValues.put(KEY_INCOME_YEAR_GROSS, incomeYearGross);
        newValues.put(KEY_INCOME_YEAR_NET, incomeYearNet);

        // Insert it into the database.
        db.update(TABLE_INCOME, newValues, where, null);
    }

    /**
     * update the income net
     */
    public void updateRowIncomeNet() {
        Cursor cursor = MainActivity.myDbMain.getRowIncome();
        String incomeTitle = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_TYPE));
        Double incomeGross = cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_YEAR_GROSS));
        cursor.close();

        updateRowIncome(incomeTitle, incomeGross);
    }


    /**
     * update the cost
     *
     * @param expenseType name of cost in string
     * @param expenseYear cost in year in double
     */
    public void updateRowExpense(String expenseType, double expenseYear) {
        String where = KEY_ID + "=" + ExpenseActivity.long_ExpenseId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_EXPENSE_TYPE, expenseType);
        newValues.put(KEY_EXPENSE_YEAR, expenseYear);

        // Insert it into the database.
        db.update(TABLE_EXPENSE, newValues, where, null);
    }

    /**
     * update the deduction
     *
     * @param deductionId   id of deduction in long
     * @param deductionType name of deduction in string
     * @param percentage    deduction in double
     */
    public void updateRowDeduction(long deductionId, String deductionType, double percentage) {
        String where = KEY_ID + "=" + deductionId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DEDUCTION_TYPE, deductionType);
        newValues.put(KEY_PERCENTAGE, percentage);

        // Insert it into the database.
        db.update(TABLE_DEDUCTION, newValues, where, null);

        updateRowIncomeNet();
    }

    /**
     * update the currency
     *
     * @param currency name of currency in string
     */
    public void updateCurrency(String currency) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_CURRENCY, currency);

        // Insert it into the database.
        db.update(TABLE_CURRENCY, newValues, null, null);
    }

    // update row
    //----------------------------------------------------------------------------------------------
    // delete rows

    /**
     * delete a Profile with profileID
     *
     * @param profileId ID of the Profile in long
     */
    public void deleteProfile(long profileId) {
        String where = KEY_ID + "=" + profileId;
        db.delete(TABLE_PROFILE, where, null);

        where = KEY_PROFILE_ID + "=" + profileId;
        db.delete(TABLE_EXPENSE, where, null);
        db.delete(TABLE_INCOME, where, null);
        db.delete(TABLE_DEDUCTION, where, null);
    }

    /**
     * delete a cost with rowID of the cost
     *
     * @param expenseId ID of the cost in long
     */
    public void deleteExpense(long expenseId) {
        String where = KEY_ID + "=" + expenseId;
        db.delete(TABLE_EXPENSE, where, null);
    }

    /**
     * delete a income with rowID of the income
     *
     * @param incomeId ID of the income in long
     */
    public void deleteIncome(long incomeId) {
        String where = KEY_ID + "=" + incomeId;
        db.delete(TABLE_INCOME, where, null);
    }

    /**
     * delete a deduction with rowID of the deduction
     *
     * @param deductionId ID of the deduction in long
     */
    public void deleteDeduction(long deductionId) {
        String where = KEY_ID + "=" + deductionId;
        db.delete(TABLE_DEDUCTION, where, null);
    }

    // delete rows
    //----------------------------------------------------------------------------------------------
    // getRows

    /**
     * Return all data in the database from table profile.
     *
     * @return Return cursor with all data in the database from table profile.
     */
    public Cursor getAllRowsProfile() {
        Cursor c = db.query(true, TABLE_PROFILE, ALL_KEYS_PROFILE,
                null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Return all data in the database from a profile.
     *
     * @return Return cursor with all data in the database from profile.
     */
    public Cursor getRowProfile() {
        String where = KEY_ID + "=" + MainActivity.long_ProfileId;
        Cursor c = db.query(true, TABLE_PROFILE, ALL_KEYS_PROFILE,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Return all other income in the database from table income.
     *
     * @return Return cursor with all other income in the database from table income.
     */
    public Cursor getAllRowsIncome() {
        String where = KEY_PROFILE_ID + "=" + MainActivity.long_ProfileId;
        String orderBy = KEY_INCOME_YEAR_GROSS + " DESC";
        Cursor c = db.query(true, TABLE_INCOME, ALL_KEYS_INCOME,
                where, null, null, null, orderBy, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Return all Data in the database from a row in table income with a id.
     *
     * @return Return cursor from a row in table income with id.
     */
    public Cursor getRowIncome() {
        String where = KEY_ID + "=" + IncomeActivity.long_IncomeId;
        Cursor c = db.query(true, TABLE_INCOME, ALL_KEYS_INCOME,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Return all data in the database from table expense.
     *
     * @return Return cursor with all data in the database from table expense.
     */
    public Cursor getAllRowsExpense() {
        String where = KEY_PROFILE_ID + "=" + MainActivity.long_ProfileId;
        String orderBy = KEY_EXPENSE_YEAR + " DESC";
        Cursor c = db.query(true, TABLE_EXPENSE, ALL_KEYS_EXPENSE,
                where, null, null, null, orderBy, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Return all Data in the database from a row in table expense with a id.
     *
     * @return Return cursor from a row in table expense with id.
     */
    public Cursor getRowExpense() {
        String where = KEY_ID + "=" + ExpenseActivity.long_ExpenseId;
        Cursor c = db.query(true, TABLE_EXPENSE, ALL_KEYS_EXPENSE,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Return all data in the database from table deduction.
     *
     * @return Return cursor with all data in the database from table deduction.
     */
    public Cursor getAllRowsDeduction() {
        String where = KEY_INCOME_ID + "=" + IncomeActivity.long_IncomeId;
        String orderBy = KEY_PERCENTAGE + " DESC";
        Cursor c = db.query(true, TABLE_DEDUCTION, ALL_KEYS_DEDUCTION,
                where, null, null, null, orderBy, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Return data in the database from a specific deduction with the id.
     *
     * @return Return cursor with all data in the database from specific deduction.
     */
    public Cursor getRowDeduction() {
        String where = KEY_ID + "=" + EditIncomeActivity.long_DeductionId;
        Cursor c = db.query(true, TABLE_DEDUCTION, ALL_KEYS_DEDUCTION,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Return all data in the database from table currency.
     *
     * @return Return cursor with all data in the database from table currency.
     */
    public Cursor getAllCurrency() {
        Cursor c = db.query(true, TABLE_CURRENCY, ALL_KEYS_CURRENCY,
                null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // getRows
    //----------------------------------------------------------------------------------------------
    // check Methods

    /**
     * Checks if a profile already exists
     *
     * @return true if a profile already exists
     */
    public boolean checkProfileExists() {
        boolean exists;
        Cursor c = db.query(true, TABLE_PROFILE, ALL_KEYS_PROFILE,
                null, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    /**
     * Checks if income already exists for profile
     *
     * @return true if income already exists
     */
    public boolean checkIncomeExists() {
        String where = KEY_PROFILE_ID + "=" + MainActivity.long_ProfileId;
        boolean exists;
        Cursor c = db.query(true, TABLE_INCOME, ALL_KEYS_INCOME,
                where, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    /**
     * Checks if expense already exists for profile
     *
     * @return true if expense already exists
     */
    public boolean checkExpenseExists() {
        String where = KEY_PROFILE_ID + "=" + MainActivity.long_ProfileId;
        boolean exists;
        Cursor c = db.query(true, TABLE_EXPENSE, ALL_KEYS_EXPENSE,
                where, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    /**
     * Checks if a deduction already exists for profile
     *
     * @return true if a deduction already exists
     */
    public boolean checkDeductionExists() {
        String where = KEY_PROFILE_ID + "=" + MainActivity.long_ProfileId + " AND " + KEY_INCOME_ID + "=" + IncomeActivity.long_IncomeId;
        boolean exists;
        Cursor c = db.query(true, TABLE_DEDUCTION, ALL_KEYS_DEDUCTION,
                where, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    /**
     * Checks if a specific deduction already exists with the id
     *
     * @return true if a deduction already exists
     */
    public boolean checkDeductionExists(long id) {
        String where = KEY_ID + "=" + id;
        boolean exists;
        Cursor c = db.query(true, TABLE_DEDUCTION, ALL_KEYS_DEDUCTION,
                where, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    /**
     * Checks if currency already exists
     *
     * @return true if currency already exists
     */
    public boolean checkCurrencyExists() {
        boolean exists;
        Cursor c = db.query(true, TABLE_CURRENCY, ALL_KEYS_CURRENCY,
                null, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    // check Methods
    //-----------------------------------------------------------------------------------------------------------------------------------------
    // End

}
