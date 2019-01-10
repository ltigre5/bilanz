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
import android.widget.Toast;

import com.example.leand.bilanztracker.R;

public class InfoActivity extends BaseActivity {
    private Toolbar toolbar;

    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Set Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        setSupportActionBar(toolbar);

        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        toolbar.setSubtitle(MainActivity.string_actualProfile);
    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
