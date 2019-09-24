package com.example.myapplication;

import android.content.Context;

import com.example.mypubliclibrary.util.ToastUtils;

public class TestPre {

    public void test(Context context) {
        ToastUtils.showLongToast(context, "提示信息");
    }
}
