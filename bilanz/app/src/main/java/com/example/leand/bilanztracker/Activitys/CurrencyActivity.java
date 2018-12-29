package com.example.leand.bilanztracker.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class CurrencyActivity extends BaseActivity {
    private EditText editText_CurrencyActivity_Currency;
    private TextView textView_CurrencyActivity_oldCurrency;

    private Toolbar toolbar;
    private GetColumnHelper getColumnHelper;

    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        //Set Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        getColumnHelper=new GetColumnHelper();

        editText_CurrencyActivity_Currency = findViewById(R.id.editText_CurrencyActivity_Currency);
        textView_CurrencyActivity_oldCurrency=findViewById(R.id.textView_CurrencyActivity_oldCurrency);

        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    public void onClick_SaveCurrency(View view) {
        if (editText_CurrencyActivity_Currency.length() < 3) {
            Toast.makeText(CurrencyActivity.this, "Enter A 3-Letter Currency",
                    Toast.LENGTH_LONG).show();

        } else {

            if (MainActivity.myDbMain.checkCurrencyExists()) {
                MainActivity.myDbMain.updateCurrency(editText_CurrencyActivity_Currency.getText().toString().toUpperCase());
            } else {
                MainActivity.myDbMain.insertCurrency(editText_CurrencyActivity_Currency.getText().toString().toUpperCase());
            }
            textView_CurrencyActivity_oldCurrency.setText(getColumnHelper.getCurrency());
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
        toolbar.setSubtitle(MainActivity.string_actualProfile);

    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // Menu


    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
