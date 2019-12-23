package com.example.mypubliclibrary.widget.dialog.basic;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.util.ShapeUtils;
import com.example.mypubliclibrary.util.WindowUtils;

/**
 * function:
 * describe:警告窗口
 * Created By LiQiang on 2019/8/15.
 */
public class BackUpWarningDialog {
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public BackUpWarningDialog(Context context, String shouValue) {
        this.context = context;
        this.showValue = shouValue;
        initView();
    }

    private View popView;

    private PopupWindow popWindow;
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

    private TextView tvLine;
    //显示内容
    private String showValue;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        if (popView == null) {
            popView = LayoutInflater.from(context).inflate(R.layout.dialog_warning, null);
            popWindow = WindowUtils.getPopupWindow(context, popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            View.OnClickListener onClickListener = getOnClickListener();
            ConstraintLayout ctlDialogWarning = popView.findViewById(R.id.ctl_dialog_warning);
            ConstraintLayout ctlContent = popView.findViewById(R.id.ctl_content);
            btnClick1 = popView.findViewById(R.id.btn_click1);
            btnClick2 = popView.findViewById(R.id.btn_click2);
            title = popView.findViewById(R.id.tv_title);
            ctlTitle = popView.findViewById(R.id.ctl_title);
            btnMiddleOne = popView.findViewById(R.id.btn_middle_one);
            tvLine = popView.findViewById(R.id.tv_line);
            ((TextView) popView.findViewById(R.id.tv_warning_value)).setText(showValue);
            ctlDialogWarning.setOnClickListener(onClickListener);
            ctlContent.setOnClickListener(onClickListener);
            btnClick1.setOnClickListener(onClickListener);
            btnClick2.setOnClickListener(onClickListener);
            btnMiddleOne.setOnClickListener(onClickListener);
            ctlContent.setBackground(ShapeUtils.getRadiusRectangle(0, 0, 14, context.getResources().getColor(R.color.colorWhite)));
            btnClick1.setBackground(ShapeUtils.getRadiusRectangle(0, 0, new float[]{0, 0, 0, 0, 0, 0, 14, 14}, context.getResources().getColor(R.color.colorWindowBack)));
            btnClick2.setBackground(ShapeUtils.getRadiusRectangle(0, 0, new float[]{0, 0, 0, 0, 14, 14, 0, 0}, context.getResources().getColor(R.color.colorWindowBack)));
            btnMiddleOne.setBackground(ShapeUtils.getRadiusRectangle(0, 0, new float[]{0, 0, 0, 0, 14, 14, 14, 14}, context.getResources().getColor(R.color.colorWindowBack)));
        }
    }

    private View.OnClickListener getOnClickListener() {
        return  view -> {
//            int id = view.getId();
//            if (id == R.id.ctl_dialog_warning) {//窗口
//                popWindow.dismiss();
//            } else if (id == R.id.ctl_content) {//内容,进行占位,避免误触关闭
//            } else if (id == R.id.btn_click1) {//点击Button1
//                if (clickBtn1 != null) clickBtn1.selectDone();
//                popWindow.dismiss();
//            } else if (id == R.id.btn_click2) {//点击Button2
//                if (clickBtn2 != null) clickBtn2.selectDone();
//                popWindow.dismiss();
//            } else if (id == R.id.btn_middle_one) {//点击中间按钮
//                if (middleOne != null) middleOne.selectDone();
//                popWindow.dismiss();
//            }
        };
    }


    /**
//     * 设置Button1的单击接口
//     *
//     * @param clickBtn1 单击接口
//     */
//    public BackUpWarningDialog setClickBtn1Interface(WidgetInterface.Warning clickBtn1) {
//        this.clickBtn1 = clickBtn1;
//        return this;
//    }

    /**
     * 设置Button1的文本颜色
     *
     * @param color 颜色
     */
    public BackUpWarningDialog setClickBtn1TextColor(int color) {
        btnClick1.setTextColor(color);
        return this;
    }

    /**
     * 设置Button2的文本颜色
     *
     * @param color 颜色
     */
    public BackUpWarningDialog setClickBtn2TextColor(int color) {
        btnClick2.setTextColor(color);
        return this;
    }

//    /**
//     * 设置Button2的单击接口
//     *
//     * @param clickBtn2 单击接口
//     */
//    public BackUpWarningDialog setClickBtn2Interface(WidgetInterface.Warning clickBtn2) {
//        this.clickBtn2 = clickBtn2;
//        return this;
//    }

//    /**
//     * 设置中间按钮的的单击接口
//     *
//     * @param middleOne 单击接口
//     */
//    public BackUpWarningDialog setMiddleOneInterface(WidgetInterface.Warning middleOne) {
//        this.middleOne = middleOne;
//        return this;
//    }


    /**
     * 设置Button1的文本内容
     *
     * @param value 内容
     */
    public BackUpWarningDialog setButtonText1(String value) {
        btnClick1.setText(value);
        return this;
    }

    /**
     * 设置Button1的文本内容
     *
     * @param value 内容
     */
    public BackUpWarningDialog setTitle(String value) {
        if (!value.isEmpty()) {
            title.setText(value);
            ctlTitle.setVisibility(View.VISIBLE);
        } else {
            ctlTitle.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置Button2的文本内容
     *
     * @param value 内容
     */
    public BackUpWarningDialog setButtonText2(String value) {
        btnClick2.setText(value);
        return this;
    }

    /**
     * 设置中间按钮的文本内容
     *
     * @param value 内容
     */
    public BackUpWarningDialog setMiddleOne(String value) {
        btnClick1.setVisibility(View.GONE);
        btnClick2.setVisibility(View.GONE);
        tvLine.setVisibility(View.GONE);
        btnMiddleOne.setText(value);
        btnMiddleOne.setVisibility(View.VISIBLE);
        return this;
    }


    public BackUpWarningDialog show() {
        WindowUtils.setBackgroundAlpha(context, 0.5f);
        popWindow.showAtLocation(popView, Gravity.CENTER, 0, 0);
        return this;
    }
}
