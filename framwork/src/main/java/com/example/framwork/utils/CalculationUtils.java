package com.example.framwork.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * Created by lenovo on 2017/9/11.
 * 计算帮助类
 */

public class CalculationUtils {
    //计算乘法  位数较大时使用
    public static String multiply2(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    //计算乘法  位数较大时使用
    public static String multiply2(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    //计算乘法  位数较大时使用
    public static String multiply2(BigDecimal v1, double v2) {
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return v1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }
    //计算乘法  位数较大时使用
    public static String divide2(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }
    /**
     * 保留两位小数 四舍五入
     *
     * @param popularity
     * @return
     */
    public static String popularity2(Double popularity) {
        BigDecimal b = new BigDecimal(popularity);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String popularity2(BigDecimal popularity) {
        return popularity.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static BigDecimal popularity2D(Double popularity) {
        BigDecimal b = new BigDecimal(popularity);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calTotal(BigDecimal total, BigDecimal price, int num) {
        total = total.add(price.multiply(new BigDecimal(num)));
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static String sub2S(BigDecimal s, BigDecimal m) {
        return s.subtract(m).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        DecimalFormat df = new java.text.DecimalFormat("#0.00");
        return b1.multiply(b2);
    }

    //计算乘法  位数较大时使用
    public static String formatForDouble(double v1) {
        DecimalFormat df = new java.text.DecimalFormat("#0.00");
        return df.format(v1);
    }

    //比较大小
    public static int compareTo(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }

    public static String removeO(String s) {
        BigDecimal a = new BigDecimal(s);
        return a.stripTrailingZeros().toPlainString();

    }
}
