package com.zncm.utils.sp;

import android.content.SharedPreferences;

//对系统SharedPerference的封装
public class StatedPerference extends SharedPerferenceBase {

    private static final String STATE_PREFERENCE = "state_preference";
    private static SharedPreferences sharedPreferences;

    enum Key {
        default_disk_path, // 程序数据默认存储位置
        temporary_disk_path, // 缓存路径
        font_size, //字体大小
        comment_open, //注释展开
        app_version_code, // 程序版本
        is_first, // 首次打开
        install_time
    }

    public static void setAppVersionCode(Integer app_version_code) {
        putInt(getSharedPreferences(), Key.app_version_code.toString(), app_version_code);
    }

    public static Integer getAppVersionCode() {
        return getInt(getSharedPreferences(), Key.app_version_code.toString(), 0);
    }

    public static void setIsFirst(Boolean is_first) {
        putBoolean(getSharedPreferences(), Key.is_first.toString(), is_first);
    }

    public static Boolean getIsFirst() {
        return getBoolean(getSharedPreferences(), Key.is_first.toString(), true);
    }

    public static void setCommentOpen(Boolean comment_open) {
        putBoolean(getSharedPreferences(), Key.comment_open.toString(), comment_open);
    }

    public static Boolean getCommentOpen() {
        return getBoolean(getSharedPreferences(), Key.comment_open.toString(), false);
    }


    public static void setTemporaryDiskPath(String path) {
        putString(getSharedPreferences(), Key.temporary_disk_path.toString(), path);
    }

    public static String getTemporaryDiskPath() {
        return getString(getSharedPreferences(), Key.temporary_disk_path.toString(), "");
    }

    public static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null)
            sharedPreferences = getPreferences(STATE_PREFERENCE);
        return sharedPreferences;
    }

    public static void setDefaultDiskPath(String path) {
        putString(getSharedPreferences(), Key.default_disk_path.toString(), path);
    }

    public static String getDefaultDiskPath() {
        return getString(getSharedPreferences(), Key.default_disk_path.toString(), "");
    }

    public static void setFontSize(Float font_size) {
        putFloat(getSharedPreferences(), Key.font_size.toString(), font_size);
    }

    public static Float getFontSize() {
        return getFloat(getSharedPreferences(), Key.font_size.toString(), 15.0f);
    }


    public static void setInstallTime(Long install_time) {
        putLong(getSharedPreferences(), Key.install_time.toString(), install_time);
    }

    public static Long getInstallTime() {
        return getLong(getSharedPreferences(), Key.install_time.toString(), 0l);
    }

}
