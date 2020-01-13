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
    //是否在发送中
    private boolean isSendInge;
    //验证码
    public String code;
    //超时的时间,不设置默认为60秒
    private int mOutTime;
    //发送间隔时间
    private int mSendIntervalTime;

    public CodeUtils setOutTime(int mOutTime) {
        this.mOutTime = mOutTime;
        return this;
    }

    @SuppressLint("StaticFieldLeak")
    private static volatile CodeUtils codeUtils;

    public static CodeUtils getInstance() {
        if (codeUtils == null) {
            codeUtils = new CodeUtils();
            codeUtils.mOutTime = 60;
        }
        return codeUtils;
    }

//    /**
//     * 是否在发送中
//     *
//     * @return true/false
//     */
//    public boolean getIsSendIng() {
//        long codeSendTime = Long.parseLong(SharedPreferencesUtils.getInstance().getParam("CodeSendTime", Long.parseLong("0")).toString());
//        if (codeSendTime == 0) {
//            initSend();
//        } else {
//            mSendIntervalTime = DateUtils.getIntervalSeconds(System.currentTimeMillis(), codeSendTime);
//            if (mSendIntervalTime < mOutTime) {
//                isSendInge = true;
//            } else {
//                initSend();
//            }
//        }
//        return isSendInge;
//    }

    /**
     * 是否在发送中
     *
     * @return true/false
     */
    public boolean getIsSendIng() {
        long codeSendTime = Long.parseLong(SharedPreferencesUtils.getInstance().getParam("CodeSendTime", Long.parseLong("0")).toString());
        if (codeSendTime > 0) {
            mSendIntervalTime = DateUtils.getIntervalSeconds(System.currentTimeMillis(), codeSendTime);
            if (mSendIntervalTime < mOutTime) isSendInge = true;
        }
        return isSendInge;
    }


//    /**
//     * 初始化发送
//     */
//    private void initSend() {
//        SharedPreferencesUtils.getInstance().setParam("CodeSendTime", System.currentTimeMillis());
//        isSendInge = false;
//        mSendIntervalTime = 0;
//    }


    /**
     * 开始读取倒计时
     *
     * @param number        验证码
     * @param countdownView 设置显示倒计时的控件
     * @param originalText  设置倒计时结束后还原的文字
     *                      调用前记得在Application里初始化SharedPreferencesUtils.getInstance().init(this);
     *                      Ui销毁时记得调用CodeUtils.getInstance().cancel(); 否则会造成内存泄露
     *                      采用本地时间进行计算的，下次开始会自动拾取区间进行倒计时
     */
    public void startCountdown(String number, TextView countdownView, String originalText) {
//        boolean result = !getIsSendIng();
        if (!isSendInge) {
            SharedPreferencesUtils.getInstance().setParam("CodeSendTime", System.currentTimeMillis());
            mSendIntervalTime = 0;
        }

        timing(number, countdownView, originalText);
//        return result;
    }

    //当前倒计时类
    private CountDownTimer mCountDownTimer;

    public void cancel() {
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
    }

    private void timing(String number, TextView countdownView, String originalText) {
        this.code = number;
        if (isSendInge && mCountDownTimer != null) mCountDownTimer.cancel();
        final int[] outTime = {mSendIntervalTime > 0 ? mOutTime - mSendIntervalTime : mOutTime};
        //发送验证码
        mCountDownTimer = new CountDownTimer(outTime[0] * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //当前任务每完成一次倒计时间隔回调
                countdownView.setText(("重新发送(" + (--outTime[0]) + "S)"));
            }

            @Override
            public void onFinish() {
                //执行完成
                countdownView.setText(originalText);
                SharedPreferencesUtils.getInstance().remove("CodeSendTime");
                code = "0";
                mSendIntervalTime = 0;
                isSendInge = false;
                cancel();
            }
        }.start();
    }
}
