package com.example.myapplication;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.util.CodeUtils;
import com.example.mypubliclibrary.util.EventBusUtils;
import com.example.mypubliclibrary.util.ListUtils;
import com.example.mypubliclibrary.util.NumberUtil;
import com.example.mypubliclibrary.util.SharedPreferencesUtils;
import com.example.mypubliclibrary.util.ToastUtils;
import com.example.mypubliclibrary.widget.dialog.basic.WarningDialog;
import com.example.mypubliclibrary.widget.dialog.build.BuildPasswordAttribute;
import com.example.mypubliclibrary.widget.dialog.build.BuildSelectTextAttribute;
import com.example.mypubliclibrary.widget.dialog.build.BuildWarningAttribute;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
        SharedPreferencesUtils.getInstance().init(this);

    }

    @Override
    protected void initData() {
        mPresenter.test(this);
//        EventBusUtils.post(new EventMsg<>().setType("test"));
    }

    @Override
    protected void initListener() {
        bindClick(new ListUtils<Integer>().add(R.id.tv_test, R.id.tv_test1));
    }


    boolean test;
    Timer mTimer;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_test:
//                new BuildPasswordAttribute(this) {
//                    @Override
//                    protected void onDone(String password) {
//                        ToastUtils.showLongToast(MainActivity.this, "输入了" + password);
//                    }
//                }.setTitle("请再次输入支付密码")
////                        .setHintText("使用会员卡余额支付需要验证身份。")
////                        .setMoney("￥ 100.00")
//                        .createWindow()
//                        .show();
                new BuildWarningAttribute(this) {
                    @Override
                    protected void oneClick() {

                    }

                    @Override
                    protected void twoClick() {

                    }
                }.setTitle("登录")
                        .setShowValue("需要登录才能购买，是否现在去登录？")
                        .createWindow()
                        .show();
//                getPhotoView(3);
//                ImageUtils.previewPhoto(this, new ListUtils<>().add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548756837006&di=551df0dcf59d1d71673c3d46b33f0d93&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201308%2F04%2F20130804155912_wCRnE.thumb.700_0.jpeg",
//                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2279952540,2544282724&fm=26&gp=0.jpg", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=851052518,4050485518&fm=26&gp=0.jpg"));
//                CodeUtils.getInstance().startTiming("", bindId(R.id.tv_test), "点击重发验证码");
//                jumpActivity(Main2Activity.class);

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
            case R.id.tv_test1:
                mTimer.cancel();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        CodeUtils.getInstance().cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CodeUtils.getInstance().readCountdown("", bindId(R.id.tv_test), "点击重发验证码");
    }
}
