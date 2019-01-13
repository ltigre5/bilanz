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
     * Get the OverviewListViewAdapter
     *
     * @return OverviewListViewAdapter
     */
    public OverviewCursorAdapter getOverviewListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsExpense();
        return new OverviewCursorAdapter(context, cursor, 0);
    }

    /**
     * Get the ProfileListViewAdapter
     *
     * @return ProfileListViewAdapter
     */
    public ProfileCursorAdapter getProfileListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsProfile();
        return new ProfileCursorAdapter(context, cursor, 0);
    }

    /**
     * Get the DeductionListViewAdapter
     *
     * @return DeductionListViewAdapter
     */
    public DeductionCursorAdapter getDeductionListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsDeduction();
        return new DeductionCursorAdapter(context, cursor, 0);
    }

    /**
     * Get the IncomeListViewAdapter
     *
     * @return IncomeListViewAdapter
     */
    public IncomeCursorAdapter getIncomeListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsIncome();
        return new IncomeCursorAdapter(context, cursor, 0);
    }

    /**
     * Get the ExpenseListViewAdapter
     *
     * @return ExpenseListViewAdapter
     */
    public ExpenseCursorAdapter getExpenseListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsExpense();
        return new ExpenseCursorAdapter(context, cursor, 0);
    }

    // GetAdapters
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End

}
