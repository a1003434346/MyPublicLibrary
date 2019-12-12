package com.example.mypubliclibrary.widget.dialog.build;

import android.content.Context;
import android.widget.TextView;

import com.example.mypubliclibrary.widget.bean.ViewAttribute;
import com.example.mypubliclibrary.widget.dialog.basic.InputDialogTest;

public abstract class BuildInputAttribute extends ViewAttribute {
    private Context mContext;
    //是否限制为单行
    private boolean isSingleLine;
    //连接同步输入的view
    private TextView connectTextView;
    //提交按钮的文字
    private String submitText;
    //最大输入长度
    private int maxLength;
    //输入类型
    private int inputType;
    //初始化的内容
    private String inputValue;
    //提示文本
    private String textHint;
    //设置允许输入哪些值
    private String digits;
    //设置提交按钮是否显示，不设置默认为显示
    private boolean submitVisibility;

    public BuildInputAttribute(Context context) {
        mContext = context;
    }

    @Override
    protected void initAttribute() {
        isWindowShadow = false;
        isCancel = true;
        isSingleLine = true;
        submitVisibility = true;
    }


    public boolean submitVisibility() {
        return submitVisibility;
    }

    /**
     * 设置提交按钮是否显示，不设置默认为显示
     *
     * @param submitVisibility submitVisibility
     * @return
     */
    public BuildInputAttribute submitVisibility(boolean submitVisibility) {
        this.submitVisibility = submitVisibility;
        return this;
    }

    public String digits() {
        return digits;
    }

    /**
     * 设置允许输入哪些值
     *
     * @param digits
     */
    public BuildInputAttribute digits(String digits) {
        this.digits = digits;
        return this;
    }

    public String textHint() {
        return textHint;
    }

    /**
     * 提示文本
     *
     * @param textHint textHint
     * @return
     */
    public BuildInputAttribute textHint(String textHint) {
        this.textHint = textHint;
        return this;
    }

    public String inputValue() {
        return inputValue;
    }

    /**
     * 初始化的内容
     *
     * @param inputValue inputValue
     * @return
     */
    public BuildInputAttribute inputValue(String inputValue) {
        this.inputValue = inputValue;
        return this;
    }

    public int maxLength() {
        return maxLength;
    }

    public int inputType() {
        return inputType;
    }

    /**
     * 输入类型
     *
     * @param inputType inputType
     * @return
     */
    public BuildInputAttribute inputType(int inputType) {
        this.inputType = inputType;
        return this;
    }

    /**
     * 最大输入长度
     *
     * @param maxLength length
     * @return
     */
    public BuildInputAttribute maxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    public boolean isSingleLine() {
        return isSingleLine;
    }

    public TextView connectTextView() {
        return connectTextView;
    }

    public String submitText() {
        return submitText;
    }

    /**
     * 提交按钮的文字
     *
     * @param submitText value
     * @return
     */
    public BuildInputAttribute submitText(String submitText) {
        this.submitText = submitText;
        return this;
    }

    /**
     * 连接同步输入的view
     *
     * @param connectTextView TextView
     */
    public BuildInputAttribute connectTextView(TextView connectTextView) {
        this.connectTextView = connectTextView;
        return this;
    }

    /**
     * 是否允许单行输入，true为单行(默认为单行) false为多行
     *
     * @param singleLine
     * @return
     */
    public BuildInputAttribute isSingleLine(boolean singleLine) {
        isSingleLine = singleLine;
        return this;
    }

    /**
     * 窗口区域外是否显示阴影,不设置默认为false
     *
     * @param windowShadow true/false
     * @return
     */
    public BuildInputAttribute isWindowShadow(boolean windowShadow) {
        isWindowShadow = windowShadow;
        return this;
    }

    /**
     * 窗口区域外是否显示阴影,不设置默认为true
     *
     * @param cancel true/false
     * @return
     */
    public BuildInputAttribute isCancel(boolean cancel) {
        isCancel = cancel;
        return this;
    }

    protected abstract void onSubmitValue(String value);

    public InputDialogTest createWindow() {
        return new InputDialogTest(mContext) {
            protected void submitValue(String value) {
                onSubmitValue(value);
            }
        }.setViewAttribute(this);
    }
}
