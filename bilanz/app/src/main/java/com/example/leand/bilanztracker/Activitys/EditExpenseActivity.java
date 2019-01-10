package com.example.leand.bilanztracker.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leand.bilanztracker.DatabaseHelper.GeneralFormatter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.EditTextFilter.InputFilterDecimal;
import com.example.leand.bilanztracker.R;

public class EditExpenseActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private EditText editText_EditExpenseActivity_Title, editText_EditExpenseActivity_Value, editText_EditExpenseActivity_RepeatedBy;
    private TextView textView_EditExpenseActivity_ExpenseDay, textView_EditExpenseActivity_ExpenseWeek,
            textView_EditExpenseActivity_ExpenseMonth, textView_EditExpenseActivity_ExpenseYear,textView_EditExpenseActivity_Currency;
    private Button button_EditExpenseActivity_DeleteExpense;
    private Spinner spinner_EditExpenseActivity_Every;

    private Toolbar toolbar;
    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;

    private String string_Every;
    private String SPINNER_YEAR, SPINNER_MONTH, SPINNER_WEEK, SPINNER_DAY;

    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        //Set Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        //Initialize Activity items
        editText_EditExpenseActivity_Title = findViewById(R.id.editText_EditExpenseActivity_Title);
        editText_EditExpenseActivity_Value = findViewById(R.id.editText_EditExpenseActivity_Value);
        editText_EditExpenseActivity_Value.setFilters(new InputFilter[]{new InputFilterDecimal(14, 2)});
        editText_EditExpenseActivity_RepeatedBy = findViewById(R.id.editText_EditExpenseActivity_RepeatedBy);
        textView_EditExpenseActivity_ExpenseDay = findViewById(R.id.textView_EditExpenseActivity_ExpenseDay);
        textView_EditExpenseActivity_ExpenseWeek = findViewById(R.id.textView_EditExpenseActivity_ExpenseWeek);
        textView_EditExpenseActivity_ExpenseMonth = findViewById(R.id.textView_EditExpenseActivity_ExpenseMonth);
        textView_EditExpenseActivity_ExpenseYear = findViewById(R.id.textView_EditExpenseActivity_ExpenseYear);
        textView_EditExpenseActivity_Currency=findViewById(R.id.textView_EditExpenseActivity_Currency);
        button_EditExpenseActivity_DeleteExpense = findViewById(R.id.button_EditExpenseActivity_DeleteExpense);
        spinner_EditExpenseActivity_Every = findViewById(R.id.spinner_EditExpenseActivity_Every);

        getColumnHelper = new GetColumnHelper();
        generalFormatter = new GeneralFormatter();

        SPINNER_YEAR = getResources().getString(R.string.every_year);
        SPINNER_MONTH = getResources().getString(R.string.every_month);
        SPINNER_WEEK = getResources().getString(R.string.every_week);
        SPINNER_DAY = getResources().getString(R.string.every_day);

        createSpinnerEvery();

        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    public void onClickDeleteExpense(View view) {
        if (!ExpenseActivity.boolean_NewExpense) {
            MainActivity.myDbMain.deleteExpense(ExpenseActivity.long_ExpenseId);

            Intent intent = new Intent(this, ExpenseActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClickSaveExpense(View view) {
        if (!editText_EditExpenseActivity_Title.getText().toString().equals("")
                && !editText_EditExpenseActivity_Value.getText().toString().equals("")) {

            String expenseTitle = editText_EditExpenseActivity_Title.getText().toString();
            double expense = Double.parseDouble(editText_EditExpenseActivity_Value.getText().toString());
            int repeatedBy = Integer.parseInt(editText_EditExpenseActivity_RepeatedBy.getText().toString());

            //calculate the expense by the chosen spinner state
            if (string_Every.equals(SPINNER_MONTH)) {
                expense = expense * (12 / repeatedBy);
            } else if (string_Every.equals(SPINNER_WEEK)) {
                expense = expense * (52 / repeatedBy);
            } else if (string_Every.equals(SPINNER_DAY)) {
                expense = expense * (365 / repeatedBy);
            }

            if (ExpenseActivity.boolean_NewExpense) {
                MainActivity.myDbMain.insertRowExpense(expenseTitle, expense);
                ExpenseActivity.boolean_NewExpense = false;
            } else {
                MainActivity.myDbMain.updateRowExpense(expenseTitle, expense);
            }

            Intent intent = new Intent(this, ExpenseActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, getString(R.string.toast_EnterATitleAndValue),
                    Toast.LENGTH_LONG).show();
        }
    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    // Lifecycle

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
        finish();
    }

    // Lifecycle
    // ---------------------------------------------------------------------------------------------
    // Spinner Methods

    //create Spinner to choose if values are for every, year, month, week or day
    private void createSpinnerEvery() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.every_arrays, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_EditExpenseActivity_Every.setAdapter(adapter);
        spinner_EditExpenseActivity_Every.setOnItemSelectedListener(this);

        string_Every = spinner_EditExpenseActivity_Every.getSelectedItem().toString();
        editText_EditExpenseActivity_RepeatedBy.setFocusableInTouchMode(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equals(SPINNER_YEAR)) {
            string_Every = SPINNER_YEAR;
            editText_EditExpenseActivity_RepeatedBy.setText("1");
            editText_EditExpenseActivity_RepeatedBy.setFocusableInTouchMode(false);

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_MONTH)) {
            editText_EditExpenseActivity_RepeatedBy.setFocusableInTouchMode(true);
            string_Every = SPINNER_MONTH;

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_WEEK)) {
            editText_EditExpenseActivity_RepeatedBy.setFocusableInTouchMode(true);
            string_Every = SPINNER_WEEK;

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_DAY)) {
            editText_EditExpenseActivity_RepeatedBy.setFocusable(true);
            string_Every = SPINNER_DAY;
        }

        editText_EditExpenseActivity_Value.requestFocus();
        editText_EditExpenseActivity_Value.setSelection(editText_EditExpenseActivity_Value.getText().length());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Spinner Methods
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        toolbar.setSubtitle(getString(R.string.subtitle_BalanceMonth) +" " + generalFormatter.getCurrencyFormatMonth(getColumnHelper.getBalanceYearDouble()));
        textView_EditExpenseActivity_Currency.setText(getColumnHelper.getCurrency());

        if (!ExpenseActivity.boolean_NewExpense) {
            editText_EditExpenseActivity_Title.setText(getColumnHelper.getExpenseTitle());
            editText_EditExpenseActivity_Title.setSelection(editText_EditExpenseActivity_Title.getText().length());
            editText_EditExpenseActivity_Value.setText(getColumnHelper.getExpenseYearDouble().toString());
            textView_EditExpenseActivity_ExpenseYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getExpenseYearDouble()));
            textView_EditExpenseActivity_ExpenseMonth.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getExpenseYearDouble()));
            textView_EditExpenseActivity_ExpenseWeek.setText(generalFormatter.getCurrencyFormatWeek(getColumnHelper.getExpenseYearDouble()));
            textView_EditExpenseActivity_ExpenseDay.setText(generalFormatter.getCurrencyFormatDay(getColumnHelper.getExpenseYearDouble()));
            spinner_EditExpenseActivity_Every.setSelection(((ArrayAdapter) spinner_EditExpenseActivity_Every.getAdapter()).getPosition(SPINNER_YEAR));
            button_EditExpenseActivity_DeleteExpense.setVisibility(View.VISIBLE);
        } else {
            button_EditExpenseActivity_DeleteExpense.setVisibility(View.INVISIBLE);
            textView_EditExpenseActivity_ExpenseYear.setText("");
            textView_EditExpenseActivity_ExpenseMonth.setText("");
            textView_EditExpenseActivity_ExpenseWeek.setText("");
            textView_EditExpenseActivity_ExpenseDay.setText("");

        }
    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
