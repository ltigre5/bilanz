package com.example.leand.bilanztracker.ListViewHelper;

import android.content.Context;
import android.database.Cursor;

import com.example.leand.bilanztracker.Activitys.MainActivity;

public class ListViewAdapter {
    private Context context;
    private Cursor cursor;

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    // get Cursor
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // GetAdapters

    /**
     * Get the ProfileListViewAdapter
     *
     * @return ProfileListViewAdapter
     */

    public ProfileCursorAdapter getProfileListViewAdapter() {
        cursor = MainActivity.myDbMain.getAllRowsProfile();
        ProfileCursorAdapter profileCursorAdapter = new ProfileCursorAdapter(context, cursor, 0);
        return profileCursorAdapter;
    }

    // GetAdapters
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // End

}
