package com.example.mypubliclibrary.widget.dialog.build;

import android.content.Context;

import com.example.mypubliclibrary.widget.bean.ViewAttribute;
import com.example.mypubliclibrary.widget.dialog.SelectViewDialog;

import java.util.List;

public abstract class BuildSelectTextDialog<T> extends ViewAttribute {
    private Context mContext;

    protected BuildSelectTextDialog(Context context) {
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

    public SelectViewDialog create() {
        return new SelectViewDialog<T>(mContext) {

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
