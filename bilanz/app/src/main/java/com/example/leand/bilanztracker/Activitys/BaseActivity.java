package com.example.leand.bilanztracker.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leand.bilanztracker.R;

public class BaseActivity extends AppCompatActivity {

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
                    Toast.makeText(this, "Add a new Profile",
                            Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.menu_Income:
                if (MainActivity.myDbMain.checkProfileExists()) {
                    intent = new Intent(this, IncomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Add a new Profile",
                            Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.menu_Expense:
                if (MainActivity.myDbMain.checkProfileExists()) {
                    intent = new Intent(this, ExpenseActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Add a new Profile",
                            Toast.LENGTH_LONG).show();
                }

                return true;

            case R.id.menu_ChangeCurrency:
                intent = new Intent(this, CurrencyActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_DeleteProfile:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                //Yes button clicked, delete all Data in Database
                                if (MainActivity.myDbMain.checkProfileExists()) {
                                    MainActivity.myDbMain.deleteProfile(MainActivity.long_ProfileId);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
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
                builder.setMessage("are you sure").setPositiveButton("yes", dialogClickListener)
                        .setNegativeButton("no", dialogClickListener).show();
                return true;

            case R.id.menu_Info:
                intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
