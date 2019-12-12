package com.example.mypubliclibrary.widget.dialog.basic;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypubliclibrary.R;


/**
 * 自定义进度条
 */
public class CProgressDialog extends BaseDialog {

    private TextView progressTvText;
    private ImageView ivLoading;
    private Context context;
    //动画集
    private RotateAnimation rotateAnimation;
    //是否显示透明
    private boolean transparent;
    //是否可取消
    private boolean cancelable;


    public CProgressDialog(Context context) {
        super(context);
        this.transparent = true;
        cancelable = true;
        this.context = context;
    }

    @Override
    protected int getGravity() {
        return center;
    }


    @Override
    protected int onRegistered() {
        return R.layout.layout_progress_dialog;
    }

    @Override
    protected void initView() {
        ivLoading = (ImageView) bindId(R.id.iv_loading);
        progressTvText = (TextView) bindId(R.id.progress_tv_text);
        initData();
    }

    private void initData() {
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(1600);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            rotateAnimation.setInterpolator(new AnticipateInterpolator());
        }
    }

    /**
     * 显示加载提示，可取消为透明背景色，不可取消为蒙色背景
     */
    public void show() {
        super.show();
        setCancelable(cancelable);
        if (cancelable) getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ivLoading.setAnimation(rotateAnimation);
    }

    /**
     * 设置是否可取消
     *
     * @param cancelable 是否可取消
     * @return CProgressDialog
     */
    public CProgressDialog cancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    /**
     * 设置是否透明背景
     *
     * @param transparent 是否透明
     * @return CProgressDialog
     */
    public CProgressDialog setTransparent(boolean transparent) {
        this.transparent = transparent;
        return this;
    }

    /**
     * 设置加载提示文字
     *
     * @param text text
     * @return CProgressDialog
     */
    public CProgressDialog setProgressText(String text) {
        progressTvText.setText(text);
        progressTvText.setVisibility(View.VISIBLE);
        return this;
    }
}
