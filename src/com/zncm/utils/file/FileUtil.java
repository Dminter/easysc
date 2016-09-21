package com.zncm.utils.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zncm.easysc.global.GlobalConstants;
import com.zncm.easysc.global.GlobalNotice;
import com.zncm.utils.L;
import com.zncm.utils.StrUtil;
import com.zncm.utils.exception.CheckedExceptionHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


//文件工具类
public class FileUtil {

    /**
     * 根据path创建文件夹，创建成功则返回路径，不成功返回空
     *
     * @param folderName
     * @return String
     * @throws
     * @Title: getFolder
     */
    public static String getFolder(String folderName) {
        if (folderName == null) {
            return null;
        }
        File dir = NativeFileUtil.createFolder(folderName);
        if (dir != null) {
            return dir.getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 创建文件
     *
     * @param folderString
     * @param fileName
     * @return String
     * @throws
     * @Title: getFile
     */
    public static String getFile(String folderString, String fileName)
            throws IOException {
        if (folderString == null | fileName == null) {
            return null;
        }
        File folder = new File(folderString);
        if (folder.exists()) {
            File file = NativeFileUtil.createFile(folderString + File.separator
                    + fileName);
            if (file != null) {
                return file.getAbsolutePath();
            } else {
                return null;
            }
        } else {
            folder = NativeFileUtil.createFolder(folderString);
            if (folder != null) {
                File file = NativeFileUtil.createFile(folder.getAbsolutePath()
                        + File.separator + fileName);
                if (file != null) {
                    return file.getAbsolutePath();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    /**
     * 判断文件是否存在，不存在则创建
     *
     * @param filePath
     * @return
     * @throws java.io.IOException
     * @Description
     */
    private static File createOrReadFile(String filePath) throws IOException {
        if (filePath == null) {
            if (L.D) {
                L.e(GlobalNotice.ERROR_FILE_PATH_NULL);
            }
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            String fileName = StrUtil.getFileNameByPath(filePath);
            if (fileName == null) {
                if (L.D) {
                    L.e("write path error");
                }
                return null;
            } else {
                String path = getFile(file.getParent(), fileName);
                if (path == null || !path.equals(filePath)) {
                    if (L.D) {
                        L.e("create file error");
                    }
                    return null;
                }
            }
        }
        return file;
    }

    /**
     * @param filePath
     * @param content
     * @return void
     * @throws
     * @Title: writeStringToFile
     */
    public static void writeStringToFile(String filePath, String content) {
        if (filePath == null) {
            if (L.D) {
                L.e(GlobalNotice.ERROR_FILE_PATH_NULL);
            }
            return;
        }
        BufferedWriter writer = null;
        try {
            File file = createOrReadFile(filePath);
            if (file == null) {
                return;
            }
            writer = new BufferedWriter(new FileWriter(file),
                    GlobalConstants.IO_BUFFER_SIZE);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            CheckedExceptionHandler.handleException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception tr) {
                    CheckedExceptionHandler.handleException(tr);
                }
            }
        }
    }

    public static List<String> getArrayFromFile(String filePath) {

        BufferedReader reader = null;
        try {

            File file = createOrReadFile(filePath);
            if (file == null) {
                return null;
            }
            List<String> contentList = new ArrayList<String>();
            reader = new BufferedReader(new FileReader(file),
                    GlobalConstants.IO_BUFFER_SIZE);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                contentList.add(tempString);
            }
            reader.close();
            return contentList;
        } catch (IOException e) {
            CheckedExceptionHandler.handleException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException tr) {
                    CheckedExceptionHandler.handleException(tr);
                }
            }
        }
        return null;
    }

    /**
     * @param filePath
     * @return
     * @Description
     */
    public static String getStringFromFile(String filePath) {

        BufferedReader reader = null;
        try {
            File file = createOrReadFile(filePath);
            if (file == null) {
                return null;
            }
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new FileReader(file),
                    GlobalConstants.IO_BUFFER_SIZE);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                buffer.append(tempString);
            }
            return buffer.toString();
        } catch (IOException e) {
            CheckedExceptionHandler.handleException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException tr) {
                    CheckedExceptionHandler.handleException(tr);
                }
            }
        }
        return null;
    }

    /**
     * 从本地获取bitmap
     *
     * @param url
     * @return
     * @Description
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            CheckedExceptionHandler.handleException(e);
            return null;
        }
    }

    /**
     * input获取String
     *
     * @param is
     * @return
     * @throws java.io.IOException
     * @Description
     */
    public static String inputStreamToString(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    /**
     * @param is
     * @param os
     * @throws java.io.IOException
     * @Description
     */
    public static void inputStreamToOutputStream(InputStream is, OutputStream os)
            throws IOException {
        final int bufsize = GlobalConstants.IO_BUFFER_SIZE * 10;
        final byte[] cbuf = new byte[bufsize];

        for (int readBytes = is.read(cbuf, 0, bufsize); readBytes > 0; readBytes = is
                .read(cbuf, 0, bufsize)) {
            os.write(cbuf, 0, readBytes);
        }
    }
}
