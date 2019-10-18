package com.example.mypubliclibrary.util;

import android.animation.ArgbEvaluator;
import android.graphics.Color;

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
