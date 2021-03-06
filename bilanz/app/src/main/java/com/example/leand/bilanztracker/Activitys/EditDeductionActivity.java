package com.example.leand.bilanztracker.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leand.bilanztracker.DatabaseHelper.GeneralFormatter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.EditTextFilter.InputFilterDecimal;
import com.example.leand.bilanztracker.R;

public class EditDeductionActivity extends BaseActivity {
    private EditText editText_EditDeductionActivity_Title, editText_EditDeductionActivity_Percentage;
    private Button button_EditDeductionActivity_Delete;

    private Toolbar toolbar;
    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;

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

        //Initialize Activity items
        editText_EditDeductionActivity_Title = findViewById(R.id.editText_EditDeductionActivity_Title);
        editText_EditDeductionActivity_Percentage = findViewById(R.id.editText_EditDeductionActivity_Percentage);
        editText_EditDeductionActivity_Percentage.setFilters(new InputFilter[]{new InputFilterDecimal(2, 2)});
        button_EditDeductionActivity_Delete = findViewById(R.id.button_EditDeductionActivity_Delete);

        getColumnHelper=new GetColumnHelper();
        generalFormatter=new GeneralFormatter();

        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    //delete the deduction
    public void onClickDeleteDeduction(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        if (!EditIncomeActivity.boolean_NewDeduction) {
                            MainActivity.myDbMain.deleteDeduction(EditIncomeActivity.long_DeductionId);

                            Intent intent = new Intent(getApplicationContext(), EditIncomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked, do nothing
                        break;
                }
            }
        };

        //set the message to show in the DialogWindow
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.dialog_QuestionDelete)).setPositiveButton(getString(R.string.dialog_Yes), dialogClickListener)
                .setNegativeButton(getString(R.string.dialog_No), dialogClickListener).show();
    }

    //save the deduction
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
                Toast.makeText(this, getString(R.string.toast_EnterATitleAndValue),
                        Toast.LENGTH_LONG).show();
        }
    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    // Lifecycle

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, EditIncomeActivity.class);
        startActivity(intent);
        finish();
    }

    // Lifecycle
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        toolbar.setSubtitle(getString(R.string.subtitle_BalanceMonth) +" " + generalFormatter.getCurrencyFormatMonth(getColumnHelper.getBalanceYearDouble()));

        if (!EditIncomeActivity.boolean_NewDeduction) {
            editText_EditDeductionActivity_Title.setText(getColumnHelper.getDeductionTitle());
            editText_EditDeductionActivity_Title.setSelection(editText_EditDeductionActivity_Title.getText().length());
            editText_EditDeductionActivity_Percentage.setText(getColumnHelper.getDeductionPercentageString());

            button_EditDeductionActivity_Delete.setVisibility(View.VISIBLE);
        } else {
            button_EditDeductionActivity_Delete.setVisibility(View.INVISIBLE);
        }
    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
