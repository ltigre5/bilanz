package com.example.leand.bilanztracker.ListViewHelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leand.bilanztracker.Activitys.ExpenseActivity;
import com.example.leand.bilanztracker.Activitys.MainActivity;
import com.example.leand.bilanztracker.DatabaseHelper.GeneralFormatter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class ExpenseCursorAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;
    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;
    private long expenseIdOld;

    public ExpenseCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getColumnHelper = new GetColumnHelper(c);
        generalFormatter=new GeneralFormatter();
        expenseIdOld=ExpenseActivity.long_ExpenseId;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.adapter_view_list_expense, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView_AdapterViewListDeduction_title = view.findViewById(R.id.textView_AdapterViewListExpense_Title);
        TextView textView_AdapterViewListDeduction_YearValue = view.findViewById(R.id.textView_AdapterViewListExpense_YearValue);
        TextView textView_AdapterViewListDeduction_MonthValue = view.findViewById(R.id.textView_AdapterViewListExpense_MonthValue);
        TextView textView_AdapterViewListDeduction_WeekValue = view.findViewById(R.id.textView_AdapterViewListExpense_WeekValue);
        TextView textView_AdapterViewListDeduction_DayValue = view.findViewById(R.id.textView_AdapterViewListExpense_DayValue);

        ExpenseActivity.long_ExpenseId=getColumnHelper.getId();

        textView_AdapterViewListDeduction_title.setText(getColumnHelper.getExpenseTitle());
        textView_AdapterViewListDeduction_YearValue.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getExpenseYearDouble()));
        textView_AdapterViewListDeduction_MonthValue.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getExpenseYearDouble()));
        textView_AdapterViewListDeduction_WeekValue.setText(generalFormatter.getCurrencyFormatWeek(getColumnHelper.getExpenseYearDouble()));
        textView_AdapterViewListDeduction_DayValue.setText(generalFormatter.getCurrencyFormatDay(getColumnHelper.getExpenseYearDouble()));


        ExpenseActivity.long_ExpenseId=expenseIdOld;
    }
}