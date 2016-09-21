package com.zncm.easysc.global;

//常量
public class GlobalConstants {
    public static final String KEY_ID = "key_id";

    
    public static final int RINGTONE_PICKED_CODE = 1;
    public static final long DAY_NEAR = 24 * 60 * 60 * 1000 - 1;
    public static final long DAY = 24 * 60 * 60 * 1000;
    public static final long HALF_DAY = 12 * 60 * 60 * 1000;
    public static final long HALF_HOUR = 30 * 60 * 1000;
    public static final int ID_DELETE = 1;
    public static final int ID_UPDATE = 2;
    public static final int ID_FINISH = 3;
    public static final int ID_RESTART = 4;
    public static final int ID_MARK = 5;
    public static final int ID_SHARE = 6;
    public static final int ID_SETWALLPAPER = 7;
    public static final int ID_COPY = 8;
    public static final int ID_EDIT = 9;
    public static final int ID_SEND_TO_DESK = 10;
    public static final int ID_SEND_TO_NOTIFY = 11;

    public static final int TAG_NUM_PAGE = 10;
    public static int TAG_PAGE_NUM = 8;
    public static long DB_PAGE_SIZE = 50;
    public static long DB_IMG_PAGE_SIZE = 10;
    public static final int REQUESTCODE_ADD = 100;
    public static final String KEY_LIST_DATA = "key_list_data ";
    public static final String KEY_COMMENT_ID = "comment_id ";
    public static final String KEY_TYPE = "key_type";
    public static final int KEY_TYPE_PICTURE = 0;
    public static final int KEY_TYPE_NOTE = 1;
    public static final int KEY_TYPE_ACCOUNT = 2;
    public static final int KEY_TYPE_CHECKLIST = 3;
    public static final int KEY_TYPE_DAY = 4;
    public static final int KEY_TYPE_LIGHT = 5;
    public static final int KEY_TYPE_TOOLS = 6;
    public static final int KEY_TYPE_SETTING = 7;

    // public static final int KEY_TYPE_WEATHER = 5;
    // public static final int KEY_TYPE_CALENDAR = 6;
    // public static final int KEY_TYPE_ALARM = 7;
    // public static final int KEY_TYPE_BACK_UP = 8;

    // 头部数据传递KEY
    public static final String KEY_TOP_BANNER_TITLE = "title";
    public static final String KEY_PARAM_DATA = "basedata";
    /**
     * 网络超时
     */
    public static final int NETWORK_TIME_OUT = 20 * 1000;
    /**
     * default IO size
     */
    public static final int IO_BUFFER_SIZE = 8 * 1024;
    // 请求数据编码
    public static final String HTTP_CHARSET = "utf-8";
    /**
     * 文件存储根目录
     */
    public static final String PATH_ROOT = "MyHelper";
    /**
     * 默认图片缓存大小
     */
    public static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 20; // 20MB
    public static final int ORIGINAL_DISK_CACHE_SIZE = 1024 * 1024 * 15; // 15MB
}
