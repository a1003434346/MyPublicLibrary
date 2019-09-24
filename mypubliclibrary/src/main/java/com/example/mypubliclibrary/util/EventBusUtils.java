package com.example.mypubliclibrary.util;

import android.content.Context;

import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.util.constant.DataInterface;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/8/6.
 */
public class EventBusUtils {
    /**
     * 解绑
     *
     * @param subscriber 对象
     */
    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber))
            EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 注册
     *
     * @param subscriber context
     */
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber))
            EventBus.getDefault().register(subscriber);
    }

    /**
     * API是否请求成功
     *
     * @param context  context
     * @param eventMsg 消息
     * @return true请求成功
     */
    public static boolean isSuccess(Context context, EventMsg eventMsg) {
        if (eventMsg.getMessage() != null && !eventMsg.getMessage().equals(DataInterface.SUCCESS)) {
            ToastUtils.showLongToast(context, eventMsg.getMessage());
            return false;
        }
        return true;
    }

    /**
     * API请求错误信息是否为指定内容
     *
     * @param message  message
     * @param eventMsg 消息
     * @return true请求成功
     */
    public static boolean isMessage(String message, EventMsg eventMsg) {
        return eventMsg.getMessage() != null && eventMsg.getMessage().equals(message);
    }


    /**
     * API是否请求成功
     *
     * @param context  context
     * @param eventMsg 消息
     * @return true请求成功
     */
    public static boolean isSuccess(RefreshLayout refreshlayout, Context context, EventMsg eventMsg) {
        if (!eventMsg.getMessage().equals(DataInterface.SUCCESS)) {
            ToastUtils.showLongToast(context, eventMsg.getMessage());
            refreshlayout.finishRefresh(false/*false*/);//传入false表示刷新失败
            return false;
        }
        return true;
    }

    public static <T> void post(EventMsg<T> eventMsg) {
        EventBus.getDefault().post(eventMsg);
    }

}
