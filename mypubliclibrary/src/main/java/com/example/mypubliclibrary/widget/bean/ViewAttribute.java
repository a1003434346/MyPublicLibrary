package com.example.mypubliclibrary.widget.bean;

import android.graphics.drawable.GradientDrawable;

public abstract class ViewAttribute {
    public GradientDrawable itemBackground;
    public int itemTextColor;
    public int lineColor;
    public int lineHeight;
    public int itemParseColor;
    public boolean cancel;

    public ViewAttribute() {
        initAttribute();
    }

    //初始化一些默认属性
    protected abstract void initAttribute();

}
