package com.example.leand.bilanztracker.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.leand.bilanztracker.DatabaseHelper.DBAdapter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.EditTextFilter.InputFilterDecimal;
import com.example.leand.bilanztracker.ListViewHelper.ListViewAdapter;
import com.example.leand.bilanztracker.R;

public class EditIncomeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private EditText editText_EditIncomeActivity_Title, editText_EditIncomeActivity_IncomeGross, editText_EditIncomeActivity_RepeatedBy;
    private TextView textView_EditIncomeActivity_IncomeGrossSaved, textView_EditIncomeActivity_IncomeNet;
    private ListView listView_SalaryActivity;
    private ListViewAdapter listViewAdapter;
    private Spinner spinner_EditIncomeActivity_Every;

    private Toolbar toolbar;
    private GetColumnHelper getColumnHelper;

    private String string_Every;
    private String SPINNER_YEAR, SPINNER_MONTH, SPINNER_WEEK, SPINNER_DAY;

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

        editText_EditIncomeActivity_Title = findViewById(R.id.editText_EditIncomeActivity_Title);
        editText_EditIncomeActivity_IncomeGross = findViewById(R.id.editText_EditIncomeActivity_IncomeGross);
        editText_EditIncomeActivity_IncomeGross.setFilters(new InputFilter[]{new InputFilterDecimal(14, 2)});
        editText_EditIncomeActivity_RepeatedBy = findViewById(R.id.editText_EditIncomeActivity_RepeatedBy);
        textView_EditIncomeActivity_IncomeGrossSaved = findViewById(R.id.textView_EditIncomeActivity_IncomeGrossSaved);
        textView_EditIncomeActivity_IncomeNet = findViewById(R.id.textView_EditIncomeActivity_IncomeNet);
        spinner_EditIncomeActivity_Every = findViewById(R.id.spinner_EditIncomeActivity_Every);

        SPINNER_YEAR = getResources().getString(R.string.every_year);
        SPINNER_MONTH = getResources().getString(R.string.every_month);
        SPINNER_WEEK = getResources().getString(R.string.every_week);
        SPINNER_DAY = getResources().getString(R.string.every_day);

        if (!IncomeActivity.boolean_NewIncome){
            MainActivity.myDbMain.updateRowIncomeNet(calculateIncomeNet(getColumnHelper.getIncomeGrossYearDouble()));
        }

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
        }
    }

    public void onClickSaveIncomeGrossYear(View view) {
        if (!editText_EditIncomeActivity_Title.getText().toString().equals("")
                && !editText_EditIncomeActivity_IncomeGross.getText().toString().equals("")) {

            String incomeTitle = editText_EditIncomeActivity_Title.getText().toString();
            double incomeGross = Double.parseDouble(editText_EditIncomeActivity_IncomeGross.getText().toString());
            int repeatedBy = Integer.parseInt(editText_EditIncomeActivity_RepeatedBy.getText().toString());

            if (string_Every.equals(SPINNER_MONTH)) {
                incomeGross = incomeGross * (12 / repeatedBy);

            } else if (string_Every.equals(SPINNER_WEEK)) {
                incomeGross = incomeGross * (52 / repeatedBy);

            } else if (string_Every.equals(SPINNER_DAY)) {
                incomeGross = incomeGross * (365 / repeatedBy);
            }

            double incomeNet=calculateIncomeNet(incomeGross);

            if (IncomeActivity.boolean_NewIncome) {
                MainActivity.myDbMain.insertRowIncome(incomeTitle, incomeGross, incomeNet);
                IncomeActivity.boolean_NewIncome=false;
            } else {
                MainActivity.myDbMain.updateRowIncome(incomeTitle, incomeGross, incomeNet);
            }

            displayItemsOnActivity();
        }
    }

    public void onClickAddNewDeduction(View view) {
        Intent intent = new Intent(this, EditDeductionActivity.class);
        boolean_NewDeduction = true;
        startActivity(intent);
    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    // calculate net

    private double calculateIncomeNet(double incomeGross){
        double totalDeduction=getColumnHelper.getTotalDeduction();

        return incomeGross-(incomeGross*totalDeduction/100);
    }

    // calculate net
    // ---------------------------------------------------------------------------------------------
    // Spinner Methods

    //create Spinner to Order Items by values or Date
    private void createSpinnerEvery() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.every_arrays, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_EditIncomeActivity_Every.setAdapter(adapter);
        spinner_EditIncomeActivity_Every.setOnItemSelectedListener(this);

        string_Every = spinner_EditIncomeActivity_Every.getSelectedItem().toString();
        editText_EditIncomeActivity_RepeatedBy.setFocusable(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equals(SPINNER_YEAR)) {
            string_Every = SPINNER_YEAR;
            editText_EditIncomeActivity_RepeatedBy.setText("1");
            editText_EditIncomeActivity_RepeatedBy.setFocusable(false);

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_MONTH)) {
            editText_EditIncomeActivity_RepeatedBy.setFocusable(true);
            string_Every = SPINNER_MONTH;

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_WEEK)) {
            editText_EditIncomeActivity_RepeatedBy.setFocusable(true);
            string_Every = SPINNER_WEEK;

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_DAY)) {
            editText_EditIncomeActivity_RepeatedBy.setFocusable(true);
            string_Every = SPINNER_DAY;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Spinner Methods
    // ---------------------------------------------------------------------------------------------
    // List Methods

    //Creates ArrayList of all profiles for ListView and adds an onClick Method which opens OverviewActivity
    public void createDeductionListView() {
        listView_SalaryActivity.setAdapter(listViewAdapter.getDeductionListViewAdapter());

        //by clicking of Item get Database-ID of Position and open EditItemActivity and send ID
        listView_SalaryActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get ID of selected Item from Database
                Cursor cursor_Deduction = (Cursor) parent.getItemAtPosition(position);
                long_DeductionId = cursor_Deduction.getLong(cursor_Deduction.getColumnIndexOrThrow(DBAdapter.KEY_ID));
                boolean_NewDeduction = false;

                Intent intent = new Intent(getApplicationContext(), EditDeductionActivity.class);
                startActivity(intent);
            }
        });
    }

    // List Methods
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        toolbar.setSubtitle(MainActivity.string_actualProfile);
        listView_SalaryActivity.setAdapter(listViewAdapter.getDeductionListViewAdapter());

        if (!IncomeActivity.boolean_NewIncome) {
            editText_EditIncomeActivity_Title.setText(getColumnHelper.getIncomeTitle());
            editText_EditIncomeActivity_Title.setSelection(editText_EditIncomeActivity_Title.getText().length());
            editText_EditIncomeActivity_IncomeGross.setText(getColumnHelper.getIncomeGrossYearDouble().toString());
            textView_EditIncomeActivity_IncomeGrossSaved.setText(getColumnHelper.getCurrencyFormatWithCurrency(getColumnHelper.getIncomeGrossYearDouble()));
            textView_EditIncomeActivity_IncomeNet.setText(getColumnHelper.getCurrencyFormatWithCurrency(getColumnHelper.getIncomeNetYearDouble()));
        } else {
            textView_EditIncomeActivity_IncomeGrossSaved.setText("");
            textView_EditIncomeActivity_IncomeNet.setText("");
        }
    }



    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
