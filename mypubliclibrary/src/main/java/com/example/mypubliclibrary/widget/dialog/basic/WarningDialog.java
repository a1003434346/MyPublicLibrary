package com.example.mypubliclibrary.widget.dialog.basic;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.util.ShapeUtils;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.dialog.build.BuildPasswordAttribute;
import com.example.mypubliclibrary.widget.dialog.build.BuildWarningAttribute;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.enums.PopupAnimation;

/**
 * function:
 * describe:警告窗口
 * Created By LiQiang on 2019/8/15.
 */
public abstract class WarningDialog extends CenterPopupView {
    private Context context;
    private XPopup.Builder mPopUp;
    private BuildWarningAttribute mBuildAttribute;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public WarningDialog(Context context) {
        super(context);
        this.context = context;
        mPopUp = new XPopup.Builder(getContext())
                .popupAnimation(PopupAnimation.TranslateFromBottom);
    }

    //
//    private View popView;
//
//    private PopupWindow popWindow;
//    //按钮1的单击接口
//    private WidgetInterface.Warning clickBtn1;
//    //按钮2的单击接口
//    private WidgetInterface.Warning clickBtn2;
//    //中间按钮的单击接口
//    private WidgetInterface.Warning middleOne;
    //按钮1
    private Button btnClick1;
    //按钮1
    private Button btnClick2;
    //只有一个按钮,居中显示
    private Button btnMiddleOne;
    //弹框的标题
    private TextView title;
    //标题的区域
    private ConstraintLayout ctlTitle;

    private TextView tvWarningValue;
    private TextView tvLine;


    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_warning;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        ConstraintLayout ctlContent = findViewById(R.id.ctl_content);
        btnClick1 = findViewById(R.id.btn_click1);
        btnClick2 = findViewById(R.id.btn_click2);
        title = findViewById(R.id.tv_title);
        ctlTitle = findViewById(R.id.ctl_title);
        btnMiddleOne = findViewById(R.id.btn_middle_one);
        tvLine = findViewById(R.id.tv_line);
        tvWarningValue = findViewById(R.id.tv_warning_value);

        ctlContent.setBackground(ShapeUtils.getRadiusRectangle(0, 0, 14, context.getResources().getColor(R.color.colorWhite)));
        btnClick1.setBackground(ShapeUtils.getRadiusRectangle(0, 0, new float[]{0, 0, 0, 0, 0, 0, 14, 14}, context.getResources().getColor(R.color.colorWindowBack)));
        btnClick2.setBackground(ShapeUtils.getRadiusRectangle(0, 0, new float[]{0, 0, 0, 0, 14, 14, 0, 0}, context.getResources().getColor(R.color.colorWindowBack)));
        btnMiddleOne.setBackground(ShapeUtils.getRadiusRectangle(0, 0, new float[]{0, 0, 0, 0, 14, 14, 14, 14}, context.getResources().getColor(R.color.colorWindowBack)));
        initData();
    }

    private void initData() {

        btnClick1.setText(mBuildAttribute.getButtonText1());
        btnClick2.setText(mBuildAttribute.getButtonText2());
        setMiddleOneValue(mBuildAttribute.getMiddleOneValue());
        tvWarningValue.setText(mBuildAttribute.getShowValue());
        setTitle(mBuildAttribute.getTitle());

        btnClick1.setTextColor(mBuildAttribute.getBtn1TextColor());
        btnClick2.setTextColor(mBuildAttribute.getBtn2TextColor());
        btnMiddleOne.setTextColor(mBuildAttribute.getMiddleTextColor());
        tvWarningValue.setTextColor(mBuildAttribute.getShowValueColor());
        title.setTextColor(mBuildAttribute.getTitleColor());

        View.OnClickListener onClickListener = getOnClickListener();
        btnClick1.setOnClickListener(onClickListener);
        btnClick2.setOnClickListener(onClickListener);
        btnMiddleOne.setOnClickListener(onClickListener);
    }

    private View.OnClickListener getOnClickListener() {
        return view -> {
            int i = view.getId();
            if (i == R.id.btn_click1) {
                btnOneClick();
            } else if (i == R.id.btn_click2) {
                btnTwoClick();
            } else if (i == R.id.btn_middle_one) {
                btnMiddleClick();
                dismiss();
            }
        };
    }

    /**
     * 点击btn1的回调
     */
    protected abstract void btnOneClick();

    /**
     * 点击btn2的回调
     */
    protected  abstract void btnTwoClick();

    /**
     * 点击中间btn的回调（只有一个Button,居中显示）
     */
    protected abstract void btnMiddleClick();


    /**
     * 设置title的文本内容
     *
     * @param value 内容
     */
    private void setTitle(String value) {
        if (!value.isEmpty()) {
            title.setText(value);
            ctlTitle.setVisibility(View.VISIBLE);
        } else {
            ctlTitle.setVisibility(View.GONE);
        }
    }


    /**
     * 设置中间按钮的文本内容
     *
     * @param value 内容
     */
    private void setMiddleOneValue(String value) {
        if (value != null) {
            btnClick1.setVisibility(View.GONE);
            btnClick2.setVisibility(View.GONE);
            tvLine.setVisibility(View.GONE);
            btnMiddleOne.setText(value);
            btnMiddleOne.setVisibility(View.VISIBLE);
        }
    }


    public WarningDialog show() {
        mPopUp.hasShadowBg(mBuildAttribute.isWindowShadow)
                .dismissOnTouchOutside(mBuildAttribute.isCancel)
                .asCustom(this)
                .showWindow();
        return this;
    }

    public WarningDialog setViewAttribute(BuildWarningAttribute viewAttribute) {
        mBuildAttribute = viewAttribute;
        return this;
    }

    @Override
    protected void onCreate() {
        initView();
    }

    @Override
    protected void onDismiss() {

    }

    @Override
    protected void onShow() {

    }
}
