package com.example.leand.bilanztracker.ListViewHelper;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leand.bilanztracker.Activitys.EditIncomeActivity;
import com.example.leand.bilanztracker.Activitys.ExpenseActivity;
import com.example.leand.bilanztracker.DatabaseHelper.GetColumnHelper;
import com.example.leand.bilanztracker.R;

public class DeductionCursorAdapter extends CursorAdapter {
    private LayoutInflater layoutInflater;
    private GetColumnHelper getColumnHelper;
    private long deductionIdOld;


    public DeductionCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getColumnHelper = new GetColumnHelper(c);
        deductionIdOld=EditIncomeActivity.long_DeductionId;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.adapter_view_list_deduction, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView_AdapterViewListDeduction_title = view.findViewById(R.id.textView_AdapterViewListDeduction_Title);
        TextView textView_AdapterViewListDeduction_Value = view.findViewById(R.id.textView_AdapterViewListDeduction_Value);

        EditIncomeActivity.long_DeductionId=getColumnHelper.getId();

        textView_AdapterViewListDeduction_title.setText(getColumnHelper.getDeductionTitle());
        textView_AdapterViewListDeduction_Value.setText(getColumnHelper.getDeductionPercentageStringPercent());

        EditIncomeActivity.long_DeductionId=deductionIdOld;
    }
}