package com.example.mypubliclibrary.widget.dialog;

import android.content.Context;
import android.graphics.Color;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.util.ShapeUtils;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.bean.ViewAttribute;

public class BottomDialog {
    private Context mContext;

    private ViewAttribute viewAttribute;


    public BottomDialog(Context context) {
        this.mContext = context;
        viewAttribute = new ViewAttribute() {
            @Override
            protected void initAttribute() {
                itemTextColor = Color.parseColor("#52CAC1");
                lineColor = Color.parseColor("#f5f5f5");
                lineHeight = 2;
                itemParseColor = Color.parseColor("#f5f5f5");
                cancel = true;
                itemBackground = ShapeUtils.getRadiusRectangle(0, 0, WindowUtils.dip2px(mContext, 12), mContext.getResources().getColor(R.color.colorWhite));
                isShowCancelButton = true;
            }
        };
    }

    /**
     * 设置选项的背景颜色，不设置默认为白色
     *
     * @param color 背景颜色
     * @return
     */
    public BottomDialog itemBackground(int color) {
        viewAttribute.itemBackground = ShapeUtils.getRadiusRectangle(0, 0, WindowUtils.dip2px(mContext, 12), color);
        return this;
    }

    /**
     * 设置选项的文本颜色
     *
     * @param color 文本颜色
     * @return
     */
    public BottomDialog itemTextColor(int color) {
        viewAttribute.itemTextColor = color;
        return this;
    }

    /**
     * 设置分割线的颜色
     *
     * @param color 分割线颜色
     * @return
     */
    public BottomDialog lineColor(int color) {
        viewAttribute.lineColor = color;
        return this;
    }

    /**
     * 设置分割线的高度
     *
     * @param height 分割线高度
     * @return
     */
    public BottomDialog lineHeight(int height) {
        viewAttribute.lineHeight = height;
        return this;
    }

    /**
     * 选项按下的背景颜色
     *
     * @param color 按下的背景颜色
     * @return
     */
    public BottomDialog itemParseColor(int color) {
        viewAttribute.itemParseColor = color;
        return this;
    }

    /**
     * 是否可以点击空白区域关闭窗口，不设置默认为true
     *
     * @param cancel true/false
     * @return
     */
    public BottomDialog isCancel(boolean cancel) {
        viewAttribute.cancel = cancel;
        return this;
    }

    /**
     * 是否显示取消按钮，不设置默认为true
     *
     * @param isCancel true/false
     * @return
     */
    public BottomDialog isShowCancelButton(boolean isCancel) {
        viewAttribute.isShowCancelButton = isCancel;
        return this;
    }

    public BottomIosDialog asBind(BottomIosDialog bottomIosDialog) {
        bottomIosDialog.mViewAttribute = this.viewAttribute;
        return bottomIosDialog;
    }
}
