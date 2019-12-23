package com.example.mypubliclibrary.widget.dialog.basic;


import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.util.SelectorUtils;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.dialog.build.BuildIosAttribute;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.List;

/**
 * function:
 * describe: 仿IOS底部弹框
 * Created By LiQiang on 2019/7/29.
 */
public abstract class BottomIosDialog extends BottomPopupView implements View.OnClickListener {

    private XPopup.Builder mPopUp;
    public Context mContext;

    private List<String> items;

    private ConstraintLayout ctlContent;
    private View lastButtonView;
    private TextView cancelView;

    public BottomIosDialog(Context context) {
        super(context);
        this.mContext = context;
        mPopUp = new XPopup.Builder(getContext()).enableDrag(true);

    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_bottom_ios;
    }

    @Override
    protected void onCreate() {
        initView();
        initData();
        findViewById(R.id.ctl_content).setBackground(getBackGround());
        findViewById(R.id.ctl_cancel).setBackground(getBackGround());
        findViewById(R.id.ctl_cancel).setVisibility(mViewAttribute.isShowCancelButton() ? View.VISIBLE : View.GONE);
    }

    private StateListDrawable getBackGround() {
        return SelectorUtils.newShapeSelector().setDefaultBgColor(mViewAttribute.itemBackgroundColor()).setCornerRadius(new float[]{WindowUtils.dip2px(mContext, 12)}).create();
    }


    //完全可见执行
    @Override
    protected void onShow() {

    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }


    private BuildIosAttribute mViewAttribute;


    public BottomIosDialog setViewAttribute(BuildIosAttribute mViewAttribute) {
        this.mViewAttribute = mViewAttribute;
        return this;
    }

    private void initView() {
        items = getItems();
        ctlContent = findViewById(R.id.ctl_content);
        cancelView = findViewById(R.id.tv_cancel);
        findViewById(R.id.tv_cancel).setOnClickListener(view -> dismiss());
    }

    private void initData() {
        addViews();
    }


    private void addViews() {
        SelectorUtils.ShapeSelector shapeSelector = SelectorUtils.newShapeSelector()
                .setDefaultBgColor(mViewAttribute.itemBackgroundColor())
                .setPressedBgColor(mViewAttribute.itemParseColor());
        cancelView.setBackground(shapeSelector.setCornerRadius(new float[]{getDP(12)}).create());
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


    /**
     * 添加view
     *
     * @param first     第一个
     * @param isAddLine 是否添加上边线
     * @param text      添加内容
     */
    private void addView(boolean first, StateListDrawable stateListDrawable, boolean isAddLine, String text, int index) {
        //自定义的控件
        Button button = (Button) LayoutInflater.from(mContext).inflate(R.layout.item_ios_bottom, null);
        button.setText(text);
        button.setId(View.generateViewId());
        button.setBackground(stateListDrawable);
        button.setTag(index);
        button.setTextColor(mViewAttribute.itemTextColor());
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
        if (isAddLine && mViewAttribute.isShowLine()) {
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, mViewAttribute.lineHeight());
            //添加上边线
            TextView lineView = new TextView(mContext);
            lineView.setId(View.generateViewId());
            lineView.setBackgroundColor(mViewAttribute.lineColor());
            params.bottomToTop = lastButtonView.getId();
            ctlContent.addView(lineView, params);
            lastButtonView = lineView;
        }
        ctlContent.addView(button, layoutParams);
    }


    public int getDP(int dp) {
        return WindowUtils.dip2px(mContext, dp);
    }

    /**
     * 显示窗口
     *
     * @return BottomIosDialog
     */
    public BottomIosDialog show() {
        mPopUp.hasShadowBg(mViewAttribute.isWindowShadow())
                .dismissOnTouchOutside(mViewAttribute.isCancel())
                .asCustom(this)
                .showWindow();
        return this;
    }


    protected abstract List<String> getItems();

    protected abstract void itemClicks(Button button, int position);
}
