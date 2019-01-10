package com.example.leand.bilanztracker.Profiles;

import android.content.Context;
import android.database.Cursor;

import com.example.leand.bilanztracker.Activitys.EditIncomeActivity;
import com.example.leand.bilanztracker.Activitys.ExpenseActivity;
import com.example.leand.bilanztracker.Activitys.IncomeActivity;
import com.example.leand.bilanztracker.Activitys.MainActivity;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

import java.util.ArrayList;
import java.util.Iterator;

public class Profile {
    private Context context;

    private GetColumnHelper getColumnHelper;

    public Profile(Context context) {
        this.context = context;
        getColumnHelper = new GetColumnHelper();
    }

    /**
     * Generate a default profile
     *
     */
    public void generalProfile() {
        MainActivity.myDbMain.insertRowProfile(context.getString(R.string.dialog_GeneralProfile));
        MainActivity.myDbMain.insertRowIncome(context.getString(R.string.profile_Salary), 50000);
        MainActivity.myDbMain.insertRowDeduction(context.getString(R.string.profile_GeneralDeduction), 10);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_Rent), 16000);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_Mobile), 400);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_InsuranceHealth), 5000);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_Electricity), 1000);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_Tax), 5000);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_Eat), 7000);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_Internet), 600);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_Unforeseen), 3000);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_Holidays), 2000);
        MainActivity.myDbMain.insertRowExpense(context.getString(R.string.profile_HouseholdInsurance), 300);
    }

    /**
     * Copy the actual Profile
     *
     */
    public void copyProfile() {
        ArrayList<Income> arrayList_Income = new ArrayList<Income>();
        ArrayList<Deduction> arrayList_Deduction = new ArrayList<Deduction>();
        ArrayList<Expense> arrayList_Expense = new ArrayList<Expense>();

        Income income;
        Deduction deduction;
        Expense expense;

        //get old values of income, deduction and expense
        //get all incomes
        if (MainActivity.myDbMain.checkIncomeExists()) {
            Cursor cursor_Income = MainActivity.myDbMain.getAllRowsIncome();
            do {

                //get old values of the income
                getColumnHelper.setCursor(cursor_Income);
                IncomeActivity.long_IncomeId = getColumnHelper.getId();
                String income_type = getColumnHelper.getIncomeTitle();
                double income_year_gross = getColumnHelper.getIncomeGrossYearDouble();
                double income_year_net = getColumnHelper.getIncomeNetYearDouble();

                income = new Income(income_type, income_year_gross, income_year_net);
                arrayList_Income.add(income);

                //get all deductions
                if (MainActivity.myDbMain.checkDeductionExists()) {
                    Cursor cursor_Deduction = MainActivity.myDbMain.getAllRowsDeduction();
                    do {

                        //get old values of the deduction
                        getColumnHelper.setCursor(cursor_Deduction);
                        EditIncomeActivity.long_DeductionId = getColumnHelper.getId();
                        String deduction_type = getColumnHelper.getDeductionTitle();
                        double percentage = getColumnHelper.getDeductionPercentageDouble();

                        deduction = new Deduction(deduction_type, percentage);
                        arrayList_Deduction.add(deduction);

                    } while (cursor_Deduction.moveToNext());
                }
            } while (cursor_Income.moveToNext());
        }

        //get all expenses
        if (MainActivity.myDbMain.checkExpenseExists()) {
            Cursor cursor_Expense = MainActivity.myDbMain.getAllRowsExpense();
            do {

                //get old values of the income
                getColumnHelper.setCursor(cursor_Expense);
                ExpenseActivity.long_ExpenseId = getColumnHelper.getId();
                String expense_type = getColumnHelper.getExpenseTitle();
                double expense_year = getColumnHelper.getExpenseYearDouble();

                expense = new Expense(expense_type, expense_year);
                arrayList_Expense.add(expense);

            } while (cursor_Expense.moveToNext());
        }

        //add new values
        MainActivity.myDbMain.insertRowProfile(getColumnHelper.getProfileTitle() + 2);

        Iterator iterator_Income = arrayList_Income.iterator();
        Iterator iterator_Deduction = arrayList_Deduction.iterator();
        Iterator iterator_Expense = arrayList_Expense.iterator();

        while (iterator_Income.hasNext()) {
            income = (Income) iterator_Income.next();
            MainActivity.myDbMain.insertRowIncome(income.getIncome_type(), income.getIncome_year_gross(), income.getIncome_year_net());

            while (iterator_Deduction.hasNext()) {
                deduction = (Deduction) iterator_Deduction.next();
                MainActivity.myDbMain.insertRowDeduction(deduction.getDeduction_type(), deduction.getPercentage());
            }
        }

        while (iterator_Expense.hasNext()) {
            expense = (Expense) iterator_Expense.next();
            MainActivity.myDbMain.insertRowExpense(expense.getExpense_type(), expense.getExpense_year());
        }
    }
}
