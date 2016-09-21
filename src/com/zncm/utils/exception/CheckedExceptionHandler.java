package com.zncm.utils.exception;

import com.zncm.utils.L;
import com.zncm.utils.TimeUtils;
import com.zncm.utils.file.FileUtil;
import com.zncm.utils.file.PathUtil;

import java.io.IOException;


//统一处理异常
public class CheckedExceptionHandler {

    public static void handleException(Throwable tr) {
        // if (L.D) {
        // L.w(tr.getClass().getSimpleName(), tr);
        // } else {
        // writeException(tr);
        // }
        L.w(tr.getClass().getSimpleName(), tr);
        writeException(tr);
    }

    private static void writeException(Throwable tr) {

        String exceptionFileName = "myhelperException-" + TimeUtils.getFileSaveTime() + ".log";
        String filePath = null;
        try {
            filePath = FileUtil.getFile(PathUtil.getExceptionPath(), exceptionFileName);
            if (filePath == null) {
                return;
            } else {
                ThrowableOperate.operateException(tr, filePath, false);
            }
        } catch (IOException e) {
            filePath = null;
            e.printStackTrace();
        }
    }
}
