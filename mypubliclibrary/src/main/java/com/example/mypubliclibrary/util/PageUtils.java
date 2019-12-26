package com.example.mypubliclibrary.util;

import android.view.View;
import android.widget.TextView;

/**
 * 功能:跟ViewPage有关的工具类
 * Created By leeyushi on 2019/12/18.
 */
public class PageUtils {
    /**
     * 设置ViewPage的标题缩放
     *
     * @param enterView      进入的标题View
     * @param leaveView      离开的标题View
     * @param additionScale  在当前大小的基础上增加尺度
     * @param positionOffset Page的回调
     */
    public static void setPageTitleScale(View enterView, View leaveView, float additionScale, float positionOffset) {
        //设置进入的缩放
        enterView.setScaleX(1.0f + additionScale * positionOffset);
        enterView.setScaleY(1.0f + additionScale * positionOffset);
        //设置离开的缩放
        if (leaveView.getScaleX() > 1.0f) {
            leaveView.setScaleX((1.0f + additionScale) + (-additionScale * positionOffset));
            leaveView.setScaleY((1.0f + additionScale) + (-additionScale * positionOffset));
        }
    }

    /**
     * 设置标题颜色的过渡
     *
     * @param enterView      进入的标题View
     * @param leaveView      离开的标题View
     * @param enterColor     进入标题的颜色
     * @param leaveColor     离开标题的颜色
     * @param positionOffset Page的回调
     */
    public static void setTitleColorTran(View enterView, View leaveView, int enterColor, int leaveColor, float positionOffset) {
        //离开页面标题的颜色
        int leavePageColor = ColorUtils.blendColors(enterColor, leaveColor, positionOffset);
        //进入页面标题的颜色
        int enterPageColor = ColorUtils.blendColors(leaveColor, enterColor, positionOffset);
        ((TextView) enterView).setTextColor(enterPageColor);
        ((TextView) leaveView).setTextColor(leavePageColor);
    }

//    /**
//     * title状态改变
//     *
//     * @param enterColor    进入的颜色
//     * @param leaveColor    离开的颜色
//     * @param additionScale 大于0代表有缩放
//     */
//    public static void titleChange(View enterView, View leaveView, int enterColor, int leaveColor, float additionScale) {
//        ((TextView) enterView).setTextColor(enterColor);
//        ((TextView) leaveView).setTextColor(leaveColor);
//        if (additionScale > 0) {
//            enterView.setScaleX(1.0f + additionScale);
//            enterView.setScaleY(1.0f + additionScale);
//            if (leaveView.getScaleX() > 1.0f && leaveView.getScaleY() > 1.0f) {
//                leaveView.setScaleX(1.0f);
//                leaveView.setScaleY(1.0f);
//            }
//        }
//    }


}
