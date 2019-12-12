package com.example.mypubliclibrary.widget.dialog.build;

import android.content.Context;

import com.example.mypubliclibrary.widget.bean.ViewAttribute;
import com.example.mypubliclibrary.widget.dialog.basic.SelectViewDialog;

import java.util.List;

public abstract class BuildSelectTextAttribute<T> extends ViewAttribute {
    private Context mContext;
    //滚动栏1选择的内容
    public T mSelectValueOne;

    //滚动栏2选择的内容
    public T mSelectValueTwo;

    //滚动栏3选择的内容
    public T mSelectValueThree;

    public int mSelectIndexOne;

    public int mSelectIndexTwo;

    public int mSelectIndexThree;

    //滚动栏1的数据
    public List<T> mDataListOne;

    /**
     * 滚动栏2的数据
     */
    public List<T> mDataListTwo;

    /**
     * 滚动栏3的数据
     */
    public List<T> mDataListThree;

    protected BuildSelectTextAttribute(Context context) {
        mContext = context;
    }

    @Override
    protected void initAttribute() {
        isWindowShadow = true;
        isCancel = true;
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

    protected abstract List<T> onDataListOne();

    protected abstract void onSelectAchieve();


    public List<T> onDataListTwo() {
        return null;
    }

    public List<T> onDataListThree() {
        return null;
    }

    public SelectViewDialog createWindow() {
        return new SelectViewDialog<T>(mContext) {
            @Override
            public List<T> getDataListTwo() {
                return onDataListTwo();
            }

            @Override
            public List<T> getDataListThree() {
                return onDataListThree();
            }

            @Override
            protected List<T> getDataListOne() {
                return onDataListOne();
            }

            @Override
            protected void selectAchieve() {
                onSelectAchieve();
            }
        }.setViewAttribute(this);
    }
}
