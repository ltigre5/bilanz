package com.example.leand.bilanztracker.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leand.bilanztracker.DatabaseHelper.DBAdapter;
import com.example.leand.bilanztracker.DatabaseHelper.GeneralFormatter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.ListViewHelper.ListViewAdapter;
import com.example.leand.bilanztracker.R;

public class OverviewActivity extends BaseActivity {
    private EditText editText_OverviewActivity_Title;
    private TextView textView_OverviewActivity_GrossYear, textView_OverviewActivity_GrossMonth,
            textView_OverviewActivity_NetYear, textView_OverviewActivity_NetMonth, textView_OverviewActivity_ExpensesYear, textView_OverviewActivity_ExpensesMonth,
            textView_OverviewActivity_BalanceYear, textView_OverviewActivity_BalanceMonth;

    private ListView listView_OverviewActivity;
    private ListViewAdapter listViewAdapter;

    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;
    private Toolbar toolbar;

    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //Set Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        //Initialize classes
        getColumnHelper = new GetColumnHelper();
        generalFormatter = new GeneralFormatter();
        listViewAdapter = new ListViewAdapter(this);

        //Initialize Activity items
        editText_OverviewActivity_Title = findViewById(R.id.editText_OverviewActivity_Title);
        textView_OverviewActivity_GrossYear = findViewById(R.id.textView_OverviewActivity_GrossYear);
        textView_OverviewActivity_GrossMonth = findViewById(R.id.textView_OverviewActivity_GrossMonth);
        textView_OverviewActivity_NetYear = findViewById(R.id.textView_OverviewActivity_NetYear);
        textView_OverviewActivity_NetMonth = findViewById(R.id.textView_OverviewActivity_NetMonth);
        textView_OverviewActivity_ExpensesYear = findViewById(R.id.textView_OverviewActivity_ExpensesYear);
        textView_OverviewActivity_ExpensesMonth = findViewById(R.id.textView_OverviewActivity_ExpensesMonth);
        textView_OverviewActivity_BalanceYear = findViewById(R.id.textView_OverviewActivity_BalanceYear);
        textView_OverviewActivity_BalanceMonth = findViewById(R.id.textView_OverviewActivity_BalanceMonth);
        listView_OverviewActivity=findViewById(R.id.listView_OverviewActivity);

        createExpenseListView();

        displayItemsOnActivity();
    }

    // OnCreate
    // ---------------------------------------------------------------------------------------------
    // onClick Methods

    public void onClickSaveProfileTitle(View view) {
        if (editText_OverviewActivity_Title.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.toast_EnterATitle),
                    Toast.LENGTH_LONG).show();
        } else {
            MainActivity.myDbMain.updateProfileTitle(editText_OverviewActivity_Title.getText().toString());
            displayItemsOnActivity();
        }
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
    public void createExpenseListView() {
        listView_OverviewActivity.setAdapter(listViewAdapter.getOverviewListViewAdapter());

        //by clicking of Item get Database-ID of Position and open EditIncomeActivity and send ID
        listView_OverviewActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get ID of selected Item from Database
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                ExpenseActivity.long_ExpenseId = cursor.getLong(cursor.getColumnIndexOrThrow(DBAdapter.KEY_ID));
                cursor.close();
                ExpenseActivity.boolean_NewExpense = false;

                Intent intent = new Intent(getApplicationContext(), EditExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    // List Methods
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        getColumnHelper.setCursor(MainActivity.myDbMain.getRowProfile());
        editText_OverviewActivity_Title.setText(getColumnHelper.getProfileTitle());
        editText_OverviewActivity_Title.setSelection(editText_OverviewActivity_Title.getText().length());

        listView_OverviewActivity.setAdapter(listViewAdapter.getOverviewListViewAdapter());
        textView_OverviewActivity_GrossYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getTotalIncomeGrossYearDouble()));
        textView_OverviewActivity_GrossMonth.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getTotalIncomeGrossYearDouble()));
        textView_OverviewActivity_NetYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getTotalIncomeNetYearDouble()));
        textView_OverviewActivity_NetMonth.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getTotalIncomeNetYearDouble()));
        textView_OverviewActivity_ExpensesYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getTotalExpenseYearDouble()));
        textView_OverviewActivity_ExpensesMonth.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getTotalExpenseYearDouble()));
        textView_OverviewActivity_BalanceYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getBalanceYearDouble()));
        textView_OverviewActivity_BalanceMonth.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getBalanceYearDouble()));

        toolbar.setSubtitle(getColumnHelper.getProfileTitle());
    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------
    // End
}
