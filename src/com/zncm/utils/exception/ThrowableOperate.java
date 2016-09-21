package com.zncm.utils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;

import com.umeng.analytics.MobclickAgent;
import com.zncm.easysc.global.GlobalNotice;
import com.zncm.easysc.global.SharedApplication;
import com.zncm.utils.L;
import com.zncm.utils.file.FileUtil;

//统一处理异常
public class ThrowableOperate {

    public static final String VERSION_UNKOWN_STRING = "version_unkown";

    private static final Context ctx = SharedApplication.getInstance().ctx;

    /**
     * 获取错误的信息
     *
     * @param tr
     * @return
     */
    private static String getErrorInfo(Throwable tr) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        tr.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

    /**
     * @return String
     * @throws
     * @Title: getVersionInfo
     */
    @SuppressWarnings("unused")
    private static String getVersionInfo() {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo info = pm.getPackageInfo(ctx.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return VERSION_UNKOWN_STRING;
        }
    }

    /**
     * @param tr
     * @param filePath
     * @return void
     * @throws
     * @Title: handleException
     */
    public static void operateException(final Throwable tr, final String filePath, final Boolean bShow) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String errorInfo = getErrorInfo(tr);
                if (errorInfo == null) {
                    return;
                }
                if (bShow) {
                    L.toastShort(GlobalNotice.ERROR_APPLICATION_UI_EXCEPTION);
                    L.e(tr.getClass().getSimpleName(), tr);
                }
                FileUtil.writeStringToFile(filePath, errorInfo);

                errorInfo = "reportError-" + errorInfo;
                // BUG上报友盟
                MobclickAgent.reportError(SharedApplication.getInstance().getApplicationContext(), errorInfo);

                Looper.loop();
            }
        }).start();
    }
}
