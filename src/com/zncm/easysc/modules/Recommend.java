package com.zncm.easysc.modules;

import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;
import com.zncm.easysc.R;
public class Recommend extends BaseHomeActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);
        getSupportActionBar().setTitle("作者其他软件");
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}