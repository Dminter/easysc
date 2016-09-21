package com.zncm.utils.file;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.zncm.easysc.global.SharedApplication;
import com.zncm.utils.exception.CheckedExceptionHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//获得手机内存储器状态
public class StorageUtil {

    private static Context ctx = SharedApplication.getInstance().ctx;

    /**
     * @return String
     * @throws
     * @Title: getSdcard2StorageDirectory
     */
    public static final String getSdcard2StorageDirectory() {
        return "/mnt/sdcard2";
    }

    /**
     * @return String
     * @throws
     * @Title: getEmmcStorageDirectory
     */
    public static final String getEmmcStorageDirectory() {
        return "/mnt/emmc";
    }

    // 额外挂载点目录
    private static String otherExternalStorageDirectory = null;
    // 额外挂载点状态未知
    private static int kOtherExternalStorageStateUnknow = -1;
    // 额外挂载点不可用
    private static int kOtherExternalStorageStateUnable = 0;
    private static int kOtherExternalStorageStateIdle = 1;
    // 默认额外挂载点状态未知
    private static int otherExternalStorageState = kOtherExternalStorageStateUnknow;

    /**
     * @return String
     * @throws
     * @Title: getOtherExternalStorageDirectory
     */
    public static final String getOtherExternalStorageDirectory() {
        if (otherExternalStorageState == kOtherExternalStorageStateUnable)
            return null;
        if (otherExternalStorageState == kOtherExternalStorageStateUnknow) {
            FstabReader fsReader = new FstabReader();
            if (fsReader.size() <= 0) {
                otherExternalStorageState = kOtherExternalStorageStateUnable;
                return null;
            }
            List<StorageInfo> storages = fsReader.getStorages();
            /* 对于可用空间小于100M的挂载节点忽略掉 */
            long availableSpace = 100 << (20);
            String path = null;
            for (int i = 0; i < storages.size(); i++) {
                StorageInfo info = storages.get(i);
                if (info != null && info.getAvailableSpace() > availableSpace) {
                    availableSpace = info.getAvailableSpace();
                    path = info.getPath();
                }
                if (path == null) {
                    continue;
                }
                if (path.equalsIgnoreCase(getEmmcStorageDirectory())
                        || path.equalsIgnoreCase(getExternalStorageDirectory())
                        || path.equalsIgnoreCase(getSdcard2StorageDirectory())) {
                    path = null;
                }
            }
            otherExternalStorageDirectory = path;
            if (otherExternalStorageDirectory != null) {
                otherExternalStorageState = kOtherExternalStorageStateIdle;
            } else {
                otherExternalStorageState = kOtherExternalStorageStateUnable;
            }
        }
        return otherExternalStorageDirectory;
    }


    /**
     * 获取内部存储Directory
     *
     * @return String
     * @throws
     * @Title: getInternalStorageDirectory
     */
    public static final String getInternalStorageDirectory() {
        String internalStorageDirectory = null;
        File file = ctx.getFilesDir();
        if (file != null) {
            internalStorageDirectory = file.getAbsolutePath();
            if (!file.exists())
                file.mkdirs();
        }
        return internalStorageDirectory;
    }

    /**
     * 获取外部存储目录
     *
     * @return String
     * @throws
     * @Title: getExternalStorageDirectory
     */
    public static final String getExternalStorageDirectory() {
        File file = Environment.getExternalStorageDirectory();
        if (file != null)
            return file.getAbsolutePath();
        return null;
    }

    /**
     * @param path
     * @return long
     * @throws
     * @Title: getAvailableSpace
     */
    public static long getAvailableSpace(String path) {
        if (path == null) {
            return -1;
        }
        File file = new File(path);
        if (!file.exists()) {
            return -1;
        }
        StatFs statfs = new StatFs(path);
        long blockSize = statfs.getBlockSize();
        long availableBlocks = statfs.getAvailableBlocks();
        return blockSize * availableBlocks;
    }

    /**
     * @param path
     * @return long
     * @throws
     * @Title: getTotalSpace
     */
    public static long getTotalSpace(String path) {
        if (path == null) {
            return -1;
        }
        File file = new File(path);
        if (!file.exists()) {
            return -1;
        }
        StatFs statfs = new StatFs(path);
        long blockSize = statfs.getBlockSize();
        long totalBlocks = statfs.getBlockCount();
        return blockSize * totalBlocks;
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return long
     * @throws
     * @Title: getInternalStorageAvailableSpace
     */
    public static long getInternalStorageAvailableSpace() {
        String path = getInternalStorageDirectory();
        return getAvailableSpace(path);
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return long
     * @throws
     * @Title: getInternalStorageTotalSpace
     */
    public static long getInternalStorageTotalSpace() {
        File path = Environment.getDataDirectory();
        if (path != null) {
            return getTotalSpace(path.getPath());
        } else {
            return 0;
        }
    }

    /**
     * @return long
     * @throws
     * @Title: getExternaltStorageAvailableSpace
     */
    public static long getExternaltStorageAvailableSpace() {
        if (!(Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED))) {
            return 0;
        }
        String path = getExternalStorageDirectory();
        return getAvailableSpace(path);
    }

    /**
     * @return long
     * @throws
     * @Title: getExternaltStorageTotalSpace
     */
    public static long getExternaltStorageTotalSpace() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        String path = getExternalStorageDirectory();
        return getTotalSpace(path);
    }

    /**
     * @return long
     * @throws
     * @Title: getSdcard2StorageAvailableSpace
     */
    public static long getSdcard2StorageAvailableSpace() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return 0;
        }
        String path = getSdcard2StorageDirectory();
        return getAvailableSpace(path);
    }

    /**
     * @return long
     * @throws
     * @Title: getSdcard2StorageTotalSpace
     */
    public static long getSdcard2StorageTotalSpace() {
        String path = getSdcard2StorageDirectory();
        return getTotalSpace(path);
    }

    /**
     * @return long
     * @throws
     * @Title: getEmmcStorageAvailableSpace
     */
    public static long getEmmcStorageAvailableSpace() {
        String path = getEmmcStorageDirectory();
        return getAvailableSpace(path);
    }

    /**
     * @return long
     * @throws
     * @Title: getEmmcStorageTotalSpace
     */
    public static long getEmmcStorageTotalSpace() {
        String path = getEmmcStorageDirectory();
        return getTotalSpace(path);
    }

    /**
     * @return long
     * @throws
     * @Title: getOtherExternaltStorageAvailableSpace
     */
    public static long getOtherExternaltStorageAvailableSpace() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return -1;
        }
        if (otherExternalStorageState == kOtherExternalStorageStateUnable)
            return -1;
        if (otherExternalStorageDirectory == null) {
            getOtherExternalStorageDirectory();
        }
        if (otherExternalStorageDirectory == null)
            return -1;
        return getAvailableSpace(otherExternalStorageDirectory);
    }

    public static long getOtherExternaltStorageTotalSpace() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return -1;
        }
        if (otherExternalStorageState == kOtherExternalStorageStateUnable)
            return -1;
        if (otherExternalStorageDirectory == null) {
            getOtherExternalStorageDirectory();
        }
        if (otherExternalStorageDirectory == null)
            return -1;
        return getTotalSpace(otherExternalStorageDirectory);
    }

    /**
     * 是否为手机内存路径
     *
     * @param path
     * @return boolean
     * @throws
     * @Title: isInternalStoragePath
     */
    public static boolean isInternalStoragePath(String path) {
        String rootPath = getInternalStorageDirectory();
        if (path != null && path.startsWith(rootPath))
            return true;
        return false;
    }

    /**
     * 获取一个大文件的存储位置
     *
     * @param saveSize
     * @return String
     * @throws
     * @Title: getSaveDiskPath
     */
    public static String getSaveDiskPath(long saveSize) {
        String savePath = null;
        if (getExternaltStorageAvailableSpace() > saveSize) {
            savePath = getExternalStorageDirectory();
        } else if (getSdcard2StorageAvailableSpace() > saveSize) {
            savePath = getSdcard2StorageDirectory();
        } else if (getEmmcStorageAvailableSpace() > saveSize) {
            savePath = getEmmcStorageDirectory();
        } else if (getOtherExternaltStorageAvailableSpace() > saveSize) {
            savePath = getOtherExternalStorageDirectory();
        } else if (getInternalStorageAvailableSpace() > saveSize) {
            savePath = getInternalStorageDirectory();
        }
        return savePath;
    }

    public static class FstabReader {
        final List<StorageInfo> storages = new ArrayList<StorageInfo>();

        public FstabReader() {
            init();
        }

        public int size() {
            return storages == null ? 0 : storages.size();
        }

        public List<StorageInfo> getStorages() {
            return storages;
        }

        public void init() {
            File file = new File("/system/etc/vold.fstab");
            if (file.exists()) {
                FileReader fr = null;
                BufferedReader br = null;
                try {
                    fr = new FileReader(file);
                    if (fr != null) {
                        br = new BufferedReader(fr);
                        String s = br.readLine();
                        while (s != null) {
                            if (s.startsWith("dev_mount")) {
                                /* "\s"转义符匹配的内容有：半/全角空格 */
                                String[] tokens = s.split("\\s");
                                String path = tokens[2]; // mount_point

                                long availableSpace = getAvailableSpace(path);
                                if (availableSpace > 0) {
                                    StorageInfo storage = new StorageInfo(path,
                                            availableSpace, getTotalSpace(path));
                                    storages.add(storage);
                                }
                            }
                            s = br.readLine();
                        }
                    }
                } catch (Exception e) {
                    CheckedExceptionHandler.handleException(e);
                } finally {
                    if (fr != null)
                        try {
                            fr.close();
                        } catch (IOException e) {
                            CheckedExceptionHandler.handleException(e);
                        }
                    if (br != null)
                        try {
                            br.close();
                        } catch (IOException e) {
                            CheckedExceptionHandler.handleException(e);
                        }
                }
            }
        }
    }

    static class StorageInfo implements Comparable<StorageInfo> {
        private String path;
        private long availableSpace;
        private long totalSpace;

        StorageInfo(String path, long availableSpace, long totalSpace) {
            this.path = path;
            this.availableSpace = availableSpace;
            this.totalSpace = totalSpace;
        }

        @Override
        public int compareTo(StorageInfo another) {
            if (null == another)
                return 1;

            return this.totalSpace - another.totalSpace > 0 ? 1 : -1;
        }

        long getAvailableSpace() {
            return availableSpace;
        }

        long getTotalSpace() {
            return totalSpace;
        }

        String getPath() {
            return path;
        }
    }
}
