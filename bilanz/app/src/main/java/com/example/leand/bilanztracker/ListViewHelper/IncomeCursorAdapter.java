package com.example.leand.bilanztracker.ListViewHelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leand.bilanztracker.Activitys.IncomeActivity;
import com.example.leand.bilanztracker.DatabaseHelper.GeneralFormatter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class IncomeCursorAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;
    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;

    public IncomeCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getColumnHelper = new GetColumnHelper(c);
        generalFormatter=new GeneralFormatter();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.adapter_view_list_income, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        IncomeActivity.long_IncomeId=getColumnHelper.getId();

        TextView textView_AdapterViewListIncome_title = view.findViewById(R.id.textView_AdapterViewListIncome_Title);
        TextView textView_AdapterViewListIncome_GrossValue = view.findViewById(R.id.textView_AdapterViewListIncome_GrossValue);
        TextView textView_AdapterViewListIncome_NetValue = view.findViewById(R.id.textView_AdapterViewListIncome_NetValue);

        textView_AdapterViewListIncome_title.setText(getColumnHelper.getIncomeTitle());
        textView_AdapterViewListIncome_NetValue.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getIncomeNetYearDouble()));
        textView_AdapterViewListIncome_GrossValue.setText(generalFormatter.getCurrencyFormat(getColumnHelper.getIncomeGrossYearDouble()));
    }
}
