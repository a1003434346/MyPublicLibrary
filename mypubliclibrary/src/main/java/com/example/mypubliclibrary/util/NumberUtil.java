package com.example.mypubliclibrary.util;

import android.os.Build;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/11.
 */
public class NumberUtil {
    /**
     * 保留小数点
     *  * @param number
     *  * @param dot  保留位数,保留0位从角四舍五入,保留1位从分四舍五入,以此类推
     *  * @return
     *  
     */
    public static double formatDoubleDot(double number, int dot) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(dot, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 保留小数点
     *  * @param number
     *  * @param dot  保留位数,保留0位从角四舍五入,保留1位从分四舍五入,以此类推
     *  * @return
     *  
     */
    public static String formatStringDot(String number, int dot) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(dot, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 如果小数点后为零显示整数否则保留小数
     *
     * @param num num
     * @return stringNum
     */
    public static String doubleTransl(double num) {
        if (num % 1.0 == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    /**
     * 英文数字转换为中文数字
     *
     * @param num num
     * @return 中文数字，比如1转换为一
     */
    public static String digitalToChinese(int num) {
        String result = null;
        switch (num) {
            case 0:
                result = "零";
                break;
            case 1:
                result = "一";
                break;
            case 2:
                result = "二";
                break;
            case 3:
                result = "三";
                break;
            case 4:
                result = "四";
                break;
            case 5:
                result = "五";
                break;
            case 6:
                result = "六";
                break;
            case 7:
                result = "七";
                break;
            case 8:
                result = "八";
                break;
            case 9:
                result = "九";
                break;
        }
        return result;
    }

    /**
     * 如果小数点后为零显示整数否则保留小数
     *
     * @param num num
     * @return stringNum
     */
    public static Double doubleTransD(double num) {
        if (num % 1.0 == 0) {
            return Double.valueOf((long) num);
        }
        return Double.valueOf(num);
    }


    /**
     * 货币格式,千分位格式化
     *
     * @param money object
     * @return string
     */
    public static String format4Money(Object money) {
        if (money == null || !isNumeric(String.valueOf(money))) {
            return "0.00";
        } else {
            DecimalFormat dfMoney = new DecimalFormat("#,##0.00");
            return dfMoney.format(Double.parseDouble(money + ""));
        }
    }

    /**
     * 将科学计数法转换为正常的数字
     *
     * @param number number
     * @return string
     */
    public static String formatScientificNotation(String number) {
        return new BigDecimal(number).toPlainString();
    }

    /**
     * 判断是否是数字
     *
     * @param s string
     * @return true is number
     */
    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * 获取随机数
     *
     * @param min 最小
     * @param max 最大
     * @return value
     */
    public static int getRandom(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取间隔时间
     *
     * @param start 开始值，推荐用System.nanoTime()获取
     * @param end   结束值，推荐用System.nanoTime()获取
     * @param unit  单位，0,1,2,3,4,5,6 分别代表纳秒、微妙、毫秒、秒、分钟、小时、天
     * @return value
     */
    public static long getIntervalTime(long start, long end, int unit) {
        long period = start - end;
        long value = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            switch (unit) {
                case 0:
                    value = TimeUnit.NANOSECONDS.toNanos(period);
                    break;
                case 1:
                    value = TimeUnit.NANOSECONDS.toMicros(period);
                    break;
                case 2:
                    value = TimeUnit.NANOSECONDS.toMillis(period);
                    break;
                case 3:
                    value = TimeUnit.NANOSECONDS.toSeconds(period);
                    break;
                case 4:
                    value = TimeUnit.NANOSECONDS.toMinutes(period);
                    break;
                case 5:
                    value = TimeUnit.NANOSECONDS.toHours(period);
                    break;
                case 6:
                    value = TimeUnit.NANOSECONDS.toDays(period);
                    break;
            }
        }
        return value;
    }

}
