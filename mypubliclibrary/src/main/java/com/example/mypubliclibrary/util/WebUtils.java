package com.example.mypubliclibrary.util;

import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.base.interfaces.WebCall;
import com.example.mypubliclibrary.view.activity.WebViewActivity;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/8/26.
 */
public class WebUtils {
    public static void jumpWeb(final BasesActivity activity, String url, final String title, WebCall... webCalls) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("title", title);
        treeMap.put("url", url);
        treeMap.put("isShowLoadingBar", true);
        activity.jumpActivity(WebViewActivity.class, treeMap);
        if (webCalls.length > 0)
            EventBusUtils.post(new EventMsg().setType("webCall").setData(webCalls[0]));
    }

    public static void jumpWeb(final BasesActivity activity, String url, final String title, boolean isShowLoadingBar, WebCall... webCalls) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("title", title);
        treeMap.put("url", url);
        treeMap.put("isShowLoadingBar", isShowLoadingBar);
//        if (webCalls.length > 0)
//            treeMap.put("webCall", webCalls[0]);
        activity.jumpActivity(WebViewActivity.class, treeMap);
    }

}
