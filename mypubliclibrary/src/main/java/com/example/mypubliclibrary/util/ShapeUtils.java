package com.example.mypubliclibrary.util;

import android.graphics.drawable.GradientDrawable;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/8/12.
 */
public class ShapeUtils {

    /**
     * 获取矩形圆角背景
     *
     * @param strokeWidth 描边的宽度
     * @param strokeColor 描边的颜色
     * @param radius      圆角
     * @param solidColor  填充的颜色
     * @return GradientDrawable
     */
    public static GradientDrawable getRadiusRectangle(int strokeWidth, int strokeColor, int radius, int solidColor) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setCornerRadius(radius);
        if (solidColor != 0) gd.setColor( solidColor);
        return gd;
    }

    /**
     * 获取矩形圆角背景
     *
     * @param strokeWidth 描边的宽度
     * @param strokeColor 描边的颜色
     * @param radius      圆角 设置四个角半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
     *                    例如 new float[]{16, 16, 16, 16, 0, 0, 0, 0}
     * @return GradientDrawable
     */
    public static GradientDrawable getRadiusRectangle(int strokeWidth, int strokeColor, float[] radius, int solidColor) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setCornerRadii(radius);
        if (solidColor != 0) gd.setColor(solidColor);
        return gd;
    }


}
