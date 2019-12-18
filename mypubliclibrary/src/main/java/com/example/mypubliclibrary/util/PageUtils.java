package com.example.mypubliclibrary.util;

import android.view.View;

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
}
