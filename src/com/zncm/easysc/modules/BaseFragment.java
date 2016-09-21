package com.zncm.easysc.modules;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.zncm.component.ormlite.DatabaseHelper;
import com.zncm.component.pullrefresh.PullToRefreshListView;
import com.zncm.easysc.R;
import com.zncm.easysc.global.SharedApplication;

public abstract class BaseFragment extends SherlockFragment {
    protected PullToRefreshListView plListView;
    protected ListView lvBase;
    protected View view;
    protected LayoutInflater mInflater;
    private DatabaseHelper databaseHelper = null;
    protected ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        view = inflater.inflate(R.layout.view_lv_base, null);
        plListView = (PullToRefreshListView) view.findViewById(R.id.lvBase);
        lvBase = plListView.getRefreshableView();
        actionBar = getSherlockActivity().getSupportActionBar();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(SharedApplication.getInstance().ctx, DatabaseHelper.class);
        }
        return databaseHelper;
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

}
