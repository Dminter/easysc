package com.zncm.utils.exception;

import android.os.Looper;

import com.zncm.utils.L;
import com.zncm.utils.file.FileUtil;
import com.zncm.utils.file.PathUtil;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;


//统一处理异常
public class UnCheckedExceptiondHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler mDefaultHandler;
    private static UnCheckedExceptiondHandler instance;

    private static long mainTheadID = Looper.getMainLooper().getThread()
            .getId();

    public static UnCheckedExceptiondHandler getInstance() {
        if (instance == null) {
            instance = new UnCheckedExceptiondHandler();
        }
        return instance;
    }

    /**
     * 获取系统默认的UncaughtException处理器, 设置该UnCheckExceptiondHandler为程序的默认处理器
     *
     * @Title: init
     */
    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     *
     * @param thread
     * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(Thread,
     * Throwable)
     */
    @Override
    public void uncaughtException(Thread thread, Throwable tr) {
        if (!handleException(thread, tr) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, tr);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * @param thread
     * @param tr
     * @return boolean
     * @throws
     * @Title: handleException
     */
    private boolean handleException(Thread thread, Throwable tr) {
        if (thread.getId() == mainTheadID) {
            if (L.D) {
                L.e(tr.getClass().getSimpleName(), tr);
                return false;
            } else {
                String exceptionFileName = "unCheckedException-"
                        + System.currentTimeMillis() + ".log";
                String filePath = null;
                try {
                    filePath = FileUtil.getFile(PathUtil.getExceptionPath(),
                            exceptionFileName);
                    if (filePath == null) {
                        return true;
                    } else {
                        ThrowableOperate.operateException(tr, filePath, true);
                    }
                } catch (IOException e) {
                    filePath = null;
                    e.printStackTrace();
                }
                return true;
            }
        }
        loopLoop();
        return false;
    }

    public static void loopLoop() {
        try {
            Looper.prepare();
            Looper.loop();
        } catch (Throwable tr) {
            L.e("exception", tr);
        } finally {
            L.d("finally");
        }
    }
}
