package com.example.mypubliclibrary.util;

import java.util.HashMap;
import java.util.Map;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/9/11.
 */
public class MapUtils<K, V> {

    private Map<K, V> mKvMap;

    public MapUtils(Map<K, V>... kvMap) {
        mKvMap = kvMap.length > 0 ? kvMap[0] : new HashMap<>();
    }

    /**
     * 如果value不为null,给Map添加value,避免把Null传给后台后，后台会抛错
     * 记得调用create
     *
     * @param k k
     * @param v v
     * @return kvMap
     */
    public MapUtils putValueNotNull(K k, V v) {
        if (v != null) {
            mKvMap.put(k, v);
        }
        return this;
    }

    /**
     * 链式添加K,V
     * 记得调用create
     *
     * @param k k
     * @param v v
     * @return kvMap
     */
    public MapUtils putValue(K k, V v) {
        mKvMap.put(k, v);
        return this;
    }

    public Map<K, V> create() {
        return mKvMap;
    }
}
