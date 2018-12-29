package com.example.leand.bilanztracker.Activitys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class OverviewActivity extends BaseActivity {
    private Toolbar toolbar;

    EditText editText_OverviewActivity_Title;
    TextView textView_OverviewActivity_SavedTitle, textView_OverviewActivity_GrossYear, textView_OverviewActivity_GrossMonth,
            textView_OverviewActivity_NetYear, textView_OverviewActivity_NetMonth, textView_OverviewActivity_ExpensesYear, textView_OverviewActivity_ExpensesMonth,
            textView_OverviewActivity_BalanceYear, textView_OverviewActivity_BalanceMonth;

    GetColumnHelper getColumnHelper;


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
        getColumnHelper = new GetColumnHelper(MainActivity.myDbMain.getRowProfile());

        //definition of Items in Activity
        editText_OverviewActivity_Title = findViewById(R.id.editText_OverviewActivity_Title);
        textView_OverviewActivity_SavedTitle = findViewById(R.id.textView_OverviewActivity_SavedTitle);
        textView_OverviewActivity_GrossYear = findViewById(R.id.textView_OverviewActivity_GrossYear);
        textView_OverviewActivity_GrossMonth = findViewById(R.id.textView_OverviewActivity_GrossMonth);
        textView_OverviewActivity_NetYear = findViewById(R.id.textView_OverviewActivity_NetYear);
        textView_OverviewActivity_NetMonth = findViewById(R.id.textView_OverviewActivity_NetMonth);
        textView_OverviewActivity_ExpensesYear = findViewById(R.id.textView_OverviewActivity_ExpensesYear);
        textView_OverviewActivity_ExpensesMonth = findViewById(R.id.textView_OverviewActivity_ExpensesMonth);
        textView_OverviewActivity_BalanceYear = findViewById(R.id.textView_OverviewActivity_BalanceYear);
        textView_OverviewActivity_BalanceMonth = findViewById(R.id.textView_OverviewActivity_BalanceMonth);

        displayItemsOnActivity();
    }

    public void onClickSaveProfileTitle(View view) {
        MainActivity.myDbMain.updateProfileTitle(editText_OverviewActivity_Title.getText().toString());
        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    //

    // List Methods
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        getColumnHelper.setCursor(MainActivity.myDbMain.getRowProfile());
        editText_OverviewActivity_Title.setText(getColumnHelper.getProfileTitle());
        editText_OverviewActivity_Title.setSelection(editText_OverviewActivity_Title.getText().length());
        textView_OverviewActivity_SavedTitle.setText(getColumnHelper.getProfileTitle());

        textView_OverviewActivity_GrossYear.setText(getColumnHelper.getCurrencyFormatWithCurrency(
                        getColumnHelper.getTotalIncomeGrossYearDouble()));

        textView_OverviewActivity_GrossMonth.setText(getColumnHelper.getCurrencyFormatWithCurrency(
                        getColumnHelper.getMonthValueDouble(getColumnHelper.getTotalIncomeGrossYearDouble())));

        textView_OverviewActivity_NetYear.setText(getColumnHelper.getCurrencyFormatWithCurrency(
                getColumnHelper.getTotalIncomeNetYearDouble()));

        textView_OverviewActivity_NetMonth.setText(getColumnHelper.getCurrencyFormatWithCurrency(
                        getColumnHelper.getMonthValueDouble(getColumnHelper.getTotalIncomeNetYearDouble())));

        textView_OverviewActivity_ExpensesYear.setText(getColumnHelper.getCurrencyFormatWithCurrency(
                getColumnHelper.getTotalExpenseYearDouble()));

        textView_OverviewActivity_ExpensesMonth.setText(getColumnHelper.getCurrencyFormatWithCurrency(
                        getColumnHelper.getMonthValueDouble(getColumnHelper.getTotalExpenseYearDouble())));

        textView_OverviewActivity_BalanceYear.setText(getColumnHelper.getCurrencyFormatWithCurrency(
                getColumnHelper.getBalanceYearDouble()));

        textView_OverviewActivity_BalanceMonth.setText(getColumnHelper.getCurrencyFormatWithCurrency(
                        getColumnHelper.getMonthValueDouble(getColumnHelper.getBalanceYearDouble())));

        toolbar.setSubtitle(MainActivity.string_actualProfile);

    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------
    // End


    // Menu
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
