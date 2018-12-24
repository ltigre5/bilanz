package com.example.leand.bilanztracker.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.leand.bilanztracker.DatabaseHelper.DBAdapter;
import com.example.leand.bilanztracker.ListViewHelper.ListViewAdapter;
import com.example.leand.bilanztracker.R;

public class MainActivity extends AppCompatActivity {
    public static DBAdapter myDbMain;
    public static long long_ProfileId;
    public static Cursor cursor_Profile;

    private ListView listView_MainActivity;
    private ListViewAdapter listViewAdapter;


    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDB();

        listView_MainActivity = findViewById(R.id.listView_MainActivity);

        listViewAdapter= new ListViewAdapter(this);

        createProfileListView();


    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    public void onClickAddNewProfile(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        //Yes button clicked, add new profile in database
                        long_ProfileId = MainActivity.myDbMain.insertRowProfile();

                        Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
                        startActivity(intent);

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked, do nothing
                        break;
                }
            }
        };

        //set the message to show in the DialogWindow
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Which Profile?").setPositiveButton("Empty Profile", dialogClickListener)
                .setNegativeButton("Default Profile", dialogClickListener).show();


    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    // Database Methods

    //Close Database when Activity is closed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    //open Database
    private void openDB() {
        myDbMain = new DBAdapter(this);
        myDbMain.open();

    }

    //close Database
    private void closeDB() {
        myDbMain.close();
    }

    // Database Methods
    // ---------------------------------------------------------------------------------------------
    // List Methods

    //Creates ArrayList of all profiles for ListView and adds an onClick Method which opens OverviewActivity
    public void createProfileListView() {
        listView_MainActivity.setAdapter(listViewAdapter.getProfileListViewAdapter());

        //by clicking of Item get Database-ID of Position and open EditItemActivity and send ID
        listView_MainActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get ID of selected Item from Database
                cursor_Profile = (Cursor) parent.getItemAtPosition(position);
                long_ProfileId = cursor_Profile.getLong(cursor_Profile.getColumnIndexOrThrow(DBAdapter.KEY_ID));

                //Open EditItemActivity and send ID
                Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
                startActivity(intent);

            }
        });
    }

    // List Methods
    // ---------------------------------------------------------------------------------------------
    // LifeCycle Methods

    @Override
    protected void onRestart() {
        super.onRestart();
        displayItemsOnActivity();
    }

    // LifeCycle Methods
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        listView_MainActivity.setAdapter(listViewAdapter.getProfileListViewAdapter());
    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------
    // End
}
