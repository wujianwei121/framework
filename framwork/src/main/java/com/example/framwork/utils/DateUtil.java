package com.example.framwork.utils;

import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期操作工具类.
 *
 * @author wujw
 */

public class DateUtil {
    public static String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_YMD = "yyyy-MM-dd";
    public static String FORMAT_MD = "MM-dd";
    public static String FORMAT_HS = "HH:ss";
    public static String FORMAT_DD = "dd";
    public static String FORMAT_HM = "HH:mm";
    public static String FORMAT_MM = "yyyy-MM";
    public static String FORMAT_YMD_HM = "yyyy.MM.dd HH:mm";
    public static String FORMAT_YMD_EE_HM = "yyyy年MM月dd日,EEE HH:mm";

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static DateUtil instance = new DateUtil();
    }

    /**
     * 私有的构造函数
     */
    private DateUtil() {

    }

    public static DateUtil getInstance() {
        return DateUtil.SingletonHolder.instance;
    }

    public long minToMillisecond(int min) {
        return min * 60000;
    }

    /**
     * 获取当前时间作为文件名
     *
     * @return
     */
    public String dataToFileName() {
        return new DateTime().toString();
    }

    public String date2Str(Calendar c) {
        return new DateTime(c).toString(FORMAT);
    }

    public String date2Str(Date d, String format) {
        if (d == null) {
            return null;
        }
        return new DateTime(d).toString(format);
    }

    public String long2Str(long d, String format) {
        if (d == 0) {
            return "";
        }
        return new DateTime(d).toString(format, Locale.CHINESE);
    }

    /**
     * 获得当前日期的字符串格式
     *
     * @param format
     * @return
     */
    public String getCurDateStr(String format) {
        DateTime dateTime = new DateTime();
        return dateTime.toString(format);
    }

    public String getCurDateStr() {
        return getCurDateStr(FORMAT);
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public long getCurTime() {
        DateTime dateTime = new DateTime();
        return dateTime.getMillis();
    }

    /**
     * 获取今天时间戳  已一天结束时间结尾 23:59:59
     *
     * @return
     */
    public long getCurTimeWithEndOfDay() {
        DateTime dateTime = new DateTime();
        return dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).getMillis();
    }

    /**
     * 已一天结束时间结尾 23:59:59
     *
     * @return
     */
    public long getTimeWithEndOfDay(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).getMillis();
    }

    /**
     * 当天的开始时间  也就是0:0:0
     *
     * @return
     */
    public long getTimeWithStartOfDay(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.withTimeAtStartOfDay().getMillis();
    }

    public long getTimeWithStartOfDay(DateTime date) {
        return date.withTimeAtStartOfDay().getMillis();
    }

    public long getTimeWithStartOfDay() {
        DateTime dateTime = new DateTime();
        return dateTime.withTimeAtStartOfDay().getMillis();
    }

    /**
     * 当月最后一天 并已23:59:59结束
     *
     * @return
     */
    public long getTimeWithEndDayOfMonth() {
        DateTime dateTime = new DateTime();
        return dateTime.dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).getMillis();
    }

    public long getTimeWithEndDayOfMonth(DateTime dateTime) {
        return dateTime.dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).getMillis();
    }

    // 格式到秒
    public String getMillon(long time) {
        return new DateTime(time).toString(FORMAT);
    }

    // 格式到天
    public String getDay(long time) {
        return new DateTime(time).toString(FORMAT_YMD);
    }

    // 格式到月日
    public String getMonthDay(long time) {
        return new DateTime(time).toString(FORMAT_MD);
    }

    public String getHourAndMin(long time) {
        return new DateTime(time).toString(FORMAT_HS);
    }

    // string类型转换为long类型
    public long stringToLong(String stime) {
        return stringToLong(stime, FORMAT);
    }

    // string类型转换为long类型
    public DateTime stringToDateTime(String stime, String format) {
        //String转换为Joda-time
        DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
        DateTime dateTime = dtf.parseDateTime(stime);
        return dateTime;
    }

    // string类型转换为long类型
    public long stringToLong(String stime, String formatType) {
        if (TextUtils.isEmpty(stime)) {
            return 0;
        }
        //String转换为Joda-time
        DateTimeFormatter dtf = DateTimeFormat.forPattern(formatType);
        DateTime dateTime = dtf.parseDateTime(stime);
        if (dateTime != null) {
            return dateTime.withSecondOfMinute(0).getMillis();
        }
        return 0;
    }

    // string类型转换为long类型
    public long stringToLongEnd(String stime) {
        if (TextUtils.isEmpty(stime)) {
            return 0;
        }
        //String转换为Joda-time
        stime = stime + " 23:59:59";
        DateTimeFormatter dtf = DateTimeFormat.forPattern(FORMAT);
        DateTime dateTime = dtf.parseDateTime(stime);
        if (dateTime != null) {
            return dateTime.getMillis();
        }
        return 0;
    }

    public int getWeek(DateTime dateTime) {
        int w = dateTime.getDayOfWeek();
        return w;
    }

    public String getWeekFromDate(String date, String format) {
        DateTime dateTime = stringToDateTime(date, format);
        return dateTime.toString("EEE", Locale.CHINESE);
    }


    public long mssToDay(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        return days;
    }

    public long mssToMin(long mss) {
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        return minutes;
    }


    /**
     * 一周前的今天时间戳 当天的开始时间  也就是0:0:0
     *
     * @return
     */
    public long beforeWeekWithStartOfDay() {
        DateTime dateTime = new DateTime();
        DateTime beforeWeek = dateTime.minusDays(6);
        return beforeWeek.withTimeAtStartOfDay().getMillis();
    }

    /**
     * 一周前的今天时间 当天的开始时间  也就是0:0:0
     *
     * @return
     */
    public String beforeWeekWithStartOfDay(String format) {
        DateTime dateTime = new DateTime();
        DateTime beforeWeek = dateTime.minusDays(6);
        return beforeWeek.withTimeAtStartOfDay().toString(format);
    }


    /**
     * 从新构造时间格式
     *
     * @return
     */
    public String formatTime(String time, String format) {
        DateTime dateTime = new DateTime(time);
        return dateTime.toString(format);
    }

    public String longToDate(long lo, String f) {
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat(f);
        return sd.format(date);

    }

    /**
     * 获取当前时间前几天
     *
     * @param index
     * @return
     */
    public DateTime beforeDay(int index) {
        DateTime dateTime = new DateTime();
        return dateTime.minusDays(index);
    }

    /**
     * 获取当前时间后几天
     *
     * @param index
     * @return
     */
    public DateTime afterDay(int index) {
        DateTime dateTime = new DateTime();
        return dateTime.plusDays(index);
    }

    public DateTime afterDay(Date date, int index) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(index);
    }

    /**
     * 获取当前时间后几天
     *
     * @param index
     * @return
     */
    public DateTime afterDay(DateTime dateTime, int index) {
        return dateTime.plusDays(index);
    }

    public String getWeekFromDate(DateTime date) {
        return date.toString("EEE", Locale.CHINESE);
    }

    /**
     * 转换成有类型的时间
     *
     * @param index
     * @param format
     * @return
     */
    public String beforeSDay(int index, String format) {
        return beforeDay(index).toString(format);
    }

    /**
     * 转换成有类型的时间
     *
     * @param index
     * @param format
     * @return
     */
    public String afterSDay(int index, String format) {
        return afterDay(index).toString(format);
    }

    /**
     * 转换成有类型的时间
     *
     * @param index
     * @param format
     * @return
     */
    public String afterSDay(DateTime dateTime, int index, String format) {
        return afterDay(dateTime, index).toString(format);
    }

    /**
     * 获取当前
     *
     * @return
     */
    public DateTime curDateTime() {
        return new DateTime();
    }

    /**
     * 判断两个时间是否相等
     *
     * @param t1
     * @param t2
     * @return
     */
    public boolean equalsTime(String t1, String t2) {
        DateTime d1 = new DateTime(t1);
        DateTime d2 = new DateTime(t2);
        return d1.isEqual(d2);
    }

    /**
     * 是否同一天
     *
     * @param t1
     * @param t2
     * @return
     */
    public boolean equalsDay(long t1, long t2) {
        DateTime d1 = new DateTime(t1);
        DateTime d2 = new DateTime(t2);
        return d1.toString(FORMAT_YMD).equals(d2.toString(FORMAT_YMD));
    }

    public boolean isToday(DateTime d2) {
        DateTime d1 = new DateTime();
        return d1.toString(FORMAT_YMD).equals(d2.toString(FORMAT_YMD));
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public int differentDaysByMillisecond(Date date1, Date date2) {
        DateTime dt1 = new DateTime(date1);
        DateTime dt2 = new DateTime(date2);
        return Days.daysBetween(dt1, dt2).plus(1).getDays();
    }

    /**
     * 判断是否是今天以后时间
     *
     * @return
     */
    public boolean isTodayAfter(long time) {
        DateTime dt1 = new DateTime();
        DateTime dt2 = new DateTime(time);
        return dt2.isAfter(dt1);
    }

    /**
     * 判断是否是今天以后时间
     *
     * @return
     */
    public boolean isTodayAfter(String time) {
        DateTime dt1 = new DateTime();
        DateTime dt2 = new DateTime(time);
        if (Days.daysBetween(dt1, dt2).getDays() >= 0)
            return true;
        return false;
    }

    /**
     * 获取当月剩余天数
     *
     * @return
     */
    public int getDaysOfMonth(DateTime dateTime) {
        return dateTime.dayOfMonth().getMaximumValue() - (dateTime.getDayOfMonth() - 1);
    }

    /**
     * 获取当月剩余天数
     *
     * @return
     */
    public int getDaysOfMonth(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfMonth().getMaximumValueOverall() - dateTime.getDayOfMonth();
    }

    // 根据时间戳计算年龄
    public int getAgeFromBirthTime(long birthTimeLong) {
        if (birthTimeLong == 0) {
            return 0;
        }
        DateTime date = new DateTime(birthTimeLong);
        DateTime newDate = curDateTime();
        Years age = Years.yearsBetween(date, newDate);
        return age.getYears();
    }

    /**
     * 结束时间是否大于开始时间
     *
     * @return
     */
    public boolean endGreaterStart(DateTime startDate, DateTime endDate) {
        return startDate.getMillis() < endDate.getMillis();
    }
}
