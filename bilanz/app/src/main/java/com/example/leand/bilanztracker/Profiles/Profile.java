package com.example.leand.bilanztracker.Profiles;

import com.example.leand.bilanztracker.Activitys.MainActivity;

public class Profile {

    public Profile(){
    }

    public void generalProfile(){
        MainActivity.myDbMain.insertRowProfile("General Profile");
        MainActivity.myDbMain.insertRowIncome("salary",50000);
        MainActivity.myDbMain.insertRowDeduction("general deduction",10);
        MainActivity.myDbMain.insertRowExpense("mobile",400);
        MainActivity.myDbMain.insertRowExpense("insurance health",5000);
        MainActivity.myDbMain.insertRowExpense("electricity",1000);
        MainActivity.myDbMain.insertRowExpense("tax",5000);
        MainActivity.myDbMain.insertRowExpense("eat",7000);
        MainActivity.myDbMain.insertRowExpense("internet",600);
        MainActivity.myDbMain.insertRowExpense("unforeseen",3000);
        MainActivity.myDbMain.insertRowExpense("holidays",2000);
        MainActivity.myDbMain.insertRowExpense("household insurance",300);
    }



}
