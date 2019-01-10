package com.example.leand.bilanztracker.DatabaseHelper;

import android.database.Cursor;

import com.example.leand.bilanztracker.Activitys.MainActivity;

import java.text.DecimalFormat;

/**
 * This class help's to get the right value out of the Database, with a cursor
 */

public class GetColumnHelper {
    private Cursor cursor;

    // Declaration
    //----------------------------------------------------------------------------------------------
    // Constructor

    /**
     * Constructor needs cursor to get the right column to get data, only needed to get the ID
     *
     * @param cursor cursor of columns to get the data
     */
    public GetColumnHelper(Cursor cursor) {
        this.cursor = cursor;
        getCurrency();

    }

    /**
     * Constructor empty
     */
    public GetColumnHelper() {
        getCurrency();

    }

    // Constructor
    //----------------------------------------------------------------------------------------------
    // setter

    /**
     * Constructor needs cursor to get the right column to get data, only needed to get the ID
     *
     * @param cursor cursor of columns to get the data
     */
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        getCurrency();
    }


    // setter
    //----------------------------------------------------------------------------------------------
    // getter table profile

    /**
     * get the title of the profile
     *
     * @return title of profile in string
     */
    public String getProfileTitle() {
        Cursor cursor = MainActivity.myDbMain.getRowProfile();
        String profileTitle=cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_PROFILE));
        cursor.close();
        return profileTitle;
    }

    // getter table profile
    //----------------------------------------------------------------------------------------------
    // getter table currency

    /**
     * get currency of the profile
     *
     * @return currency in string
     */
    public String getCurrency() {
        if (MainActivity.myDbMain.checkCurrencyExists()) {
            Cursor cursor = MainActivity.myDbMain.getAllCurrency();
            String currency = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_CURRENCY));
            return currency;

        } else {
            return "";
        }
    }

    // getter table currency
    //----------------------------------------------------------------------------------------------
    // getter table income

    /**
     * get total income/year of the profile
     *
     * @return total income/year in Double
     */
    public Double getTotalIncomeGrossYearDouble() {
        if (MainActivity.myDbMain.checkIncomeExists()) {
            double totalIncomeGross = 0.0;

            Cursor cursor = MainActivity.myDbMain.getAllRowsIncome();

            do {
                totalIncomeGross += cursor.getInt(cursor.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_YEAR_GROSS));
            } while (cursor.moveToNext());

            cursor.close();

            return totalIncomeGross;

        } else {
            return 0.00;
        }
    }

    /**
     * get total income/year of the profile
     *
     * @return total income/year in Double
     */
    public Double getTotalIncomeNetYearDouble() {
        if (MainActivity.myDbMain.checkIncomeExists()) {
            double totalIncomeNet = 0.0;

            Cursor cursor = MainActivity.myDbMain.getAllRowsIncome();

            do {
                totalIncomeNet += cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_YEAR_NET));
            } while (cursor.moveToNext());

            cursor.close();

            return totalIncomeNet;

        } else {
            return 0.00;
        }
    }


    //----------------------------------------------------------------------------------------------

    /**
     * get the title of the income
     *
     * @return title of the income in string
     */
    public String getIncomeTitle() {
        Cursor cursor = MainActivity.myDbMain.getRowIncome();
        String incomeTitle = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_TYPE));
        cursor.close();
        return incomeTitle;
    }

    /**
     * get incomeGross/year of the profile
     *
     * @return total incomeGross/year in Double
     */
    public Double getIncomeGrossYearDouble() {
        if (MainActivity.myDbMain.checkIncomeExists()) {
            Cursor cursor = MainActivity.myDbMain.getRowIncome();
            Double incomeGross = cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_YEAR_GROSS));
            cursor.close();
            return incomeGross;

        } else {
            return 0.00;
        }
    }

    /**
     * get incomeGross/year of the profile
     *
     * @return total incomeGross/year in Double
     */
    public Double getIncomeNetYearDouble() {
        if (MainActivity.myDbMain.checkIncomeExists()) {
            Cursor cursor = MainActivity.myDbMain.getRowIncome();
            Double incomeNet = cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_YEAR_NET));
            cursor.close();
            return incomeNet;

        } else {
            return 0.00;
        }
    }

    // getter table income
    //----------------------------------------------------------------------------------------------
    // getter table expense

    /**
     * get the title of the expense
     *
     * @return title of expense in string
     */
    public String getExpenseTitle() {
        Cursor cursor = MainActivity.myDbMain.getRowExpense();
        String expenseTitle = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_EXPENSE_TYPE));
        cursor.close();
        return expenseTitle;
    }

    /**
     * get the expense
     *
     * @return expense in Double
     */
    public Double getExpenseYearDouble() {
        Cursor cursor = MainActivity.myDbMain.getRowExpense();
        Double expense = cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_EXPENSE_YEAR));
        cursor.close();
        return expense;
    }

    /**
     * get total expense/year of the profile
     *
     * @return total expense/year in Double
     */
    public Double getTotalExpenseYearDouble() {
        if (MainActivity.myDbMain.checkExpenseExists()) {
            double totalExpenses = 0.0;
            Cursor cursor = MainActivity.myDbMain.getAllRowsExpense();

            do {
                totalExpenses += cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_EXPENSE_YEAR));
            } while (cursor.moveToNext());

            cursor.close();

            return totalExpenses;

        } else {
            return 0.00;
        }
    }

    // getter table expense
    //----------------------------------------------------------------------------------------------
    // getter table deduction

    /**
     * get the title of the deduction
     *
     * @return title of deduction in string
     */
    public String getDeductionTitle() {
        Cursor cursor = MainActivity.myDbMain.getRowDeduction();
        String deductionTitle = cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_DEDUCTION_TYPE));
        cursor.close();
        return deductionTitle;
    }

    /**
     * get the percentage of the deduction
     *
     * @return percentage of deduction in Double
     */
    public Double getDeductionPercentageDouble() {
        Cursor cursor = MainActivity.myDbMain.getRowDeduction();
        Double deductionPercentage = cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_PERCENTAGE));
        cursor.close();
        return deductionPercentage;
    }

    /**
     * get the percentage of the deduction
     *
     * @return percentage of deduction in String
     */
    public String getDeductionPercentageString() {
        return getDeductionPercentageDouble().toString();
    }

    /**
     * get the percentage of the deduction
     *
     * @return percentage of deduction in String with %
     */
    public String getDeductionPercentageStringPercent() {
        return getDeductionPercentageDouble() + "%";
    }

    /**
     * get total deduction of the profile
     *
     * @return total deduction in double
     */
    public Double getTotalDeduction() {
        double totalDeduction = 0;

        if (MainActivity.myDbMain.checkDeductionExists()) {
            Cursor cursor_Deduction = MainActivity.myDbMain.getAllRowsDeduction();
            do {
                totalDeduction += cursor_Deduction.getDouble(cursor_Deduction.getColumnIndexOrThrow(DBAdapter.KEY_PERCENTAGE));
            } while (cursor_Deduction.moveToNext());

            return totalDeduction;

        } else {
            return 0.00;
        }
    }

    // getter table deduction
    //----------------------------------------------------------------------------------------------
    // getter general

    /**
     * get total balance/year of the profile
     *
     * @return total balance/year in Double
     */
    public Double getBalanceYearDouble() {
        return getTotalIncomeNetYearDouble() - getTotalExpenseYearDouble();
    }


    /**
     * get the id
     *
     * @return id in long
     */
    public long getId() {
        return cursor.getLong(cursor.getColumnIndexOrThrow(DBAdapter.KEY_ID));
    }


    // getter general
    //----------------------------------------------------------------------------------------------
    // end
}
