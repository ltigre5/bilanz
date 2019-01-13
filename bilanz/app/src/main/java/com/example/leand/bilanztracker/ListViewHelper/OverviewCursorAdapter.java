package com.example.leand.bilanztracker.ListViewHelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leand.bilanztracker.Activitys.ExpenseActivity;
import com.example.leand.bilanztracker.DatabaseHelper.GeneralFormatter;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class OverviewCursorAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;
    private GetColumnHelper getColumnHelper;
    private GeneralFormatter generalFormatter;
    private long expenseIdOld;

    public OverviewCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getColumnHelper = new GetColumnHelper(c);
        generalFormatter=new GeneralFormatter();
        expenseIdOld=ExpenseActivity.long_ExpenseId;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.adapter_view_list_overview, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView_AdapterViewListOverview_title = view.findViewById(R.id.textView_AdapterViewListOverview_Title);

        ExpenseActivity.long_ExpenseId=getColumnHelper.getId();

        textView_AdapterViewListOverview_title.setText(getColumnHelper.getExpenseTitle());

        ExpenseActivity.long_ExpenseId=expenseIdOld;
    }
}
