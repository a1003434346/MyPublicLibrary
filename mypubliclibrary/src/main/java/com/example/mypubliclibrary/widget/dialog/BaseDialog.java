package com.example.mypubliclibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.example.mypubliclibrary.R;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/29.
 */
public abstract class BaseDialog extends Dialog {

    //底部
    protected int bottom = Gravity.BOTTOM;
    //顶部
    protected int top = Gravity.TOP;
    //居中
    protected int center = Gravity.CENTER;


    public BaseDialog(Context context) {
        super(context, R.style.DefaultProgressDialog);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onRegistered());
        initView();
        int position = getGravity();
        getWindow().getAttributes().gravity = position == 0 ? center : position;
    }

    protected View bindId(int viewId) {
        return findViewById(viewId);
    }

    //不传值默认为居中
    protected abstract int getGravity();

    protected abstract int onRegistered();

    protected abstract void initView();

}
