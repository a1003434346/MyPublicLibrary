package com.example.mypubliclibrary.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * function:
 * describe:验证码工具类
 * Created By LiQiang on 2019/8/29.
 */
public class CodeUtils {
    /**
     * 验证成功以后，得调用isStart=false还原
     * CodeUtils.getInstance().isStart=false;
     */
    //是否已经进入倒计时
    public boolean isStart;
    //验证码
    public String code;


    @SuppressLint("StaticFieldLeak")
    private static volatile CodeUtils codeUtils;

    public static CodeUtils getInstance() {
        if (codeUtils == null) {
            codeUtils = new CodeUtils();
        }
        return codeUtils;
    }


    /**
     * 开始计时验证码倒计时
     *
     * @param number        验证码
     * @param countdownView 设置显示倒计时的控件
     * @param originalText  设置倒计时结束后还原的文字
     * @param context       context
     * @return 是否获取频繁，间隔时间为60秒
     */
    public boolean startTiming(String number, TextView countdownView, String originalText, Context context) {
        boolean result = false;
        long codeSendTime = Long.parseLong(SharedPreferencesUtils.getInstance().getParam("CodeSendTime", Long.parseLong("0")).toString());
        if (codeSendTime == 0) {
            //第一次发送，开始倒计时
            SharedPreferencesUtils.getInstance().setParam("CodeSendTime", System.currentTimeMillis());
            timing(number, countdownView, originalText);
            result = true;
        } else if (DateUtils.getIntervalSeconds(System.currentTimeMillis(), codeSendTime) < 60) {
            ToastUtils.showLongToast(context, "获取验证码频繁");
        } else {
            //超过60秒，开始倒计时
            timing(number, countdownView, originalText);
            result = true;
        }
        return result;
    }

    private void timing(String number, TextView countdownView, String originalText) {
        if (!isStart) {
            isStart = true;
            this.code = number;
            //发送验证码
            new CountDownTimer(60 * 1000, 1000) {
                int time = 60;

                @Override
                public void onTick(long millisUntilFinished) {
                    if (isStart) {
                        //当前任务每完成一次倒计时间隔回调
                        countdownView.setText((--time + "S后重试"));
                    } else {
                        onFinish();
//                        downTimer.onFinish();
                    }
                }

                @Override
                public void onFinish() {
                    //执行完成
                    countdownView.setText(originalText);
                    isStart = false;
                    SharedPreferencesUtils.getInstance().remove("CodeSendTime");
                    code = "0";
                    cancel();
                }
            }.start();
        }
    }
}
