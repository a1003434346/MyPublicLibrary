package com.example.mypubliclibrary.widget.bean;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public abstract class ViewAttribute {
    public StateListDrawable itemBackground;
    public int itemTextColor;
    public int lineColor;
    public int lineHeight;
    public int itemParseColor;
    public boolean cancel;
    //是否显示取消按钮
    public boolean isShowCancelButton;
    //是否显示分割线
    public boolean isShowLine;

    public ViewAttribute() {
        initAttribute();
    }

    //初始化一些默认属性
    protected abstract void initAttribute();

}
