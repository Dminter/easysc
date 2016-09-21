package com.zncm.easysc.modules;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.zncm.component.ormlite.DatabaseHelper;
import com.zncm.component.pullrefresh.PullToRefreshListView;
import com.zncm.easysc.R;
import com.zncm.easysc.global.SharedApplication;

public class BaseLvActivity extends SherlockFragmentActivity {

    protected PullToRefreshListView plListView;
    protected ListView lvBase;
    protected View view;
    protected LayoutInflater mInflater;

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
        setContentView(R.layout.view_lv_base);
        plListView = (PullToRefreshListView) findViewById(R.id.lvBase);
        lvBase = plListView.getRefreshableView();
    }

    public void loadComplete() {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                plListView.onRefreshComplete();
                plListView.onLoadMoreComplete();
            }
        });
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
