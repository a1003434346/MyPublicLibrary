package com.example.mypubliclibrary.util;

import com.example.mypubliclibrary.base.interfaces.TimerUtilsCall;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 功能:
 * Created By leeyushi on 2020/1/14.
 */
public class TimerUtils {

    /**
     * 延迟运行
     *
     * @param utilsCall 运行的回调
     * @param delay     延迟的单位（毫秒）
     */
    public static void delayRun(TimerUtilsCall utilsCall, long delay) {
        // 延迟发送
        new Timer().schedule(new TimerTask() {
            public void run() {
                utilsCall.run();
            }
        }, delay);
    }
}
