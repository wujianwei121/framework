package com.example.framwork.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by ${wjw} on 2016/5/5.
 */
public class CommonUtil {
    /**
     * @param V 设置下划线
     */
    private final static int kSystemRootStateUnknow = -1;
    private final static int kSystemRootStateDisable = 0;
    private final static int kSystemRootStateEnable = 1;
    private static int systemRootState = kSystemRootStateUnknow;

    public static void setFlags(TextView V) {
        V.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        V.getPaint().setAntiAlias(true);//抗锯齿
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(InputMethodManager mInputMethodManager, View view) {
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(InputMethodManager mInputMethodManager, View view) {
        mInputMethodManager
                .showSoftInput(view, 0);
    }


    private static long lastClickTime = 0;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 复制文本到剪切板
     *
     * @param text 文本
     */
    public static void copy(Activity aty, String text) {

        ClipboardManager clipboard = (ClipboardManager) aty.getSystemService(Context
                .CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText(null, text);
        clipboard.setPrimaryClip(clip);
    }

    /**
     * 获取人气显示 默认保留后四位
     *
     * @param popularity
     * @return
     */
    public static String popularity(int popularity) {
        if (popularity < 10000) {
            return String.valueOf(popularity);
        } else {
            double popularityD = (double) popularity / 10000;
            BigDecimal b = new BigDecimal(popularityD);
            double d = b.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
            return new StringBuilder().append(d).append("万").toString();
        }
    }

    /**
     * 保留两位小数 四舍五入
     *
     * @param popularity
     * @return
     */
    public static String popularityTwo(Double popularity) {
        BigDecimal b = new BigDecimal(popularity);
        double d = b.setScale(2, BigDecimal.ROUND_UP).doubleValue();
        return new StringBuilder().append(d).toString();
    }
    public static double popularityTwod(Double popularity) {
        BigDecimal b = new BigDecimal(popularity);
        double d = b.setScale(2, BigDecimal.ROUND_UP).doubleValue();
        return d;
    }
    /**
     * 保留两位小数 截取
     *
     * @param popularity
     * @return
     */
    public static String popularityTwoDown(Double popularity) {
        BigDecimal b = new BigDecimal(popularity);
        double d = b.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        return new StringBuilder().append(d).toString();
    }

    /**
     * 保留两位小数
     *
     * @return
     */
    public static String popularityTwoDou(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        DecimalFormat df = new DecimalFormat("0.00");
        String result = df.format(b1.multiply(b2).doubleValue());
        return result;
    }

    /**
     * 保留两位小数
     *
     * @return
     */
    public static Double popularityPrice(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    //  f=  "0.00"
    public static String formatDouble(String f, Double d) {
        DecimalFormat df = new DecimalFormat(f);
        return df.format(d);
    }

    /**
     * 获取数据显示 保留小数点后一位
     *
     * @return
     */
    public static String popularityOne(String sPopularity) {
        float popularity = 0;
        try {
            popularity = Float.valueOf(sPopularity);
            if (popularity < 10000) {
                return sPopularity;
            } else if (popularity > 10000 && popularity < 100000000) {
                double popularityD = (double) popularity / 10000;
                BigDecimal b = new BigDecimal(popularityD);
                double d = b.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();
                return new StringBuilder().append(d).append("万").toString();
            } else {
                double popularityD = (double) popularity / 100000000;
                BigDecimal b = new BigDecimal(popularityD);
                double d = b.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();
                return new StringBuilder().append(d).append("亿").toString();
            }
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    /**
     * 判断str1中包含str2的个数
     *
     * @param str1
     * @param str2
     * @return counter
     */
    public static int countStr(String str1, char str2) {
        int count = 0;
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == str2) {
                count++;
            }
        }
        return count;
    }


    /*计算最大页数*/
    public static int getMaxPage(int count, int perpage) {
        if (perpage == 0) {
            return 1;
        } else {
            return count % perpage == 0 ? count / perpage : count / perpage + 1;
        }
    }

    public static String getUUId(Context context) {
        String identity = (String) SPUtils.getInstance().readObject(context, "identity");
        if (identity == null) {
            identity = java.util.UUID.randomUUID().toString().replace("-","_");
            SPUtils.getInstance().saveObject(context, "identity", identity);
        }
        return identity;
    }

    /**
     * 判断是否root  无弹框
     *
     * @return
     */
    public static boolean isRootSystem() {
        if (systemRootState == kSystemRootStateEnable) {
            return true;
        } else if (systemRootState == kSystemRootStateDisable) {
            return false;
        }
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    systemRootState = kSystemRootStateEnable;
                    return true;
                }
            }
        } catch (Exception e) {
        }
        systemRootState = kSystemRootStateDisable;
        return false;
    }

}

