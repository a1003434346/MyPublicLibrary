package com.example.mypubliclibrary.util;

import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.view.activity.WebViewActivity;

import java.util.TreeMap;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/8/26.
 */
public class WebUtils {
    public static void jumpWeb(final BasesActivity activity, String url, final String title) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("title", title);
        treeMap.put("url", url);
        activity.jumpActivity(WebViewActivity.class, treeMap);
    }
}
