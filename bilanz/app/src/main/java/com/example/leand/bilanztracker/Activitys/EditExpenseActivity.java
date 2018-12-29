package com.example.leand.bilanztracker.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.EditTextFilter.InputFilterDecimal;
import com.example.leand.bilanztracker.ListViewHelper.ListViewAdapter;
import com.example.leand.bilanztracker.R;

public class EditExpenseActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{
    private EditText editText_EditExpenseActivity_Title, editText_EditExpenseActivity_Value, editText_EditExpenseActivity_RepeatedBy;
    private TextView textView_EditIncomeActivity_IncomeGrossSaved;
    private Spinner spinner_EditExpenseActivity_Every;

    private Toolbar toolbar;
    private GetColumnHelper getColumnHelper;

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

        //definition of Items in Activity
        editText_EditExpenseActivity_Title = findViewById(R.id.editText_EditExpenseActivity_Title);
        editText_EditExpenseActivity_Value = findViewById(R.id.editText_EditExpenseActivity_Value);
        editText_EditExpenseActivity_Value.setFilters(new InputFilter[]{new InputFilterDecimal(14, 2)});
        editText_EditExpenseActivity_RepeatedBy = findViewById(R.id.editText_EditExpenseActivity_RepeatedBy);
        spinner_EditExpenseActivity_Every = findViewById(R.id.spinner_EditExpenseActivity_Every);

        getColumnHelper = new GetColumnHelper();

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
    }

    public void onClickSaveExpense(View view) {
    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    //


    // calculate net
    // ---------------------------------------------------------------------------------------------
    // Spinner Methods

    //create Spinner to Order Items by values or Date
    private void createSpinnerEvery() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.every_arrays, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_EditExpenseActivity_Every.setAdapter(adapter);
        spinner_EditExpenseActivity_Every.setOnItemSelectedListener(this);

        string_Every = spinner_EditExpenseActivity_Every.getSelectedItem().toString();
        editText_EditExpenseActivity_RepeatedBy.setFocusable(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).toString().equals(SPINNER_YEAR)) {
            string_Every = SPINNER_YEAR;
            editText_EditExpenseActivity_RepeatedBy.setText("1");
            editText_EditExpenseActivity_RepeatedBy.setFocusable(false);

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_MONTH)) {
            editText_EditExpenseActivity_RepeatedBy.setFocusable(true);
            string_Every = SPINNER_MONTH;

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_WEEK)) {
            editText_EditExpenseActivity_RepeatedBy.setFocusable(true);
            string_Every = SPINNER_WEEK;

        } else if (parent.getItemAtPosition(position).toString().equals(SPINNER_DAY)) {
            editText_EditExpenseActivity_RepeatedBy.setFocusable(true);
            string_Every = SPINNER_DAY;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Spinner Methods
    // ---------------------------------------------------------------------------------------------
    // List Methods


    //
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        toolbar.setSubtitle(MainActivity.string_actualProfile);

    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End



    // Menu
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
