package com.example.myapplication;

import android.content.Context;

import com.example.mypubliclibrary.util.ToastUtils;

public class TestFragment {
    public void test(Context context) {
        ToastUtils.showLongToast(context, "提示信息TestFragment");
    }
}
