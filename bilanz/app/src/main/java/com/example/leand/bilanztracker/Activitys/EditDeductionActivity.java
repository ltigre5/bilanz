package com.example.leand.bilanztracker.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.EditTextFilter.InputFilterDecimal;
import com.example.leand.bilanztracker.R;

public class EditDeductionActivity extends BaseActivity {
    private EditText editText_EditDeductionActivity_Title, editText_EditDeductionActivity_Percentage;
    private Button button_EditDeductionActivity_Delete;

    private Toolbar toolbar;
    private GetColumnHelper getColumnHelper;

    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deduction);

        //Set Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        editText_EditDeductionActivity_Title = findViewById(R.id.editText_EditDeductionActivity_Title);
        editText_EditDeductionActivity_Percentage = findViewById(R.id.editText_EditDeductionActivity_Percentage);
        editText_EditDeductionActivity_Percentage.setFilters(new InputFilter[]{new InputFilterDecimal(2, 2)});
        button_EditDeductionActivity_Delete = findViewById(R.id.button_EditDeductionActivity_Delete);

        if (EditIncomeActivity.boolean_NewDeduction) {
            getColumnHelper = new GetColumnHelper();

        } else {
            getColumnHelper = new GetColumnHelper(MainActivity.myDbMain.getRowDeduction(EditIncomeActivity.long_DeductionId));
        }

        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    public void onClickDeleteDeduction(View view) {
        if (!EditIncomeActivity.boolean_NewDeduction) {
            MainActivity.myDbMain.deleteDeduction(EditIncomeActivity.long_DeductionId);

            Intent intent = new Intent(this, EditIncomeActivity.class);
            startActivity(intent);
        }
    }

    public void onClickSaveDeduction(View view) {
        if (!editText_EditDeductionActivity_Title.getText().toString().equals("")
                && !editText_EditDeductionActivity_Percentage.getText().toString().equals("")) {
            if (EditIncomeActivity.boolean_NewDeduction) {
                MainActivity.myDbMain.insertRowDeduction(editText_EditDeductionActivity_Title.getText().toString(),
                        Double.parseDouble(editText_EditDeductionActivity_Percentage.getText().toString()));

            } else {
                MainActivity.myDbMain.updateRowDeduction(EditIncomeActivity.long_DeductionId, editText_EditDeductionActivity_Title.getText().toString(),
                        Double.parseDouble(editText_EditDeductionActivity_Percentage.getText().toString()));
            }

            Intent intent = new Intent(this, EditIncomeActivity.class);
            startActivity(intent);
            finish();
        } else {
                Toast.makeText(this, "Enter A title and value",
                        Toast.LENGTH_LONG).show();
        }
    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    //

    //
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        toolbar.setSubtitle(getColumnHelper.getProfileTitle());

        if (!EditIncomeActivity.boolean_NewDeduction) {
            editText_EditDeductionActivity_Title.setText(getColumnHelper.getDeductionTitle());
            editText_EditDeductionActivity_Title.setSelection(editText_EditDeductionActivity_Title.getText().length());
            editText_EditDeductionActivity_Percentage.setText(getColumnHelper.getDeductionPercentageString());
        }


    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End


    // Menu
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
