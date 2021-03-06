package com.example.leand.bilanztracker.ListViewHelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leand.bilanztracker.Activitys.MainActivity;
import com.example.leand.bilanztracker.DatabaseHelper.GeneralFormatter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class ProfileCursorAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;
    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;
    private long profileIdOld;

    public ProfileCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getColumnHelper = new GetColumnHelper(c);
        generalFormatter = new GeneralFormatter();
        profileIdOld = MainActivity.long_ProfileId;
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
        TextView textView_AdapterViewListProfile_IncomeMonthNet = view.findViewById(R.id.textView_AdapterViewListProfile_IncomeMonthNet);
        TextView textView_AdapterViewListProfile_IncomeMonthGross = view.findViewById(R.id.textView_AdapterViewListProfile_IncomeMonthGross);

        MainActivity.long_ProfileId = getColumnHelper.getId();

        textView_AdapterViewListProfile_title.setText(getColumnHelper.getProfileTitle());
        textView_AdapterViewListProfile_IncomeMonthGross.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getTotalIncomeGrossYearDouble()));
        textView_AdapterViewListProfile_IncomeMonthNet.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getTotalIncomeNetYearDouble()));
        textView_AdapterViewListProfile_ExpenseYear.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getTotalExpenseYearDouble()));
        textView_AdapterViewListProfile_BalanceYear.setText(generalFormatter.getCurrencyFormatMonth(getColumnHelper.getBalanceYearDouble()));

        MainActivity.long_ProfileId = profileIdOld;
    }
}
