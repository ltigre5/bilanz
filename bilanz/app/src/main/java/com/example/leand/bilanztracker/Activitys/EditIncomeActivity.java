package com.example.leand.bilanztracker.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leand.bilanztracker.DatabaseHelper.DBAdapter;
import com.example.leand.bilanztracker.DatabaseHelper.GeneralFormatter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.EditTextFilter.InputFilterDecimal;
import com.example.leand.bilanztracker.ListViewHelper.ListViewAdapter;
import com.example.leand.bilanztracker.R;

public class EditIncomeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private EditText editText_EditIncomeActivity_Title, editText_EditIncomeActivity_IncomeGross, editText_EditIncomeActivity_RepeatedBy;
    private TextView textView_EditIncomeActivity_IncomeGrossYear, textView_EditIncomeActivity_IncomeNetYear,
            textView_EditIncomeActivity_IncomeGrossMonth, textView_EditIncomeActivity_IncomeNetMonth,
            textView_EditIncomeActivity_Currency;
    private Button button_EditIncomeActivity_DeleteIncome;

    private ListView listView_SalaryActivity;
    private ListViewAdapter listViewAdapter;

    private Spinner spinner_EditIncomeActivity_Every;

    private Toolbar toolbar;
    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;

    private String string_Every;
    private String SPINNER_YEAR, SPINNER_MONTH, SPINNER_WEEK, SPINNER_DAY;
    int repeatedBy = 1;

    public static long long_DeductionId;
    public static boolean boolean_NewDeduction;


    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);

        //Set Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        //definition of Items in Activity
        listView_SalaryActivity = findViewById(R.id.listView_EditIncomeActivity);

        listViewAdapter = new ListViewAdapter(this);

        getColumnHelper = new GetColumnHelper();
        generalFormatter = new GeneralFormatter();

        //Initialize Activity items
        editText_EditIncomeActivity_Title = findViewById(R.id.editText_EditIncomeActivity_Title);
        editText_EditIncomeActivity_IncomeGross = findViewById(R.id.editText_EditIncomeActivity_IncomeGross);
        editText_EditIncomeActivity_IncomeGross.setFilters(new InputFilter[]{new InputFilterDecimal(14, 2)});
        editText_EditIncomeActivity_RepeatedBy = findViewById(R.id.editText_EditIncomeActivity_RepeatedBy);
        textView_EditIncomeActivity_IncomeGrossYear = findViewById(R.id.textView_EditIncomeActivity_IncomeGrossYear);
        textView_EditIncomeActivity_IncomeNetYear = findViewById(R.id.textView_EditIncomeActivity_IncomeNetYear);
        textView_EditIncomeActivity_IncomeGrossMonth = findViewById(R.id.textView_EditIncomeActivity_IncomeGrossMonth);
        textView_EditIncomeActivity_IncomeNetMonth = findViewById(R.id.textView_EditIncomeActivity_IncomeNetMonth);
        textView_EditIncomeActivity_Currency=findViewById(R.id.textView_EditIncomeActivity_Currency);
        button_EditIncomeActivity_DeleteIncome = findViewById(R.id.button_EditIncomeActivity_DeleteIncome);
        spinner_EditIncomeActivity_Every = findViewById(R.id.spinner_EditIncomeActivity_Every);

        SPINNER_YEAR = getResources().getString(R.string.every_year);
        SPINNER_MONTH = getResources().getString(R.string.every_month);
        SPINNER_WEEK = getResources().getString(R.string.every_week);
        SPINNER_DAY = getResources().getString(R.string.every_day);

        createSpinnerEvery();

        createDeductionListView();

        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    public void onClickADelete(View view) {
        if (!IncomeActivity.boolean_NewIncome) {
            MainActivity.myDbMain.deleteIncome(IncomeActivity.long_IncomeId);

            Intent intent = new Intent(this, IncomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClickSaveIncomeGrossYear(View view) {
        if (!editText_EditIncomeActivity_Title.getText().toString().equals("")
                && !editText_EditIncomeActivity_IncomeGross.getText().toString().equals("")) {

            String incomeTitle = editText_EditIncomeActivity_Title.getText().toString();
            double incomeGross = Double.parseDouble(editText_EditIncomeActivity_IncomeGross.getText().toString());
            repeatedBy = Integer.parseInt(editText_EditIncomeActivity_RepeatedBy.getText().toString());

            //calculate the expense by the chosen spinner state
            if (string_Every.equals(SPINNER_MONTH)) {
                incomeGross = incomeGross * (12 / repeatedBy);
            } else if (string_Every.equals(SPINNER_WEEK)) {
                incomeGross = incomeGross * (52 / repeatedBy);
            } else if (string_Every.equals(SPINNER_DAY)) {
                incomeGross = incomeGross * (365 / repeatedBy);
            }

            if (IncomeActivity.boolean_NewIncome) {
                MainActivity.myDbMain.insertRowIncome(incomeTitle, incomeGross);
                IncomeActivity.boolean_NewIncome = false;
            } else {
                MainActivity.myDbMain.updateRowIncome(incomeTitle, incomeGross);
            }

            displayItemsOnActivity();

            editText_EditIncomeActivity_IncomeGross.requestFocus();
            editText_EditIncomeActivity_IncomeGross.setSelection(editText_EditIncomeActivity_IncomeGross.getText().length());

        } else {
            Toast.makeText(this, getString(R.string.toast_EnterATitleAndValue),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onClickAddNewDeduction(View view) {
        Intent intent = new Intent(this, EditDeductionActivity.class);
        boolean_NewDeduction = true;
        startActivity(intent);
        finish();
    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    // Lifecycle

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, IncomeActivity.class);
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
        spinner_EditIncomeActivity_Every.setAdapter(adapter);
        spinner_EditIncomeActivity_Every.setOnItemSelectedListener(this);

        string_Every = spinner_EditIncomeActivity_Every.getSelectedItem().toString();
        editText_EditIncomeActivity_RepeatedBy.setFocusableInTouchMode(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equals(SPINNER_YEAR)) {
            string_Every = SPINNER_YEAR;
            editText_EditIncomeActivity_RepeatedBy.setText("1");
            editText_EditIncomeActivity_RepeatedBy.setFocusableInTouchMode(false);

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_MONTH)) {
            editText_EditIncomeActivity_RepeatedBy.setFocusableInTouchMode(true);
            string_Every = SPINNER_MONTH;

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_WEEK)) {
            editText_EditIncomeActivity_RepeatedBy.setFocusableInTouchMode(true);
            string_Every = SPINNER_WEEK;

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_DAY)) {
            editText_EditIncomeActivity_RepeatedBy.setFocusableInTouchMode(true);
            string_Every = SPINNER_DAY;
        }

        editText_EditIncomeActivity_IncomeGross.requestFocus();
        editText_EditIncomeActivity_IncomeGross.setSelection(editText_EditIncomeActivity_IncomeGross.getText().length());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Spinner Methods
    // ---------------------------------------------------------------------------------------------
    // List Methods

    //Creates ArrayList of all profiles for ListView and adds an onClick Method which opens EditDeductionActivity
    public void createDeductionListView() {
        listView_SalaryActivity.setAdapter(listViewAdapter.getDeductionListViewAdapter());

        //by clicking of Item get Database-ID of Position and open EditDeductionActivity
        listView_SalaryActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get ID of selected Item from Database
                Cursor cursor_Deduction = (Cursor) parent.getItemAtPosition(position);
                long_DeductionId = cursor_Deduction.getLong(cursor_Deduction.getColumnIndexOrThrow(DBAdapter.KEY_ID));
                boolean_NewDeduction = false;

                Intent intent = new Intent(getApplicationContext(), EditDeductionActivity.class);
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
        toolbar.setSubtitle(getString(R.string.subtitle_BalanceMonth) + " " + generalFormatter.getCurrencyFormatMonth(getColumnHelper.getBalanceYearDouble()));
        listView_SalaryActivity.setAdapter(listViewAdapter.getDeductionListViewAdapter());
        textView_EditIncomeActivity_Currency.setText(getColumnHelper.getCurrency());

        if (!IncomeActivity.boolean_NewIncome) {
            editText_EditIncomeActivity_Title.setText(getColumnHelper.getIncomeTitle());
            editText_EditIncomeActivity_Title.setSelection(editText_EditIncomeActivity_Title.getText().length());

            Double value = getColumnHelper.getIncomeGrossYearDouble();

            if (string_Every.equals(SPINNER_MONTH)) {
                value = value / (12 / repeatedBy);
            } else if (string_Every.equals(SPINNER_WEEK)) {
                value = value / (52 / repeatedBy);
            } else if (string_Every.equals(SPINNER_DAY)) {
                value = value / (365 / repeatedBy);
            }

            editText_EditIncomeActivity_IncomeGross.setText(value.toString());
            textView_EditIncomeActivity_IncomeGrossYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getIncomeGrossYearDouble()));
            textView_EditIncomeActivity_IncomeNetYear.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getIncomeNetYearDouble()));
            textView_EditIncomeActivity_IncomeGrossMonth.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getIncomeGrossYearDouble()));
            textView_EditIncomeActivity_IncomeNetMonth.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getIncomeNetYearDouble()));
            button_EditIncomeActivity_DeleteIncome.setVisibility(View.VISIBLE);

        } else {
            textView_EditIncomeActivity_IncomeGrossYear.setText("");
            textView_EditIncomeActivity_IncomeNetYear.setText("");
            button_EditIncomeActivity_DeleteIncome.setVisibility(View.INVISIBLE);
        }
    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
