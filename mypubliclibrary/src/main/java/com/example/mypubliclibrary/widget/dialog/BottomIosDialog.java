package com.example.mypubliclibrary.widget.dialog;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.util.SelectorUtils;
import com.example.mypubliclibrary.util.ShapeUtils;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.bean.BottomIosDialogInfo;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * function:
 * describe: 仿IOS底部弹框
 * Created By LiQiang on 2019/7/29.
 */
public abstract class BottomIosDialog extends BottomPopupView implements View.OnClickListener {
//    final Context context;


//    private View popView;

    //    protected PopupWindow popWindow;
    private XPopup.Builder mPopUp;
    private Context context;
    //文本的颜色
    private int itemColor;

    private TextView cancelView;
    //上边线的颜色
    private int lineColor;

    //上边线的高度
    private int mLineHeight;
    //选项按下的背景颜色
    private int mParseColor;

    //点击窗口外部是否可以关闭,默认为true
    private boolean cancel;

    private List<String> items;

    private ConstraintLayout ctlContent;
    private Button lastButtonView;

//    public BottomIosDialog setItems(List<String> items) {
//        this.items = items;
//        return this;
//    }


    public BottomIosDialog setItemColor(int color) {
        itemColor = color;
        return this;
    }

    public BottomIosDialog setCancel(boolean cancel) {
        this.cancel = cancel;
        return this;
    }

    public BottomIosDialog(Context context) {
        super(context);
        this.context = context;
        cancel = true;
        mPopUp = new XPopup.Builder(getContext()).enableDrag(true);

    }

    /**
     * 设置上边线的颜色，不设置默认为f5f5f5
     *
     * @param color 上边线的颜色
     * @return BottomIosDialog
     */
    public BottomIosDialog setLineColor(int color) {
        lineColor = color;
        return this;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_bottom_ios;
    }

    @Override
    protected void onCreate() {
        initView();
        initData();
        findViewById(R.id.ctl_cancel).setBackground(mDialogAttributes.itemBackground);
        findViewById(R.id.ctl_content).setBackground(mDialogAttributes.itemBackground);

    }

    //完全可见执行
    @Override
    protected void onShow() {

    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }

    private BottomIosDialogInfo mDialogAttributes;

    public static class BuildAttributes {
        private BottomIosDialogInfo iosDialogInfo = new BottomIosDialogInfo();
        private Context context;

        public BuildAttributes(Context context) {
            this.context = context;
        }

        public BuildAttributes itemBackground(int color) {
            iosDialogInfo.itemBackground = ShapeUtils.getRadiusRectangle(0, 0, WindowUtils.dip2px(context, 12), color);
            return this;
        }

        public BuildAttributes itemColor(int color) {
            iosDialogInfo.itemColor = color;
            return this;
        }

        public void asBind(BottomIosDialog bottomIosDialog) {
            bottomIosDialog.mDialogAttributes = this.iosDialogInfo;
        }
    }


    /**
     * 设置选项的背景颜色，不设置默认为白色
     *
     * @param color 背景颜色
     * @return BottomIosDialog
     */
    public BottomIosDialog setItemBackgroundColor(int color) {
//        findViewById(R.id.ctl_cancel).setBackground(ShapeUtils.getRadiusRectangle(0, 0, getDP(12), color));
//        findViewById(R.id.ctl_content).setBackground(ShapeUtils.getRadiusRectangle(0, 0, getDP(12), color));

        return this;
    }


    private void initView() {
//        if (popView == null) {
        //默认文本颜色
        itemColor = Color.parseColor("#52CAC1");
        items = getItems();
//        popView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_ios, null);
        ctlContent = findViewById(R.id.ctl_content);
        cancelView = findViewById(R.id.tv_cancel);
        findViewById(R.id.ctl_cancel).setBackground(ShapeUtils.getRadiusRectangle(0, 0, getDP(12), getColor(R.color.colorWhite)));
        findViewById(R.id.ctl_content).setBackground(ShapeUtils.getRadiusRectangle(0, 0, getDP(12), getColor(R.color.colorWhite)));
//        findViewById(R.id.ctl_dialog).setOnClickListener(view -> {
//            if (cancel) dismiss();
//        });
        findViewById(R.id.tv_cancel).setOnClickListener(view -> dismiss());
//        popWindow = WindowUtils.getPopupWindow(context, popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        }
    }

    private void initData() {
        addViews();
        //设置popupWindow的显示和隐藏动画
//        setAnimationStyle(R.style.BottomIosDialogAnimation);
    }

    /**
     * 设置取消按钮是否显示
     */
    public BottomIosDialog setCancelShow(boolean show) {
        findViewById(R.id.ctl_cancel).setVisibility(show ? View.VISIBLE : View.GONE);
        if (!show)
            setCancel(true);
        return this;
    }


    /**
     * 设置按下的颜色
     *
     * @param color color
     * @return BottomIosDialog
     */
    public BottomIosDialog setParseColor(int color) {
        mParseColor = color;
        return this;
    }

    private void addViews() {
        SelectorUtils.ShapeSelector shapeSelector = SelectorUtils.newShapeSelector()
                .setDefaultStrokeColor(Color.parseColor("#FFFFFF"))
                .setPressedBgColor(mParseColor == 0 ? Color.parseColor("#f5f5f5") : mParseColor);
        cancelView.setBackground(shapeSelector.setCornerRadius(new float[]{getDP(12), getDP(12), getDP(12), getDP(12), getDP(12), getDP(12), getDP(12), getDP(12)}).create());
        for (int i = 0; i < items.size(); i++) {
            if (i == 0) {
                //添加第一个View
                addView(true, shapeSelector.setCornerRadius(new float[]{0, 0, 0, 0, getDP(12), getDP(12), getDP(12), getDP(12)}).create(), items.size() > 1, items.get(i), i);
            } else if (i == items.size() - 1) {
                //添加最后一个View
                addView(false, shapeSelector.setCornerRadius(new float[]{getDP(12), getDP(12), getDP(12), getDP(12), 0, 0, 0, 0}).create(), false, items.get(i), i);
                break;
            } else {
                addView(false, shapeSelector.setCornerRadius(new float[]{0, 0, 0, 0, 0, 0, 0, 0}).create(), true, items.get(i), i);
            }
        }

    }

    @Override
    public void onClick(View view) {
        itemClicks((Button) view, (Integer) view.getTag());
        dismiss();
    }


    public BottomIosDialog setLineHeight(int height) {
        mLineHeight = height;
        return this;
    }

    /**
     * 添加view
     *
     * @param first     第一个
     * @param isAddLine 是否添加上边线
     * @param text      添加内容
     */
    private void addView(boolean first, StateListDrawable stateListDrawable, boolean isAddLine, String text, int index) {
        //自定义的控件
        Button button = (Button) LayoutInflater.from(context).inflate(R.layout.item_ios_bottom, null);
        button.setText(text);
        button.setId(View.generateViewId());
        button.setBackground(stateListDrawable);
        button.setTag(index);
        button.setTextColor(itemColor);
        button.setOnClickListener(this);
        //构造宽高的属性
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, getDP(50));
        if (first) {
            //添加第一个
            layoutParams.bottomToBottom = ctlContent.getId();
        } else {
            layoutParams.bottomToTop = lastButtonView.getId();
        }
        lastButtonView = button;
        if (isAddLine) {
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, mLineHeight == 0 ? 2 : mLineHeight);
            //添加上边线
            TextView lineView = new TextView(context);
            lineView.setBackgroundColor(lineColor == 0 ? Color.parseColor("#f5f5f5") : lineColor);
            params.topToTop = lastButtonView.getId();
            ctlContent.addView(lineView, params);
        }
        ctlContent.addView(button, layoutParams);
    }


    private int getDP(int dp) {
        return WindowUtils.dip2px(context, dp);
    }

    private int getColor(int color) {
        return context.getResources().getColor(color);
    }

    /**
     * 显示窗口
     *
     * @param shadow 窗口区域外是否显示阴影，默认为显示
     * @return BottomIosDialog
     */
    public BottomIosDialog show(boolean... shadow) {
        mPopUp.hasShadowBg(shadow.length == 0 || shadow[0])
                .dismissOnTouchOutside(cancel)
                .asCustom(this)
                .showWindow();
        return this;
    }


    protected abstract List<String> getItems();

    protected abstract void itemClicks(Button button, int position);
}
