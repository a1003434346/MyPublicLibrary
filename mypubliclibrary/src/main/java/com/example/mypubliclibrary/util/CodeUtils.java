package com.example.mypubliclibrary.util;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * function:
 * describe:验证码工具类
 * Created By LiQiang on 2019/8/29.
 */
public class CodeUtils {
    //倒计时显示的TextView
    private TextView countdownView;
    //倒计时完成后显示的文字
    private String originalText;
    //是否已经进入倒计时
    public boolean isStart;


    @SuppressLint("StaticFieldLeak")
    private static volatile CodeUtils codeUtils;

    public static CodeUtils getInstance() {
        if (codeUtils == null) {
            codeUtils = new CodeUtils();
        }
        return codeUtils;
    }

    //设置显示倒计时的控件
    public CodeUtils setCountDownView(TextView countDownView) {
        //把旧有的最后状态赋值给新控件
        if (countdownView != null)
            countDownView.setText(countdownView.getText().toString());
        //把旧控件设置为新控件
        countdownView = countDownView;
        return codeUtils;
    }

    //设置倒计时结束显示的文字
    public CodeUtils setOriginalText(String originalText) {
        this.originalText = originalText;
        return codeUtils;
    }

    /**
     * 开始倒计时验证码
     * 需要再initData初始化时,设置上次的缓存等待 R.id.tv_get_code为要显示倒计时的控件，R.string.get_code为倒计时结束后显示的文字
     * if (CodeUtils.getInstance().isStart)
     * CodeUtils.getInstance().setCountDownView((TextView) bindId(R.id.tv_get_code)).setOriginalText(getString(R.string.get_code));
     * }
     * 调用显示倒计时 R.id.tv_get_code为要显示倒计时的控件，R.string.get_code为倒计时结束后显示的文字
     * if (getTextValue(R.id.tv_get_code).equals(getString(R.string.get_code)))
     * //获取验证码
     * CodeUtils.getInstance().setCountDownView((TextView) bindId(R.id.tv_get_code)).setOriginalText(getString(R.string.get_code)).startSendCode();
     */
    public void startSendCode() {
        if (!isStart) {
            isStart = true;
            //发送验证码
            new CountDownTimer(60 * 1000, 1000) {
                int time = 60;

                @Override
                public void onTick(long millisUntilFinished) {
                    //当前任务每完成一次倒计时间隔回调
                    countdownView.setText((--time + "S后重试"));
                }

                @Override
                public void onFinish() {
                    //执行完成
                    countdownView.setText(originalText);
                    //避免内存泄漏,显示控件置为Null
                    countdownView = null;
                    isStart = false;
                }
            }.start();
        }
    }

}
