package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.base.bean.EventMsg;

public class Main2Activity extends BasesActivity {

    @Override
    public void onEvent(EventMsg message) {

    }

    @Override
    protected int onRegistered() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        bindClick(R.id.tv_2);
    }

    @Override
    protected void setData() {

    }


    @Override
    public void onClick(View v) {
        finish();
    }
}
