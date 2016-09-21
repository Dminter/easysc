package com.zncm.easysc.modules;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.zncm.component.ormlite.DatabaseHelper;
import com.zncm.easysc.global.SharedApplication;

public class BaseHomeActivity extends SherlockFragmentActivity {
    protected ActionBar actionBar;


    private DatabaseHelper databaseHelper = null;

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(SharedApplication.getInstance().ctx, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }
}
