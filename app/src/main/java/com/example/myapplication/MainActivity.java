package com.example.myapplication;

import android.view.View;

import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.util.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BasesActivity<TestPre> {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMsg message) {
        if(message.getType().equals("test")){
            mPresenter.test(this);
        }
    }

    @Override
    protected int onRegistered() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        EventBusUtils.register(this);
    }

    @Override
    protected void initData() {

        EventBusUtils.post(new EventMsg<>().setType("test"));
    }


    @Override
    public void onClick(View view) {

    }
}
