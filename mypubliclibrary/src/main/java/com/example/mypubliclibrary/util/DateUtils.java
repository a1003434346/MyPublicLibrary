package com.example.mypubliclibrary.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * function:
 * describe: 日期工具类
 * Created By LiQiang on 2019/7/18.
 */
public class DateUtils {
    /**
     * 获取当前日期
     *
     * @param pattern 日期格式化的样式,例如yyyy年MM月dd日 HH时mm分ss秒
     * @return 日期
     */
    public static String getCurrentDate(String... pattern) {
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        return new SimpleDateFormat(pattern.length > 0 ? pattern[0] : "yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
    }

    /**
     * 把时间转换成字符串
     *
     * @param time 时间
     * @return 字符串时间
     */
    public static String timeFormatString(int time) {
        return time < 10 ? "0" + time : time + "";
    }

    /**
     * 根据小时获取生活单位
     *
     * @return 生活单位
     */
    public static String getLiveUnit(int hours) {
        String liveUnit;
        if (hours >= 6 && hours < 12) {
            liveUnit = "上午";
        } else if (hours >= 12 && hours < 15) {
            liveUnit = "中午";
        } else if (hours >= 15 && hours < 19) {
            liveUnit = "下午";
        } else if (hours >= 19 && hours <= 23) {
            liveUnit = "晚上";
        } else if (hours < 6) {
            liveUnit = "夜晚";
        } else {
            liveUnit = "";
        }
        return liveUnit;
    }

    /**
     * 计算两个日期相差多少天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static long differ(Date date1, Date date2) {
        return date2.getTime() / 86400000 - date1.getTime() / 86400000;  //用立即数，减少乘法计算的开销
    }

    /**
     * 获取间隔时间秒
     *
     * @param current 当前的时间，用System.currentTimeMillis()获取
     * @param start   开始的时间，用System.currentTimeMillis()获取
     * @return 秒
     */
    public static int getIntervalSeconds(long current, long start) {
        return (int) ((current - start) / 1000);
    }

    /**
     * 获取日期
     *
     * @return Date
     */
    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 根据传进去的日期转换成Date
     *
     * @param dayOfNumber 日期
     * @return Date
     */
    public static Date getDate(int... dayOfNumber) {
        Calendar calendar = new GregorianCalendar();
        if (dayOfNumber.length == 3) {
            calendar.set(Calendar.YEAR, dayOfNumber[0]);
            calendar.set(Calendar.MONTH, dayOfNumber[1]);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfNumber[2]);
        } else if (dayOfNumber.length == 6) {
            calendar.set(Calendar.YEAR, dayOfNumber[0]);
            calendar.set(Calendar.MONTH, dayOfNumber[1]);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfNumber[2]);
            calendar.set(Calendar.HOUR_OF_DAY, dayOfNumber[3]);
            calendar.set(Calendar.MINUTE, dayOfNumber[4]);
            calendar.set(Calendar.SECOND, dayOfNumber[5]);
        }
        return calendar.getTime();
    }

    /**
     * 获取年
     *
     * @return 年
     */
    public static int getDateYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return 月
     */
    public static int getDateMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return 日
     */
    public static int getDatDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 获取小时
     *
     * @return 小时
     */
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟
     *
     * @return 分钟
     */
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     *
     * @return 秒
     */
    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    /**
     * 获取毫秒
     *
     * @return 秒
     */
    public static Long getMs() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTimeInMillis();
    }

}
