package com.example.mypubliclibrary.util;

import android.os.Bundle;

import java.io.Serializable;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/8/16.
 */
public class BundleUtils {

    private Bundle bundle;

    public BundleUtils() {
        bundle = new Bundle();
    }

    public Bundle build() {
        return bundle;
    }

    //传进来的value实体类需要实现Serializable接口
    public final <T extends Serializable> Bundle put(String key, T value) {
        bundle.putSerializable(key, value);
        return bundle;
    }
}
