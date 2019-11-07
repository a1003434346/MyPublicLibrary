package com.example.myapplication;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.util.EventBusUtils;
import com.example.mypubliclibrary.util.ListUtils;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.dialog.BottomIosDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends BasesActivity<TestPre> {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMsg message) {
//        if (message.getType().equals("test")) {
//            mPresenter.test(this);
//        }
    }

    @Override
    protected int onRegistered() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        EventBusUtils.register(this);
        Log.i("testId", bindId(R.id.ctl_content).getId() + "");

    }

    @Override
    protected void initData() {
        mPresenter.test(this);
//        EventBusUtils.post(new EventMsg<>().setType("test"));
    }

    @Override
    protected void initListener() {
        bindClick(R.id.tv_test);

    }

    boolean test;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_test:
                jumpFragment(R.id.fl_test, new BlankFragment());

//                jumpActivity(Main2Activity.class);
//                new BottomIosDialog(this) {
//                    @Override
//                    protected List<String> getItems() {
//                        return new ListUtils<String>().add("播放完当前声音结束", "5分钟", "15分钟", "30分钟", "60分钟");
//                    }
//
//                    @Override
//                    protected void itemClicks(Button button, int position) {
//
//                    }
//                }.setBackgroundColor(Color.parseColor("#000000")).setLineColor(Color.parseColor("#565656")).setCancelShow(false).show();
                break;
        }
    }
}
