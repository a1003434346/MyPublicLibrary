package com.example.mypubliclibrary.widget.dialog.build;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

import com.example.mypubliclibrary.util.style.PublicLibraryStyle;
import com.example.mypubliclibrary.widget.bean.ViewAttribute;
import com.example.mypubliclibrary.widget.dialog.basic.BottomIosDialog;

import java.util.List;

public abstract class BuildIosAttribute extends ViewAttribute {
    private Context mContext;
    private List<String> mItems;

    @Override
    protected void initAttribute() {
        itemTextColor = PublicLibraryStyle.colorTheme;
        lineColor = Color.parseColor("#f5f5f5");
        lineHeight = 2;
        itemParseColor = Color.parseColor("#f5f5f5");
        isCancel = true;
        itemBackgroundColor = Color.parseColor("#FFFFFF");
        isShowCancelButton = true;
        isShowLine = true;
        isWindowShadow = true;
    }

    public BuildIosAttribute(Context context) {
        this.mContext = context;
    }

    /**
     * 设置选项的背景颜色，不设置默认为白色
     *
     * @param color 背景颜色
     * @return
     */
    public BuildIosAttribute itemBackgroundColor(int color) {
        itemBackgroundColor = color;
        return this;
    }

    /**
     * 设置选项的列表
     *
     * @param strings 列表
     * @return
     */
    public BuildIosAttribute items(List<String> strings) {
        mItems = strings;
        return this;
    }

    /**
     * 设置选项的文本颜色
     *
     * @param color 文本颜色
     * @return
     */
    public BuildIosAttribute itemTextColor(int color) {
        itemTextColor = color;
        return this;
    }

    /**
     * 设置分割线的颜色
     *
     * @param color 分割线颜色
     * @return
     */
    public BuildIosAttribute lineColor(int color) {
        lineColor = color;
        return this;
    }

    /**
     * 设置分割线的高度
     *
     * @param height 分割线高度
     * @return
     */
    public BuildIosAttribute lineHeight(int height) {
        lineHeight = height;
        return this;
    }

    /**
     * 选项按下的背景颜色
     *
     * @param color 按下的背景颜色
     * @return
     */
    public BuildIosAttribute itemParseColor(int color) {
        itemParseColor = color;
        return this;
    }

    /**
     * 是否可以点击空白区域关闭窗口，不设置默认为true
     *
     * @param cancel true/false
     * @return
     */
    public BuildIosAttribute isCancel(boolean cancel) {
        this.isCancel = cancel;
        return this;
    }

    /**
     * 是否显示取消按钮，不设置默认为true
     *
     * @param isCancel true/false
     * @return
     */
    public BuildIosAttribute isShowCancelButton(boolean isCancel) {
        isShowCancelButton = isCancel;
        return this;
    }

    /**
     * 是否显示分割线，不设置默认为true
     *
     * @param showLine true/false
     * @return
     */
    public BuildIosAttribute isShowLine(boolean showLine) {
        isShowLine = showLine;
        return this;
    }

    /**
     * 窗口区域外是否显示阴影，不设置默认为true
     *
     * @param windowShadow true/false
     * @return
     */
    public BuildIosAttribute isWindowShadow(boolean windowShadow) {
        isWindowShadow = windowShadow;
        return this;
    }

    public boolean isShowCancelButton() {
        return isShowCancelButton;
    }



    protected abstract void itemClick(Button button, int position);

    public BottomIosDialog createWindow() {
        return new BottomIosDialog(mContext) {

            @Override
            protected List<String> getItems() {
                return mItems;
            }

            @Override
            protected void itemClicks(Button button, int position) {
                itemClick(button, position);
            }
        }.setViewAttribute(this);
    }

}
