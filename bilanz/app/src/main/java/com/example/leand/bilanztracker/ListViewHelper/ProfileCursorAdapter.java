package com.example.leand.bilanztracker.ListViewHelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leand.bilanztracker.Activitys.MainActivity;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class ProfileCursorAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;
    private GetColumnHelper getColumnHelper;
    private long profileIdOld;

    public ProfileCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getColumnHelper = new GetColumnHelper(c);
        profileIdOld=MainActivity.long_ProfileId;
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

        MainActivity.long_ProfileId=getColumnHelper.getId();

        textView_AdapterViewListProfile_title.setText(getColumnHelper.getProfileTitle());
        textView_AdapterViewListProfile_IncomeYear.setText(getColumnHelper.getCurrencyFormatWithCurrency(getColumnHelper.getTotalIncomeNetYearDouble()));
        textView_AdapterViewListProfile_ExpenseYear.setText(getColumnHelper.getCurrencyFormatWithCurrency(getColumnHelper.getTotalExpenseYearDouble()));
        textView_AdapterViewListProfile_BalanceYear.setText(getColumnHelper.getCurrencyFormatWithCurrency(getColumnHelper.getBalanceYearDouble()));

        MainActivity.long_ProfileId=profileIdOld;
    }
}
