package com.example.leand.bilanztracker.ListViewHelper;

import android.content.Context;
import android.database.Cursor;

import com.example.leand.bilanztracker.Activitys.MainActivity;

public class ListViewAdapter {
    private Context context;
    private Cursor cursor;

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    // get Cursor
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // GetAdapters

    /**
     * Get the ProfileListViewAdapter
     *
     * @return ProfileListViewAdapter
     */

    public ProfileCursorAdapter getProfileListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsProfile();
        ProfileCursorAdapter profileCursorAdapter = new ProfileCursorAdapter(context, cursor, 0);
        return profileCursorAdapter;
    }

    /**
     * Get the DeductionListViewAdapter
     *
     * @return DeductionListViewAdapter
     */

    public DeductionCursorAdapter getDeductionListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsDeduction();
        DeductionCursorAdapter deductionCursorAdapter = new DeductionCursorAdapter(context, cursor, 0);
        return deductionCursorAdapter;
    }

    /**
     * Get the IncomeListViewAdapter
     *
     * @return IncomeListViewAdapter
     */

    public IncomeCursorAdapter getIncomeListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsIncome();
        IncomeCursorAdapter incomeCursorAdapter = new IncomeCursorAdapter(context, cursor, 0);
        return incomeCursorAdapter;
    }

    /**
     * Get the ExpenseListViewAdapter
     *
     * @return ExpenseListViewAdapter
     */

    public ExpenseCursorAdapter getExpenseListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsExpense();
        ExpenseCursorAdapter expenseCursorAdapter = new ExpenseCursorAdapter(context, cursor, 0);
        return expenseCursorAdapter;
    }

    // GetAdapters
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End

}
