package com.zncm.easysc.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.umeng.analytics.MobclickAgent;
import com.zncm.component.ormlite.DatabaseHelper;
import com.zncm.component.view.HackyViewPager;
import com.zncm.easysc.R;
import com.zncm.easysc.data.base.ScData;
import com.zncm.easysc.global.GlobalConstants;
import com.zncm.easysc.global.SharedApplication;
import com.zncm.utils.L;
import com.zncm.utils.StrUtil;
import com.zncm.utils.exception.CheckedExceptionHandler;

import java.util.ArrayList;

public class ScPagerActivity extends SherlockFragmentActivity {

    private ViewPager mViewPager;
    @SuppressWarnings("rawtypes")
    private ArrayList list;
    private int current;
    private ArrayList<ScData> datas = null;
    private RuntimeExceptionDao<ScData, Integer> dao = null;
    private ActionBar actionBar;
    private DatabaseHelper databaseHelper = null;
    private String title;

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(SharedApplication.getInstance().ctx, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sc_scan);
        mViewPager = (HackyViewPager) findViewById(R.id.vpCy);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        current = getIntent().getExtras().getInt("current");
        title = getIntent().getExtras().getString("title");
        actionBar.setTitle(title);
        list = getIntent().getExtras().getParcelableArrayList(GlobalConstants.KEY_LIST_DATA);
        datas = (ArrayList<ScData>) list.get(0);
        mViewPager.setAdapter(new SamplePagerAdapter(datas));
        mViewPager.setCurrentItem(current);
    }

    class SamplePagerAdapter extends PagerAdapter {
        private ArrayList<ScData> datas;

        public SamplePagerAdapter(ArrayList<ScData> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            if (datas == null) {
                return 0;
            } else {
                return datas.size();
            }

        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            final Context ctx = container.getContext();
            final DetailsView view = new DetailsView(ctx);
            final ScData data = datas.get(position);
            view.getTvTitle().setText(StrUtil.replaceD(data.getTitle()));
            view.getTvAuthor().setText(data.getAuthor());
            view.getTvContent().setText(StrUtil.replaceD(data.getContent()));
            view.getTvComment().setText(StrUtil.replaceD(data.getComment()));
            view.getTvPage().setText((position + 1) + "/" + datas.size());
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }


    // umeng

    @Override
    protected void onResume() {

        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {

        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("net").setIcon(R.drawable.net).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("collecte").setIcon(R.drawable.star_empty).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("share").setIcon(R.drawable.abs__ic_menu_share_holo_dark).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getTitle().equals("net")) {
            if (datas != null && StrUtil.notEmptyOrNull(datas.get(mViewPager.getCurrentItem()).getTitle())) {
                Intent intent = new Intent(this,
                        BikeWebView.class);
                intent.putExtra("word", datas.get(mViewPager.getCurrentItem()).getTitle());
                startActivity(intent);
            }
        }
        if (item.getTitle().equals("collecte")) {
            try {
                if (dao == null) {
                    dao = getHelper().getScDataDao();
                }
                if (datas.get(mViewPager.getCurrentItem()) != null) {
                    ScData data = datas.get(mViewPager.getCurrentItem());
                    data.setStatus(1);//喜欢
                    dao.update(data);
                    L.toastShort("已收藏");
                }
            } catch (Exception e) {
                CheckedExceptionHandler.handleException(e);
            }
        }


        if (item.getTitle().equals("share")) {
            if (datas != null && StrUtil.notEmptyOrNull(datas.get(mViewPager.getCurrentItem()).getTitle())) {
                StrUtil.SendTo(ScPagerActivity.this, datas.get(mViewPager.getCurrentItem()).getTitle() + "\n" + StrUtil.replaceD(datas.get(mViewPager.getCurrentItem()).getContent()));
            }
        }


        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return false;
    }


}
