package com.example.leand.bilanztracker.DatabaseHelper;

import android.database.Cursor;

import com.example.leand.bilanztracker.Activitys.MainActivity;

/**
 * This class help's to get the right value out of the Database, with a cursor
 */

public class GetColumnHelper {
    private Cursor cursor;
    private long long_ProfileId;

    // Declaration
    //----------------------------------------------------------------------------------------------
    // Constructor

    /**
     * Constructor needs cursor to get the right column to get data
     *
     * @param cursor cursor of columns to get the data
     */
    public GetColumnHelper(Cursor cursor) {
        this.cursor = cursor;
        long_ProfileId = MainActivity.long_ProfileId;
    }

    // Constructor
    //----------------------------------------------------------------------------------------------
    // setter

    /**
     * Constructor needs cursor to get the right column to get data
     *
     * @param cursor cursor of columns to get the data
     */
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
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
        return cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_PROFILE));
    }

    /**
     * get the total income/year of the profile
     *
     * @return total income/year in double
     */
    public double getProfileTotalIncomeYear() {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_TOTAL_INCOME_YEAR));
    }

    /**
     * get the total expense/year of the profile
     *
     * @return total expense/year in double
     */
    public double getProfileTotalExpenseYear() {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_TOTAL_EXPENSE_YEAR));
    }

    /**
     * get balance/year of the profile
     *
     * @return balance/year in double
     */
    public double getProfileBalanceYear() {
        return cursor.getDouble(cursor.getColumnIndexOrThrow(DBAdapter.KEY_BALANCE_YEAR));
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
            return cursor.getString(cursor.getColumnIndexOrThrow(DBAdapter.KEY_CURRENCY));

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
     * @return total income/year in double
     */
    public Double getTotalIncomeYear() {
        if (MainActivity.myDbMain.checkIncomeExists()) {
            double totalIncome = 0.0;

            totalIncome += getTotalOtherIncomeYear();

            totalIncome += getSalaryNetYear();

            MainActivity.myDbMain.updateRowProfileTotalIncomeYear(totalIncome);
            return totalIncome;

        } else {
            return 0.0;
        }
    }

    /**
     * get total otherIncome/year of the profile
     *
     * @return total otherIncome/year in double
     */
    public Double getTotalOtherIncomeYear() {
        if (MainActivity.myDbMain.checkOtherIncomeExists()) {
            double totalOtherIncome = 0.0;
            Cursor cursor_OtherIncome = MainActivity.myDbMain.getAllRowsOtherIncome();

            do {
                totalOtherIncome += cursor_OtherIncome.getDouble(cursor_OtherIncome.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_YEAR));
            } while (cursor_OtherIncome.moveToNext());

            return totalOtherIncome;

        } else {
            return 0.0;
        }
    }

    /**
     * get salaryNet/year of the profile
     *
     * @return total salaryNet/year in double
     */
    public Double getSalaryNetYear() {
        double salaryGross;
        double salaryNet;
        double totalDeduction = getTotalDeduction();

        salaryGross = getSalaryGrossYear();
        salaryNet = salaryGross - (salaryGross * (totalDeduction / 100));
        return salaryNet;
    }

    /**
     * get salaryGross/year of the profile
     *
     * @return total salaryGross/year in double
     */
    public Double getSalaryGrossYear() {
        if (MainActivity.myDbMain.checkSalaryExists()) {
            Cursor cursor_Salary = MainActivity.myDbMain.getSalary();

            return cursor_Salary.getDouble(cursor_Salary.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_YEAR));

        } else {
            return 0.0;
        }
    }

    // getter table income
    //----------------------------------------------------------------------------------------------
    // getter table expense

    /**
     * get total expense/year of the profile
     *
     * @return total expense/year in double
     */
    public Double getTotalExpenseYear() {
        if (MainActivity.myDbMain.checkIncomeExists()) {
            double totalIncome = 0.0;
            Cursor cursor_OtherIncome = MainActivity.myDbMain.getAllRowsOtherIncome();

            if (MainActivity.myDbMain.checkOtherIncomeExists()) {
                do {
                    totalIncome += cursor_OtherIncome.getDouble(cursor_OtherIncome.getColumnIndexOrThrow(DBAdapter.KEY_INCOME_YEAR));
                } while (cursor_OtherIncome.moveToNext());
            }


            MainActivity.myDbMain.updateRowProfileTotalIncomeYear(totalIncome);
            return totalIncome;

        } else {
            return 0.0;
        }
    }

    // getter table expense
    //----------------------------------------------------------------------------------------------
    // getter table deduction

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
            return 0.0;
        }
    }

    // getter table deduction
    //----------------------------------------------------------------------------------------------
    // getter general

    public Double getBalanceYear(){
        return getTotalIncomeYear()-getTotalExpenseYear();
    }


    // getter general
    //----------------------------------------------------------------------------------------------
    // end
}
