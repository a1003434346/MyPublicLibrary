package com.example.mypubliclibrary.util;

import com.blankj.utilcode.util.GsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/9/2.
 */
public class JsonUtils {
    private JSONObject jsonObject;

    public JsonUtils(String strJson) {
        try {
            jsonObject = new JSONObject(strJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JsonUtils getJSONObject(String key) {
        try {
            jsonObject = jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public <T> T getValue(String key) {
        T t = null;
        try {
            t = (T) jsonObject.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return t;
    }
}
