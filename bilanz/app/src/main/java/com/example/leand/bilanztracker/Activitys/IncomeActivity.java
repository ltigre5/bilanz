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

public class IncomeActivity extends BaseActivity {
    private TextView textView_IncomeActivity_TotalIncomeGrossYear, textView_IncomeActivity_TotalIncomeNetYear,
            textView_IncomeActivity_ActualProfile;

    private ListView listView_IncomeActivity;
    private ListViewAdapter listViewAdapter;

    private Toolbar toolbar;
    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;

    public static long long_IncomeId;
    public static boolean boolean_NewIncome;

    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        //Set Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        //Initialize Activity items
        textView_IncomeActivity_TotalIncomeGrossYear = findViewById(R.id.textView_IncomeActivity_TotalIncomeGrossYear);
        textView_IncomeActivity_TotalIncomeNetYear = findViewById(R.id.textView_IncomeActivity_TotalIncomeNetYear);
        textView_IncomeActivity_ActualProfile=findViewById(R.id.textView_IncomeActivity_ActualProfile);
        listView_IncomeActivity = findViewById(R.id.listView_IncomeActivity);

        listViewAdapter = new ListViewAdapter(this);
        getColumnHelper = new GetColumnHelper();
        generalFormatter = new GeneralFormatter();

        createIncomeListView();

        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    public void onClickAddNewIncome(View view) {
        Intent intent = new Intent(this, EditIncomeActivity.class);
        boolean_NewIncome = true;
        long_IncomeId = 0;
        startActivity(intent);
        finish();
    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    // Lifecycle

    @Override
    public void onRestart() {
        super.onRestart();
        displayItemsOnActivity();
    }

    // Lifecycle
    // ---------------------------------------------------------------------------------------------
    // List Methods

    //Creates ArrayList of all profiles for ListView and adds an onClick Method which opens EditIncomeActivity
    public void createIncomeListView() {
        listView_IncomeActivity.setAdapter(listViewAdapter.getIncomeListViewAdapter());

        //by clicking of Item get Database-ID of Position and open EditIncomeActivity and send ID
        listView_IncomeActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get ID of selected Item from Database
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                long_IncomeId = cursor.getLong(cursor.getColumnIndexOrThrow(DBAdapter.KEY_ID));
                cursor.close();
                boolean_NewIncome = false;

                Intent intent = new Intent(getApplicationContext(), EditIncomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // List Methods
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        toolbar.setSubtitle(getString(R.string.subtitle_BalanceMonth) +" "+ generalFormatter.getCurrencyFormatMonth(getColumnHelper.getBalanceYearDouble()));
        textView_IncomeActivity_ActualProfile.setText(getColumnHelper.getProfileTitle());

        listView_IncomeActivity.setAdapter(listViewAdapter.getIncomeListViewAdapter());

        textView_IncomeActivity_TotalIncomeGrossYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getTotalIncomeGrossYearDouble()));
        textView_IncomeActivity_TotalIncomeNetYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getTotalIncomeNetYearDouble()));
    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
