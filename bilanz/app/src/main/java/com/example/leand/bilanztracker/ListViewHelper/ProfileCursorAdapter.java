package com.example.leand.bilanztracker.ListViewHelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class ProfileCursorAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;
    private GetColumnHelper getColumnHelper;
    private String currency;

    public ProfileCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getColumnHelper = new GetColumnHelper(c);
        currency = getColumnHelper.getCurrency();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.adapter_view_list_profile, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView_AdapterViewListProfile_title = view.findViewById(R.id.textView_AdapterViewListProfile_title);
        TextView textView_AdapterViewListProfile_BalanceYear = view.findViewById(R.id.textView_AdapterViewListProfile_BalanceYear);
        TextView textView_AdapterViewListProfile_ExpenseYear = view.findViewById(R.id.textView_AdapterViewListProfile_ExpenseYear);
        TextView textView_AdapterViewListProfile_IncomeYear = view.findViewById(R.id.textView_AdapterViewListProfile_IncomeYear);

        String profileTitle = getColumnHelper.getProfileTitle();
        Double totalIncomeYear = getColumnHelper.getProfileTotalIncomeYear();
        Double totalExpenseYear = getColumnHelper.getProfileTotalExpenseYear();
        Double balanceYear = getColumnHelper.getProfileBalanceYear();

        textView_AdapterViewListProfile_title.setText(profileTitle);
        textView_AdapterViewListProfile_IncomeYear.setText(totalIncomeYear.toString() + " " + currency);
        textView_AdapterViewListProfile_ExpenseYear.setText(totalExpenseYear.toString() + " " + currency);
        textView_AdapterViewListProfile_BalanceYear.setText(balanceYear.toString() + " " + currency);

    }
}
