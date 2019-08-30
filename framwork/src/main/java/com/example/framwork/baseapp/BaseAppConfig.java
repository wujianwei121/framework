package com.example.framwork.baseapp;

import android.content.Context;

import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.DLog;
import com.example.framwork.utils.FileUtil;

import java.io.File;
import java.security.PublicKey;

/**
 * Created by lenovo on 2017/2/22.
 */

public class BaseAppConfig {
    public static final String DEVICE_NO_KEY = "device_no";
    private volatile static BaseAppConfig instance;
    /**
     * App根目录.
     */
    public static String APP_PATH_ROOT;
    /**
     * 下载文件目录
     */
    public static String DOWNLOAD_PATH;
    public static String IMAGE_PATH;
    public static String VERSION_NUM;//版本号
    public static String CACHE_PATH;
    public static String DEVICE_NO = "";//设备号
    public static boolean getCachePath = false;
    //设置服务器地址
    public static String SERVICE_PATH = " ";
    //SharedPreferences  name
    public static String SP_NAME = "APP";

    public static void init(Context context, String fileName) {
        APP_PATH_ROOT = FileUtil.getInstance().getRootPath().getAbsolutePath() + File.separator + fileName;
        DOWNLOAD_PATH = APP_PATH_ROOT + File.separator + "downLoad" + File.separator;
        CACHE_PATH = APP_PATH_ROOT + File.separator + "cache" + File.separator;
        IMAGE_PATH = APP_PATH_ROOT + File.separator + "image" + File.separator;
        SP_NAME = fileName + ".db";
        VERSION_NUM = CommonUtil.getVersion(context);
        initFile();
        initAndroidId(context);
    }

    public static void initAndroidId(Context context) {
        DEVICE_NO = CommonUtil.getUUId(context);
        DLog.d("androidid", DEVICE_NO);
    }

    public static void initFile() {
        FileUtil.getInstance().initDirectory(APP_PATH_ROOT);
        getCachePath = FileUtil.getInstance().initDirectory(CACHE_PATH);
        FileUtil.getInstance().initDirectory(DOWNLOAD_PATH);
        FileUtil.getInstance().initDirectory(IMAGE_PATH);
    }

}
