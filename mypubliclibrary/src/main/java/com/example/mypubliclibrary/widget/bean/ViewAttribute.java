package com.example.mypubliclibrary.widget.bean;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 视图属性
 */
public abstract class ViewAttribute {
    public int itemBackgroundColor;
    public int itemTextColor;
    public int lineColor;
    public int lineHeight;
    public int itemParseColor;
    //点击窗口区域外是否可以关闭窗口
    public boolean isCancel;
    //是否显示取消按钮
    public boolean isShowCancelButton;
    //是否显示分割线
    public boolean isShowLine;
    //窗口区域外是否显示阴影
    public boolean isWindowShadow;

    protected ViewAttribute() {
        initAttribute();
    }

    //初始化一些默认属性
    protected abstract void initAttribute();


//    protected int getItemBackgroundColor() {
//        return itemBackgroundColor;
//    }
//
//    protected int getItemTextColor() {
//        return itemTextColor;
//    }
//
//    protected int getLineColor() {
//        return lineColor;
//    }
//
//    protected int getLineHeight() {
//        return lineHeight;
//    }
//
//    protected int getItemParseColor() {
//        return itemParseColor;
//    }
//
//    protected boolean isCancel() {
//        return isCancel;
//    }
//
//    protected boolean isShowCancelButton() {
//        return isShowCancelButton;
//    }
//
//    protected boolean isShowLine() {
//        return isShowLine;
//    }
//
//    protected boolean isWindowShadow() {
//        return isWindowShadow;
//    }
}
