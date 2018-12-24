package com.example.leand.bilanztracker.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class OverviewActivity extends AppCompatActivity {
    EditText editText_OverviewActivity_Title;
    TextView textView_OverviewActivity_SavedTitle, textView_OverviewActivity_GrossYear, textView_OverviewActivity_GrossMonth,
            textView_OverviewActivity_NetYear, textView_OverviewActivity_NetMonth, textView_OverviewActivity_OtherIncomeYear,
            textView_OverviewActivity_OtherIncomeMonth, textView_OverviewActivity_ExpensesYear, textView_OverviewActivity_ExpensesMonth,
            textView_OverviewActivity_BalanceYear, textView_OverviewActivity_BalanceMonth;

    GetColumnHelper getColumnHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        getColumnHelper = new GetColumnHelper(MainActivity.myDbMain.getRowProfile());

        editText_OverviewActivity_Title = findViewById(R.id.editText_OverviewActivity_Title);
        textView_OverviewActivity_SavedTitle = findViewById(R.id.textView_OverviewActivity_SavedTitle);
        textView_OverviewActivity_GrossYear = findViewById(R.id.textView_OverviewActivity_GrossYear);
        textView_OverviewActivity_GrossMonth = findViewById(R.id.textView_OverviewActivity_GrossMonth);
        textView_OverviewActivity_NetYear = findViewById(R.id.textView_OverviewActivity_NetYear);
        textView_OverviewActivity_NetMonth = findViewById(R.id.textView_OverviewActivity_NetMonth);
        textView_OverviewActivity_OtherIncomeYear = findViewById(R.id.textView_OverviewActivity_OtherIncomeYear);
        textView_OverviewActivity_OtherIncomeMonth = findViewById(R.id.textView_OverviewActivity_OtherIncomeMonth);
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

    // List Methods
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        String currency = getColumnHelper.getCurrency();

        getColumnHelper.setCursor(MainActivity.myDbMain.getRowProfile());
        editText_OverviewActivity_Title.setText(getColumnHelper.getProfileTitle());
        textView_OverviewActivity_SavedTitle.setText(getColumnHelper.getProfileTitle());

        textView_OverviewActivity_GrossYear.setText(getColumnHelper.getSalaryGrossYear().toString() + " " + currency);
        textView_OverviewActivity_GrossMonth.setText((getColumnHelper.getSalaryGrossYear().doubleValue() / 12.0) + " " + currency);
        textView_OverviewActivity_NetYear.setText(getColumnHelper.getSalaryNetYear().toString() + " " + currency);
        textView_OverviewActivity_NetMonth.setText((getColumnHelper.getSalaryNetYear().doubleValue() / 12.0) + " " + currency);
        textView_OverviewActivity_OtherIncomeYear.setText(getColumnHelper.getTotalOtherIncomeYear().toString() + " " + currency);
        textView_OverviewActivity_OtherIncomeMonth.setText((getColumnHelper.getTotalOtherIncomeYear().doubleValue() / 12.0) + " " + currency);
        textView_OverviewActivity_ExpensesYear.setText(getColumnHelper.getTotalExpenseYear().toString() + " " + currency);
        textView_OverviewActivity_ExpensesMonth.setText((getColumnHelper.getTotalExpenseYear().doubleValue() / 12.0) + " " + currency);
        textView_OverviewActivity_BalanceYear.setText(getColumnHelper.getBalanceYear().toString() + " " + currency);
        textView_OverviewActivity_BalanceMonth.setText((getColumnHelper.getBalanceYear().doubleValue() / 12.0) + " " + currency);
    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------
    // End
}
