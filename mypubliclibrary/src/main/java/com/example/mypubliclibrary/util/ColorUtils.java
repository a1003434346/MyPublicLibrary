package com.example.mypubliclibrary.util;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.util.Log;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/9/4.
 */
public class ColorUtils {
    //支持颜色过度的类
    private static final ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public int stringToColor(String color) {
        return Color.parseColor(color);
    }

    /**
     * 十进制Color转换成十六进制的ARGB
     *
     * @param color color
     * @author Unknown
     */
    public static String convertToARGB(int color) {
        String alpha = Integer.toHexString(Color.alpha(color));
        String red = Integer.toHexString(Color.red(color));
        String green = Integer.toHexString(Color.green(color));
        String blue = Integer.toHexString(Color.blue(color));

        if (alpha.length() == 1) {
            alpha = "0" + alpha;
        }

        if (red.length() == 1) {
            red = "0" + red;
        }

        if (green.length() == 1) {
            green = "0" + green;
        }

        if (blue.length() == 1) {
            blue = "0" + blue;
        }

        return "#" + alpha + red + green + blue;
    }

    /**
     * 当前颜色是否接近黑色
     *
     * @param rgb 颜色值
     * @return
     */
    public static boolean isLightColor(int rgb) {
        int blue = Color.blue(rgb);
        int green = Color.green(rgb);
        int red = Color.red(rgb);

        double luma = red * 0.299 + green * 0.587 + blue * 0.114; // per ITU-R BT.709

//        if (luma < 50) {
//            // pick a different colour
//            return true;
//        }
//        return false;
        return luma < 50;
    }


    /**
     * 给当前颜色值设置透明度
     *
     * @param percent 透明值
     * @param rgb     rgb
     * @return
     */
    public static  int getTranslucentColor(float percent, int rgb) {
        int blue = Color.blue(rgb);
        int green = Color.green(rgb);
        int red = Color.red(rgb);
        int alpha = Color.alpha(rgb);
        alpha = Math.round(alpha * percent);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 颜色过度
     *
     * @param from  开始颜色值
     * @param to    结束颜色值
     * @param ratio 0.1-1百分比
     * @return
     */
    public static int blendColors(int from, int to, float ratio) {
        return (int) argbEvaluator.evaluate(ratio, from, to);
    }
}
