package com.zncm.easysc.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


//全局Application
public class SharedApplication extends Application {
    public Context ctx;
    private String storagePath = null;// 数据存储路径
    public static SharedApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.ctx = this.getApplicationContext();
        instance = this;
    }

    // 全局对象是单例的
    public static SharedApplication getInstance() {
        return instance;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public SharedPreferences getPreferences(String fileString) {
        return this.getPreferences(fileString, Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences(String fileString, int mod) {
        return getSharedPreferences(fileString, mod);
    }

}
