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
        EventBusUtils.postSticky(new EventMsg().setType("webCall").setData(webCalls.length > 0 ? webCalls[0] : null));
        activity.jumpActivity(WebViewActivity.class, treeMap);
    }

    public static void jumpWeb(final BasesActivity activity, String url, final String title, boolean isShowLoadingBar, WebCall... webCalls) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("title", title);
        treeMap.put("url", url);
        treeMap.put("isShowLoadingBar", isShowLoadingBar);
        EventBusUtils.postSticky(new EventMsg().setType("webCall").setData(webCalls.length > 0 ? webCalls[0] : null));
        activity.jumpActivity(WebViewActivity.class, treeMap);
    }

}
