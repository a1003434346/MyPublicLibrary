package com.example.mypubliclibrary.widget.dialog.build;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

import com.example.mypubliclibrary.widget.bean.ViewAttribute;
import com.example.mypubliclibrary.widget.dialog.basic.BottomIosDialog;

import java.util.List;

public abstract class BuildIosDialog extends ViewAttribute {
    private Context mContext;
    private List<String> mItems;

    @Override
    protected void initAttribute() {
        itemTextColor = Color.parseColor("#52CAC1");
        lineColor = Color.parseColor("#f5f5f5");
        lineHeight = 2;
        itemParseColor = Color.parseColor("#f5f5f5");
        isCancel = true;
        itemBackgroundColor = Color.parseColor("#FFFFFF");
        isShowCancelButton = true;
        isShowLine = true;
        isWindowShadow = true;
    }

    public BuildIosDialog(Context context) {
        this.mContext = context;
    }

    /**
     * 设置选项的背景颜色，不设置默认为白色
     *
     * @param color 背景颜色
     * @return
     */
    public BuildIosDialog itemBackgroundColor(int color) {
        itemBackgroundColor = color;
        return this;
    }

    /**
     * 设置选项的列表
     *
     * @param strings 列表
     * @return
     */
    public BuildIosDialog items(List<String> strings) {
        mItems = strings;
        return this;
    }

    /**
     * 设置选项的文本颜色
     *
     * @param color 文本颜色
     * @return
     */
    public BuildIosDialog itemTextColor(int color) {
        itemTextColor = color;
        return this;
    }

    /**
     * 设置分割线的颜色
     *
     * @param color 分割线颜色
     * @return
     */
    public BuildIosDialog lineColor(int color) {
        lineColor = color;
        return this;
    }

    /**
     * 设置分割线的高度
     *
     * @param height 分割线高度
     * @return
     */
    public BuildIosDialog lineHeight(int height) {
        lineHeight = height;
        return this;
    }

    /**
     * 选项按下的背景颜色
     *
     * @param color 按下的背景颜色
     * @return
     */
    public BuildIosDialog itemParseColor(int color) {
        itemParseColor = color;
        return this;
    }

    /**
     * 是否可以点击空白区域关闭窗口，不设置默认为true
     *
     * @param cancel true/false
     * @return
     */
    public BuildIosDialog isCancel(boolean cancel) {
        this.isCancel = cancel;
        return this;
    }

    /**
     * 是否显示取消按钮，不设置默认为true
     *
     * @param isCancel true/false
     * @return
     */
    public BuildIosDialog isShowCancelButton(boolean isCancel) {
        isShowCancelButton = isCancel;
        return this;
    }

    /**
     * 是否显示分割线，不设置默认为true
     *
     * @param showLine true/false
     * @return
     */
    public BuildIosDialog isShowLine(boolean showLine) {
        isShowLine = showLine;
        return this;
    }

    /**
     * 窗口区域外是否显示阴影，不设置默认为true
     *
     * @param windowShadow true/false
     * @return
     */
    public BuildIosDialog isWindowShadow(boolean windowShadow) {
        isWindowShadow = windowShadow;
        return this;
    }

    protected abstract void itemClick(Button button, int position);

    public BottomIosDialog create() {
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
