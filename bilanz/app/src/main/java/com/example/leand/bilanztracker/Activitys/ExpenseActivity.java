package com.example.leand.bilanztracker.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leand.bilanztracker.DatabaseHelper.DBAdapter;
import com.example.leand.bilanztracker.DatabaseHelper.GeneralFormatter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.ListViewHelper.ListViewAdapter;
import com.example.leand.bilanztracker.R;

public class ExpenseActivity extends BaseActivity {
    private TextView textView_ExpenseActivity_TotalExpenseYear, textView_ExpenseActivity_TotalExpenseMonth;

    private ListView listView_ExpenseActivity;
    private ListViewAdapter listViewAdapter;

    private Toolbar toolbar;
    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;

    public static long long_ExpenseId;
    public static boolean boolean_NewExpense;

    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        //Set Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        //definition of Items in Activity
        textView_ExpenseActivity_TotalExpenseYear = findViewById(R.id.textView_ExpenseActivity_TotalExpenseYear);
        textView_ExpenseActivity_TotalExpenseMonth = findViewById(R.id.textView_ExpenseActivity_TotalExpenseMonth);
        listView_ExpenseActivity = findViewById(R.id.listView_ExpenseActivity);

        listViewAdapter = new ListViewAdapter(this);
        getColumnHelper = new GetColumnHelper();
        generalFormatter = new GeneralFormatter();

        createExpenseListView();

        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    public void onClickAddExpense(View view) {
        Intent intent = new Intent(this, EditExpenseActivity.class);
        boolean_NewExpense = true;
        long_ExpenseId = 0;
        startActivity(intent);
    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    // List Methods

    //Creates ArrayList of all profiles for ListView and adds an onClick Method which opens OverviewActivity
    public void createExpenseListView() {
        listView_ExpenseActivity.setAdapter(listViewAdapter.getExpenseListViewAdapter());

        //by clicking of Item get Database-ID of Position and open EditItemActivity and send ID
        listView_ExpenseActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get ID of selected Item from Database
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                long_ExpenseId = cursor.getLong(cursor.getColumnIndexOrThrow(DBAdapter.KEY_ID));
                boolean_NewExpense = false;

                Intent intent = new Intent(getApplicationContext(), EditExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    // List Methods
    // ---------------------------------------------------------------------------------------------
    // LifeCycle Methods


    //
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        toolbar.setSubtitle("Balance/month: "+ generalFormatter.getCurrencyFormatMonth(getColumnHelper.getBalanceYearDouble()));

        listView_ExpenseActivity.setAdapter(listViewAdapter.getExpenseListViewAdapter());
        textView_ExpenseActivity_TotalExpenseYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getTotalExpenseYearDouble()));
        textView_ExpenseActivity_TotalExpenseMonth.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getTotalExpenseYearDouble()));

    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
