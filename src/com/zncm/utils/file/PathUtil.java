package com.zncm.utils.file;


import com.zncm.easysc.global.GlobalConstants;
import com.zncm.easysc.global.SharedApplication;
import com.zncm.utils.StrUtil;
import com.zncm.utils.sp.StatedPerference;

import java.io.File;

//获取存储位置的相对路径
public class PathUtil {
    public static final String PATH_VOICE = "voice"; // 录音目录
    public static final String PATH_DRAWING = "drawing"; // 涂鸦目录
    public static final String PATH_CACHE = "cache"; // 缓存目录
    public static final String PATH_DOWNLOAD = "download"; // 下载目录
    public static final String PATH_CONFIG = "config"; // 配置目录
    public static final String PATH_PICTURE = "picture"; // 图片目录
    public static final String PATH_DATA = "data"; // 数据目录
    public static final String PATH_AVATAR = "avatar"; // 头像目录
    public static final String PATH_EXCEPTION = "exception"; // 异常信息目录
    public static final String PATH_DB = "db"; // 数据库目录

    private static final String defaultDiskPath = SharedApplication.getInstance().getStoragePath();

    private static String getPathFolder(String path) {
        if (StrUtil.notEmptyOrNull(defaultDiskPath)) {
            return FileUtil.getFolder(defaultDiskPath + File.separator + GlobalConstants.PATH_ROOT + File.separator
                    + path + File.separator);
        } else {
            String tempPath = getTemporaryDiskPath(GlobalConstants.DEFAULT_DISK_CACHE_SIZE);
            if (StrUtil.notEmptyOrNull(tempPath)) {
                return FileUtil.getFolder(tempPath + File.separator + GlobalConstants.PATH_ROOT + File.separator + path
                        + File.separator);
            }
        }
        return null;
    }

    public static String getDrawingPath() {
        return getPathFolder(PATH_DRAWING);
    }

    public static String getVoicePath() {
        return getPathFolder(PATH_VOICE);
    }

    /**
     * 获取临时的文件夹
     *
     * @return String
     * @throws
     * @Title: getTemporaryDiskPath
     */
    public static String getTemporaryDiskPath(long fileSize) {
        String temporayDiskPath = StatedPerference.getTemporaryDiskPath();
        if (temporayDiskPath != null && StorageUtil.getTotalSpace(temporayDiskPath) > 0) {
            return temporayDiskPath;
        } else {
            String diskPath = StorageUtil.getSaveDiskPath(fileSize);
            if (StrUtil.isEmptyOrNull(diskPath)) {
                StatedPerference.setTemporaryDiskPath(diskPath);
            }
            return diskPath;
        }
    }

    /**
     * 获取默认缓存路径
     *
     * @return String
     * @throws
     * @Title: getCachePath
     */
    public static String getCachePath() {
        String path = getPathFolder(PATH_CACHE);
        if (path == null) {
            return null;
        } else {
            if (StorageUtil.getAvailableSpace(path) < GlobalConstants.DEFAULT_DISK_CACHE_SIZE) {
                return null;
            } else {
                return path;
            }
        }
    }

    /**
     * 获取默认下载路径
     *
     * @return String
     * @throws
     * @Title: getDownloadPath
     */
    public static String getDownloadPath() {
        return getPathFolder(PATH_DOWNLOAD);
    }

    /**
     * 获取默认配置路径
     *
     * @return String
     * @throws
     * @Title: getConfigPath
     */
    public static String getConfigPath() {
        return getPathFolder(PATH_CONFIG);
    }

    /**
     * 获取默认图片路径
     *
     * @return String
     * @throws
     * @Title: getPicturePath
     */
    public static String getPicturePath() {
        return getPathFolder(PATH_PICTURE);
    }

    /**
     * 获取默认数据路径
     *
     * @return String
     * @throws
     * @Title: getDataPath
     */
    public static String getDataPath() {
        return getPathFolder(PATH_DATA);
    }

    /**
     * 获取默认头像路径
     *
     * @return String
     * @throws
     * @Title: getAvatarPath
     */
    public static String getAvatarPath() {
        return getPathFolder(PATH_AVATAR);
    }

    /**
     * 获取默认异常路径
     *
     * @return String
     * @throws
     * @Title: getAvatarPath
     */
    public static String getExceptionPath() {
        String exceptionPath = getDataPath() + File.separator + PATH_EXCEPTION;
        return FileUtil.getFolder(exceptionPath);
    }

    public static String getPathDb() {
        return getPathFolder(PATH_DB);
    }

}
