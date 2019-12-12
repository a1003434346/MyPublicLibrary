package com.example.mypubliclibrary.widget.bean;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 视图属性
 */
public abstract class ViewAttribute {
    protected int itemBackgroundColor;
    protected int itemTextColor;
    protected int lineColor;
    protected int lineHeight;
    protected int itemParseColor;
    //点击窗口区域外是否可以关闭窗口
    protected boolean isCancel;
    //是否显示取消按钮
    protected boolean isShowCancelButton;
    //是否显示分割线
    protected boolean isShowLine;
    //窗口区域外是否显示阴影
    protected boolean isWindowShadow;

    protected ViewAttribute() {
        initAttribute();
    }

    //初始化一些默认属性
    protected abstract void initAttribute();


    public int itemBackgroundColor() {
        return itemBackgroundColor;
    }

    public int itemTextColor() {
        return itemTextColor;
    }

    public int lineColor() {
        return lineColor;
    }

    public int lineHeight() {
        return lineHeight;
    }

    public int itemParseColor() {
        return itemParseColor;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public boolean isShowCancelButton() {
        return isShowCancelButton;
    }

    public boolean isShowLine() {
        return isShowLine;
    }

    public boolean isWindowShadow() {
        return isWindowShadow;
    }
}
