package com.example.leand.bilanztracker.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.leand.bilanztracker.Activitys.MainActivity;

public class DBAdapter {
    public final String SALARY = "salary";
    public final String OTHER_INCOME = "other income";
    public final String SEARCH_SALARY = "'salary'";
    public final String SEARCH_OTHER_INCOME = "'other income'";


    // Logcat tag
    private static final String LOG = "DBAdapter";

    // Database Version
    private static final int DATABASE_VERSION = 3;

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

    // PROFILE Table - column names
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_TOTAL_EXPENSE_YEAR = "total_expense_year";
    public static final String KEY_TOTAL_INCOME_YEAR = "total_income_year";
    public static final String KEY_BALANCE_YEAR = "balance_year";

    // COST Table - column names
    public static final String KEY_EXPENSE_TYPE = "expense_type";
    public static final String KEY_EXPENSE_YEAR = "expense_year";

    // INCOME Table - column names
    public static final String KEY_INCOME_TYPE = "income_type";
    public static final String KEY_INCOME_YEAR = "income_year";
    public static final String KEY_SALARY_OTHER = "salary_other";

    // COST Table - column names
    public static final String KEY_DEDUCTION_TYPE = "deduction_type";
    public static final String KEY_PERCENTAGE = "percentage";

    // Currency Table - column names
    public static final String KEY_CURRENCY = "currency";

    public static final String[] ALL_KEYS_PROFILE = new String[]{KEY_ID, KEY_PROFILE, KEY_TOTAL_INCOME_YEAR, KEY_TOTAL_EXPENSE_YEAR, KEY_BALANCE_YEAR};
    public static final String[] ALL_KEYS_EXPENSE = new String[]{KEY_ID, KEY_PROFILE_ID, KEY_EXPENSE_TYPE, KEY_EXPENSE_YEAR};
    public static final String[] ALL_KEYS_INCOME = new String[]{KEY_ID, KEY_PROFILE_ID, KEY_INCOME_TYPE, KEY_INCOME_YEAR, KEY_SALARY_OTHER};
    public static final String[] ALL_KEYS_DEDUCTION = new String[]{KEY_ID, KEY_PROFILE_ID, KEY_DEDUCTION_TYPE, KEY_PERCENTAGE};
    public static final String[] ALL_KEYS_CURRENCY = new String[]{KEY_ID, KEY_CURRENCY};

    // Profile Table Create Statements
    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + TABLE_PROFILE + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE + " TEXT NOT NULL,"
            + KEY_TOTAL_INCOME_YEAR + " INTEGER NOT NULL,"
            + KEY_TOTAL_EXPENSE_YEAR + " INTEGER NOT NULL,"
            + KEY_BALANCE_YEAR + " INTEGER NOT NULL" + ")";

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
            + KEY_INCOME_YEAR + " INTEGER NOT NULL,"
            + KEY_SALARY_OTHER + " INTEGER NOT NULL" + ")";

    // Deduction table create statement
    private static final String CREATE_TABLE_DEDUCTION = "CREATE TABLE "
            + TABLE_DEDUCTION + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PROFILE_ID + " INTEGER NOT NULL,"
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
     * Add a new profile
     */
    public long insertRowProfile() {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE, "new Profile");
        initialValues.put(KEY_TOTAL_INCOME_YEAR, 0);
        initialValues.put(KEY_TOTAL_EXPENSE_YEAR, 0);
        initialValues.put(KEY_BALANCE_YEAR, 0);

        // Insert it into the database.
        return db.insert(TABLE_PROFILE, null, initialValues);
    }

    /**
     * Add a new expense
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
        db.insert(TABLE_EXPENSE, null, initialValues);
    }

    /**
     * Add a new income
     *
     * @param incomeType name of income in string
     * @param incomeYear income in year in double
     */
    public void insertRowIncome(String incomeType, double incomeYear) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE_ID, MainActivity.long_ProfileId);
        initialValues.put(KEY_INCOME_TYPE, incomeType);
        initialValues.put(KEY_INCOME_YEAR, incomeYear);
        initialValues.put(KEY_SALARY_OTHER, OTHER_INCOME);

        // Insert it into the database.
        db.insert(TABLE_INCOME, null, initialValues);
    }

    /**
     * Add a new salary
     *
     * @param salaryYear salary in year in double
     */
    public void insertRowSalary(double salaryYear) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PROFILE_ID, MainActivity.long_ProfileId);
        initialValues.put(KEY_INCOME_TYPE, "Salary gross");
        initialValues.put(KEY_INCOME_YEAR, salaryYear);
        initialValues.put(KEY_SALARY_OTHER, SALARY);

        // Insert it into the database.
        db.insert(TABLE_INCOME, null, initialValues);
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
        initialValues.put(KEY_DEDUCTION_TYPE, deductionType);
        initialValues.put(KEY_PERCENTAGE, percentage);

        // Insert it into the database.
        db.insert(TABLE_DEDUCTION, null, initialValues);
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
    // delete rows

    /**
     * delete a Profile with profileID
     *
     * @param profileId ID of the Profile in long
     */
    public void deleteProfile(long profileId) {
        String where = KEY_PROFILE_ID + "=" + profileId;
        db.delete(TABLE_PROFILE, where, null);
        db.delete(TABLE_EXPENSE, where, null);
        db.delete(TABLE_INCOME, where, null);
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
    }

    /**
     * Update the total income/year
     *
     * @param totalIncomeYear total of income/year in double
     */
    public void updateRowProfileTotalIncomeYear(double totalIncomeYear) {
        String where = KEY_PROFILE_ID + "=" + MainActivity.long_ProfileId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TOTAL_INCOME_YEAR, totalIncomeYear);

        // Insert it into the database.
        db.update(TABLE_PROFILE, newValues, where, null);
    }

    /**
     * Update the total expense/year
     *
     * @param totalExpenseYear total of expense/year in double
     */
    public void updateRowProfileTotalExpenseYear(double totalExpenseYear) {
        String where = KEY_PROFILE_ID + "=" + MainActivity.long_ProfileId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TOTAL_EXPENSE_YEAR, totalExpenseYear);

        // Insert it into the database.
        db.update(TABLE_PROFILE, newValues, where, null);
    }

    /**
     * Update the balance/year
     *
     * @param BalanceYear total of income/year in double
     */
    public void updateRowProfileBalanceYear(double BalanceYear) {
        String where = KEY_PROFILE_ID + "=" + MainActivity.long_ProfileId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_BALANCE_YEAR, BalanceYear);

        // Insert it into the database.
        db.update(TABLE_PROFILE, newValues, where, null);
    }



    /**
     * update the cost
     *
     * @param costId      id of cost in long
     * @param expenseType name of cost in string
     * @param expenseYear cost in year in double
     */
    public void updateRowExpense(long costId, String expenseType, double expenseYear) {
        String where = KEY_PROFILE_ID + "=" + costId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_EXPENSE_TYPE, expenseType);
        newValues.put(KEY_EXPENSE_YEAR, expenseYear);

        // Insert it into the database.
        db.update(TABLE_EXPENSE, newValues, where, null);
    }

    /**
     * update the income
     *
     * @param incomeId   id of income in long
     * @param incomeType name of income in string
     * @param incomeYear income in year in double
     */
    public void updateRowIncome(long incomeId, String incomeType, double incomeYear) {
        String where = KEY_PROFILE_ID + "=" + incomeId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_INCOME_TYPE, incomeType);
        newValues.put(KEY_INCOME_YEAR, incomeYear);

        // Insert it into the database.
        db.update(TABLE_INCOME, newValues, where, null);
    }

    /**
     * update the deduction
     *
     * @param deductionId   id of deduction in long
     * @param deductionType name of deduction in string
     * @param percentage    deduction in double
     */
    public void updateRowDeduction(long deductionId, String deductionType, double percentage) {
        String where = KEY_PROFILE_ID + "=" + deductionId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DEDUCTION_TYPE, deductionType);
        newValues.put(KEY_PERCENTAGE, percentage);

        // Insert it into the database.
        db.update(TABLE_DEDUCTION, newValues, where, null);
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
    public Cursor getAllRowsOtherIncome() {
        String where= KEY_PROFILE_ID + "=" +MainActivity.long_ProfileId + " AND " + KEY_SALARY_OTHER + "=" + SEARCH_OTHER_INCOME;
        Cursor c = db.query(true, TABLE_INCOME, ALL_KEYS_INCOME,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Return salary in the database from table income.
     *
     * @return Return cursor with salary in the database from table income.
     */
    public Cursor getSalary() {
        String where= KEY_PROFILE_ID + "=" +MainActivity.long_ProfileId + " AND " + KEY_SALARY_OTHER + "=" + SEARCH_SALARY;
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
        String where= KEY_PROFILE_ID + "=" +MainActivity.long_ProfileId;
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
        String where= KEY_PROFILE_ID + "=" +MainActivity.long_ProfileId;
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
    //-----------------------------------------------------------------------------------------------------------------------------------------
    // check Methods

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

    /**
     * Checks if income already exists for profile
     *
     * @return true if income already exists
     */
    public boolean checkIncomeExists() {
        String where= KEY_PROFILE_ID + "=" +MainActivity.long_ProfileId;
        boolean exists;
        Cursor c = db.query(true, TABLE_INCOME, ALL_KEYS_INCOME,
                where, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    /**
     * Checks if other income already exists for profile
     *
     * @return true if other income already exists
     */
    public boolean checkOtherIncomeExists() {
        String where= KEY_PROFILE_ID + "=" +MainActivity.long_ProfileId + " AND " + KEY_SALARY_OTHER + "=" + SEARCH_OTHER_INCOME;
        boolean exists;
        Cursor c = db.query(true, TABLE_INCOME, ALL_KEYS_INCOME,
                where, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    /**
     * Checks if salary already exists for profile
     *
     * @return true if salary already exists
     */
    public boolean checkSalaryExists() {
        String where= KEY_PROFILE_ID + "=" +MainActivity.long_ProfileId + " AND " + KEY_SALARY_OTHER + "=" + SEARCH_SALARY;
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
        String where= KEY_PROFILE_ID + "=" +MainActivity.long_ProfileId;
        boolean exists;
        Cursor c = db.query(true, TABLE_EXPENSE, ALL_KEYS_EXPENSE,
                where, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    /**
     * Checks if deduction already exists for profile
     *
     * @return true if deduction already exists
     */
    public boolean checkDeductionExists() {
        String where= KEY_PROFILE_ID + "=" +MainActivity.long_ProfileId;
        boolean exists;
        Cursor c = db.query(true, TABLE_DEDUCTION, ALL_KEYS_DEDUCTION,
                where, null, null, null, null, null);

        exists = c.moveToFirst();
        c.close();
        return exists;
    }

    // check Methods
    //-----------------------------------------------------------------------------------------------------------------------------------------


}
