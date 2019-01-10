package com.example.leand.bilanztracker.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leand.bilanztracker.DatabaseHelper.DBAdapter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.ListViewHelper.ListViewAdapter;
import com.example.leand.bilanztracker.Profiles.Profile;
import com.example.leand.bilanztracker.R;

public class MainActivity extends AppCompatActivity {
    private ListView listView_MainActivity;
    private ListViewAdapter listViewAdapter;

    private GetColumnHelper getColumnHelper;
    private Toolbar toolbar;
    private Profile profile;

    public static DBAdapter myDbMain;
    public static Cursor cursor;
    public static String string_actualProfile;
    public static long long_ProfileId;
    public static boolean boolean_ProfileDeletedDuplicated =false;


    // Declaration
    //----------------------------------------------------------------------------------------------
    // OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Toolbar
        toolbar = findViewById(R.id.toolbar_MainActivity);
        toolbar.setSubtitleTextAppearance(this, R.style.subtitle);
        setSupportActionBar(toolbar);

        //open Database
        openDB();

        getColumnHelper = new GetColumnHelper();
        listViewAdapter = new ListViewAdapter(this);
        profile = new Profile(MainActivity.this);

        if (myDbMain.checkProfileExists()) {
            Cursor cursor = myDbMain.getAllRowsProfile();
            getColumnHelper.setCursor(cursor);
            long_ProfileId = getColumnHelper.getId();
            cursor.close();
        }

        //Initialize Activity items
        listView_MainActivity = findViewById(R.id.listView_MainActivity);

        createProfileListView();

        displayItemsOnActivity();
    }

    // OnCreate
    //----------------------------------------------------------------------------------------------
    // onClick Methods

    public void onClickAddNewProfile(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), OverviewActivity.class);
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        //Yes button clicked, add new profile in database
                        MainActivity.myDbMain.insertRowProfile();
                        displayItemsOnActivity();

                        startActivity(intent);

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        //No button clicked, add a default profile
                        profile.generalProfile();
                        displayItemsOnActivity();

                        startActivity(intent);

                        break;
                }
            }
        };

        //set the message to show in the DialogWindow
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.dialog_QuestionProfile)).setPositiveButton(getString(R.string.dialog_EmptyProfile), dialogClickListener)
                .setNegativeButton(getString(R.string.dialog_GeneralProfile), dialogClickListener).show();
    }

    // onClick Methods
    // ---------------------------------------------------------------------------------------------
    // Lifecycle

    @Override
    public void onRestart() {
        super.onRestart();
        displayItemsOnActivity();
    }

    // Lifecycle
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

        //by clicking of Item get Database-ID of Position and open OverviewActivity
        listView_MainActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get ID of selected Item from Database
                cursor = (Cursor) parent.getItemAtPosition(position);
                long_ProfileId = cursor.getLong(cursor.getColumnIndexOrThrow(DBAdapter.KEY_ID));
                displayItemsOnActivity();

                Intent intent = new Intent(getApplicationContext(), OverviewActivity.class);
                startActivity(intent);
            }
        });
    }

    // List Methods
    // ---------------------------------------------------------------------------------------------
    // Displaying Values

    //show Values on Activity
    public void displayItemsOnActivity() {
        listView_MainActivity.setAdapter(listViewAdapter.getProfileListViewAdapter());

        if (boolean_ProfileDeletedDuplicated ==true){
            Cursor cursor = myDbMain.getAllRowsProfile();
            getColumnHelper.setCursor(cursor);
            long_ProfileId = getColumnHelper.getId();
            cursor.close();
            boolean_ProfileDeletedDuplicated =false;
        }

        if (myDbMain.checkProfileExists()) {
            string_actualProfile = getColumnHelper.getProfileTitle();
        } else {
            string_actualProfile = "";
        }
        toolbar.setSubtitle(string_actualProfile);
    }

    // Displaying Values
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // Menu

    //Create the Menubar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Menus
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_Profiles:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_Overview:
                if (MainActivity.myDbMain.checkProfileExists()) {
                    intent = new Intent(this, OverviewActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, getString(R.string.toast_EnterNewProfile),
                            Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.menu_Income:
                if (MainActivity.myDbMain.checkProfileExists()) {
                    intent = new Intent(this, IncomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, getString(R.string.toast_EnterNewProfile),
                            Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.menu_Expense:
                if (MainActivity.myDbMain.checkProfileExists()) {
                    intent = new Intent(this, ExpenseActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, getString(R.string.toast_EnterNewProfile),
                            Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.menu_ChangeCurrency:
                intent = new Intent(this, CurrencyActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_DuplicateProfile:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                //Yes button clicked, delete all Data in Database
                                if (MainActivity.myDbMain.checkProfileExists()) {
                                    profile.copyProfile();
                                    boolean_ProfileDeletedDuplicated =true;
                                    displayItemsOnActivity();
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
                return true;

            case R.id.menu_DeleteProfile:
                dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                //Yes button clicked, delete all Data in Database
                                if (MainActivity.myDbMain.checkProfileExists()) {
                                    MainActivity.myDbMain.deleteProfile(MainActivity.long_ProfileId);
                                    boolean_ProfileDeletedDuplicated =true;
                                    displayItemsOnActivity();
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked, do nothing
                                break;
                        }
                    }
                };

                //set the message to show in the DialogWindow
                builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.dialog_QuestionDelete)).setPositiveButton(getString(R.string.dialog_Yes), dialogClickListener)
                        .setNegativeButton(getString(R.string.dialog_No), dialogClickListener).show();
                return true;

            case R.id.menu_Info:
                intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Menu
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End
}
