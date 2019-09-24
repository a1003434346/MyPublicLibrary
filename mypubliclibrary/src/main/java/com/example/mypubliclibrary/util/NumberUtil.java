package com.example.mypubliclibrary.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

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
     * 判断是否是数字
     *
     * @param s string
     * @return true is number
     */
    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static int getRandom(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }
}
