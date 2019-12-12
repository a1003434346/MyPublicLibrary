package com.example.mypubliclibrary.widget.dialog.build;

import android.content.Context;
import android.graphics.Color;

import androidx.core.graphics.ColorUtils;

import com.example.mypubliclibrary.widget.bean.ViewAttribute;
import com.example.mypubliclibrary.widget.dialog.basic.SelectViewDialog;

import java.util.List;

public abstract class BuildSelectTextAttribute<T> extends ViewAttribute {
    private Context mContext;
    private int currentItemOne;
    private int currentItemTwo;
    private int currentItemThree;
    //文本颜色
    private int textColor;
    //title颜色
    private int titleColor;
    //完成颜色
    private int doneColor;
    //间距
    private int padding;
    //滚动栏1选择的内容
    public T mSelectValueOne;

    //滚动栏2选择的内容
    public T mSelectValueTwo;

    //滚动栏3选择的内容
    public T mSelectValueThree;

    private int mSelectIndexOne;

    private int mSelectIndexTwo;

    private int mSelectIndexThree;

    //滚动栏1的数据
    private List<T> mListOne;

    /**
     * 滚动栏2的数据
     */
    private List<T> mListTwo;

    /**
     * 滚动栏3的数据
     */
    private List<T> mListThree;

    protected BuildSelectTextAttribute(Context context) {
        mContext = context;
    }


    @Override
    protected void initAttribute() {
        isWindowShadow = true;
        isCancel = true;
        currentItemOne = -1;
        currentItemTwo = -1;
        currentItemThree = -1;
        padding = 16;
        titleColor = Color.parseColor("#52CAC1");
        doneColor = Color.parseColor("#52CAC1");
        textColor = Color.parseColor("#000000");
    }

    public void setSelectIndexOne(int mSelectIndexOne) {
        this.mSelectIndexOne = mSelectIndexOne;
    }

    public void setSelectIndexTwo(int mSelectIndexTwo) {
        this.mSelectIndexTwo = mSelectIndexTwo;
    }

    public void setSelectIndexThree(int mSelectIndexThree) {
        this.mSelectIndexThree = mSelectIndexThree;
    }

    public int getSelectIndexOne() {
        return mSelectIndexOne;
    }

    public int getSelectIndexTwo() {
        return mSelectIndexTwo;
    }

    public int getSelectIndexThree() {
        return mSelectIndexThree;
    }

    public List<T> getListOne() {
        return mListOne;
    }

    public List<T> getListTwo() {
        return mListTwo;
    }

    public List<T> getListThree() {
        return mListThree;
    }

    public BuildSelectTextAttribute<T> setListOne(List<T> listOne) {
        mListOne = listOne;
        return this;
    }

    public BuildSelectTextAttribute<T> setListTwo(List<T> listTwo) {
        mListTwo = listTwo;
        return this;
    }

    public BuildSelectTextAttribute<T> setListThree(List<T> listThree) {
        mListThree = listThree;
        return this;
    }

    public int getCurrentItemOne() {
        return currentItemOne;
    }

    public int getCurrentItemTwo() {
        return currentItemTwo;
    }

    public int getCurrentItemThree() {
        return currentItemThree;
    }

    /**
     * 设置滚轮一当前选择的位置
     *
     * @param index index
     * @return
     */
    public BuildSelectTextAttribute<T> setCurrentItemOne(int index) {
        currentItemOne = index;
        return this;
    }

    /**
     * 设置滚轮一当前选择的位置
     *
     * @param t t
     * @return
     */
    public BuildSelectTextAttribute<T> setCurrentItemOne(T t) {
        for (int i = 0; i < mListOne.size(); i++) {
            if (mListOne.get(i).toString().equals(t.toString())) {
                currentItemOne = i;
                break;
            }
        }
        return this;
    }

    /**
     * 设置滚轮二当前选择的位置
     *
     * @param t t
     * @return
     */
    public BuildSelectTextAttribute<T> setCurrentItemTwo(T t) {
        for (int i = 0; i < mListTwo.size(); i++) {
            if (mListTwo.get(i).toString().equals(t.toString())) {
                currentItemTwo = i;
                break;
            }
        }
        return this;
    }


    /**
     * 设置滚轮三当前选择的位置
     *
     * @param t t
     * @return
     */
    public BuildSelectTextAttribute<T> setCurrentItemThree(T t) {
        for (int i = 0; i < mListThree.size(); i++) {
            if (mListThree.get(i).toString().equals(t.toString())) {
                currentItemThree = i;
                break;
            }
        }
        return this;
    }


    /**
     * 设置滚轮二当前选择的位置
     *
     * @param index index
     * @return
     */
    public BuildSelectTextAttribute<T> setCurrentItemTwo(int index) {
        currentItemTwo = index;
        return this;
    }

    /**
     * 设置滚轮三当前选择的位置
     *
     * @param index index
     * @return
     */
    public BuildSelectTextAttribute<T> setCurrentItemThree(int index) {
        currentItemThree = index;
        return this;
    }

    public int getTextColor() {
        return textColor;
    }

    public BuildSelectTextAttribute<T> setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }


    public int getTitleColor() {
        return titleColor;
    }

    /**
     * 设置Title颜色
     */
    public BuildSelectTextAttribute<T> setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public int getDoneColor() {
        return doneColor;
    }

    /**
     * 设置完成颜色的Color
     *
     * @param doneColor color
     * @return
     */
    public BuildSelectTextAttribute<T> setDoneColor(int doneColor) {
        this.doneColor = doneColor;
        return this;
    }

    public int getPadding() {
        return padding;
    }

    /**
     * 设置间距，不设置默认为16
     *
     * @param padding 间距
     * @return
     */
    public BuildSelectTextAttribute<T> setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    /**
     * 点击窗口区域外是否可以关闭窗口，不设置默认为true
     *
     * @param cancel true/false
     */
    public void isCancel(boolean cancel) {
        this.isCancel = cancel;
    }

    /**
     * 窗口区域外是否显示阴影，不设置默认为true
     *
     * @param windowShadow true/false
     */
    public void isWindowShadow(boolean windowShadow) {
        this.isWindowShadow = windowShadow;
    }


    protected abstract void onSelectAchieve();


    public SelectViewDialog createWindow() {
        return new SelectViewDialog<T>(mContext) {
            @Override
            protected void selectAchieve() {
                onSelectAchieve();
            }
        }.setViewAttribute(this);
    }
}
