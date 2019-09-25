package com.example.mypubliclibrary.widget.dialog;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.util.SelectorUtils;
import com.example.mypubliclibrary.util.ShapeUtils;
import com.example.mypubliclibrary.util.WindowUtils;

import java.util.List;

/**
 * function:
 * describe: 仿IOS底部弹框
 * Created By LiQiang on 2019/7/29.
 */
public abstract class BottomIosDialog implements View.OnClickListener {
//    final Context context;


    private View popView;

    protected PopupWindow popWindow;

    private Context context;

    private int itemColor;

    private TextView cancelView;


    //点击窗口外部是否可以关闭
    private boolean cancel;

    private List<String> items;

    private ConstraintLayout ctlContent;

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
        this.context = context;
        initView();
    }


    private void initView() {
        if (popView == null) {
            //默认文本颜色
            itemColor = Color.parseColor("#52CAC1");
            items = getItems();
            popView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_ios, null);
            ctlContent = popView.findViewById(R.id.ctl_content);
            cancelView = popView.findViewById(R.id.tv_cancel);
            addViews();
            popView.findViewById(R.id.ctl_cancel).setBackground(ShapeUtils.getRadiusRectangle(0, 0, getDP(12), getColor(R.color.colorWhite)));
            popView.findViewById(R.id.ctl_content).setBackground(ShapeUtils.getRadiusRectangle(0, 0, getDP(12), getColor(R.color.colorWhite)));
            popView.findViewById(R.id.ctl_dialog).setOnClickListener(view -> {
                if (cancel) popWindow.dismiss();
            });
            popView.findViewById(R.id.tv_cancel).setOnClickListener(view -> popWindow.dismiss());
            popWindow = WindowUtils.getPopupWindow(context, popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }
    }

    private void addViews() {
        SelectorUtils.ShapeSelector shapeSelector = SelectorUtils.newShapeSelector()
                .setDefaultStrokeColor(Color.parseColor("#FFFFFF"))
                .setPressedBgColor(Color.parseColor("#f5f5f5"));
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
        popWindow.dismiss();
    }

    private Button lastButtonView;

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
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, getDP(1));
            //添加上边线
            TextView lineView = new TextView(context);
            lineView.setBackgroundColor(Color.parseColor("#f5f5f5"));
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

    public BottomIosDialog show() {
        WindowUtils.setBackgroundAlpha(context, 0.5f);
        popWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
        return this;
    }

    protected abstract List<String> getItems();

    protected abstract void itemClicks(Button button, int position);
}
