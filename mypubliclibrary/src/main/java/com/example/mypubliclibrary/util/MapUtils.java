package com.example.mypubliclibrary.util;

import java.util.Map;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/9/11.
 */
public class MapUtils {

    private static MapUtils mapUtils;

    public static MapUtils getInstance() {
        if (mapUtils == null) {
            mapUtils = new MapUtils();
        }
        return mapUtils;
    }

    /**
     * 如果value不为null,给Map添加value
     *
     * @param kvMap kvMap
     * @param k     k
     * @param v     v
     * @return kvMap
     */
    public <K, V> MapUtils putValue(Map<K, V> kvMap, K k, V v) {
        if (v != null) {
            kvMap.put(k, v);
        }
        return mapUtils;
    }
}
