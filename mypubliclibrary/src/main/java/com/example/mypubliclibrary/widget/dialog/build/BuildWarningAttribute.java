package com.example.mypubliclibrary.widget.dialog.build;

import android.content.Context;
import android.graphics.Color;

import com.example.mypubliclibrary.widget.bean.ViewAttribute;
import com.example.mypubliclibrary.widget.dialog.basic.PasswordPayDialog;
import com.example.mypubliclibrary.widget.dialog.basic.WarningDialog;

/**
 * 功能:
 * Created By leeyushi on 2019/12/23.
 */
public class BuildWarningAttribute extends ViewAttribute {
    //中间文本内容，设置了以后只显示一个Button在中间
    private String middleOneValue;
    //Button2的文本内容
    private String buttonText2;
    private String title;
    private String buttonText1;
    private int btn2TextColor;
    private int btn1TextColor;
    private int middleTextColor;
    private String showValue;
    private int showValueColor;
    private int titleColor;
    private Context context;

    @Override
    protected void initAttribute() {
        isCancel = true;
        isWindowShadow = true;
        buttonText2 = "确定";
        buttonText1 = "返回";
        btn2TextColor = Color.parseColor("#000000");
        btn1TextColor = Color.parseColor("#000000");
        middleTextColor = Color.parseColor("#000000");
        titleColor = Color.parseColor("#000000");
        showValueColor = Color.parseColor("#9D9D9D");
    }

    public BuildWarningAttribute(Context context) {
        this.context = context;
    }

    public String getMiddleOneValue() {
        return middleOneValue;
    }

    public BuildWarningAttribute setMiddleOneValue(String middleOne) {
        this.middleOneValue = middleOne;
        return this;
    }

    public String getButtonText2() {
        return buttonText2;
    }

    public BuildWarningAttribute setButtonText2(String buttonText2) {
        this.buttonText2 = buttonText2;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BuildWarningAttribute setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getButtonText1() {
        return buttonText1;
    }

    public BuildWarningAttribute setButtonText1(String buttonText1) {
        this.buttonText1 = buttonText1;
        return this;
    }

    public int getBtn2TextColor() {
        return btn2TextColor;
    }

    public BuildWarningAttribute setBtn2TextColor(int btn2TextColor) {
        this.btn2TextColor = btn2TextColor;
        return this;
    }

    public int getBtn1TextColor() {
        return btn1TextColor;
    }

    public BuildWarningAttribute setBtn1TextColor(int btn1TextColor) {
        this.btn1TextColor = btn1TextColor;
        return this;
    }

    public int getMiddleTextColor() {
        return middleTextColor;
    }

    public BuildWarningAttribute setMiddleTextColor(int middleTextColor) {
        this.middleTextColor = middleTextColor;
        return this;
    }

    public String getShowValue() {
        return showValue;
    }

    public BuildWarningAttribute setShowValue(String showValue) {
        this.showValue = showValue;
        return this;
    }

    public int getShowValueColor() {
        return showValueColor;
    }

    public BuildWarningAttribute setShowValueColor(int showValueColor) {
        this.showValueColor = showValueColor;
        return this;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public BuildWarningAttribute setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public WarningDialog createWindow() {
        return new WarningDialog(context).setViewAttribute(this);
    }
}
