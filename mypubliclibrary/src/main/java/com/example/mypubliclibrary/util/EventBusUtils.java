package com.example.mypubliclibrary.util;

import android.content.Context;

import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.util.constant.DataInterface;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
     * @param context      context
     * @param eventMsg     消息
     * @param initiator    发起人
     * @param currentValid 是否只对当前发起人有效
     * @return true请求成功
     */
    public static boolean isSuccess(Context context, EventMsg eventMsg, String initiator, boolean currentValid, SmartRefreshLayout... srlRefreshHead) {
        //判断当前发起人是否有效，如果接口中的发起人为空，代表不区分发起人
        boolean isValid = !currentValid || initiator.equals(eventMsg.getInitiator()) || StringUtils.isEmpty(eventMsg.getInitiator());
        boolean result = eventMsg.getRequest() != null && eventMsg.getMessage() != null && eventMsg.getMessage().equals(DataInterface.SUCCESS) && isValid;
        if (!result && eventMsg.getRequest() != null && !StringUtils.isEmpty(eventMsg.getMessage()) && !eventMsg.getMessage().equals(DataInterface.SUCCESS))
            ToastUtils.showLongToast(context, eventMsg.getMessage());
        if (srlRefreshHead.length > 0 && srlRefreshHead[0] != null && eventMsg.getRequest() != null) {
            srlRefreshHead[0].finishRefresh(result);
            srlRefreshHead[0].finishLoadMore(result);
        }
        return result;
    }


    public static void post(EventMsg eventMsg) {
        EventBus.getDefault().post(eventMsg);
    }

}
