package com.example.mypubliclibrary.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/31.
 */
public class ListUtils<T> {
    private List<T> tList;

    public ListUtils() {
        tList = new ArrayList<T>();
    }

    public List<T> build() {
        return tList;
    }

    @SafeVarargs
    public final List<T> add(T... t) {
        tList.addAll(Arrays.asList(t));
        return tList;
    }

    public List<T> arraysToList(T[] ts) {
        tList.addAll(Arrays.asList(ts));
        return tList;
    }

    /**
     * 更新数据
     *
     * @param oldData 数据源
     * @param newData 新的数据
     */
    public static <T> void updateListData(List<T> oldData, List<T> newData) {
        oldData.clear();
        if (newData != null)
            oldData.addAll(newData);
    }

    /**
     * 深度拷贝List
     *
     * @param src 目标list
     * @return list
     */
    public static <T extends Serializable> List<T> deepCopy(List<T> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            out.flush();
            out.close();
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        } catch (IOException ignored) {

        } catch (ClassNotFoundException ignored) {

        }
        return null;

    }

    /**
     * 格式化List数据
     *
     * @param data       数据源
     * @param lineNumber 一行显示几个
     * @param <T>        t
     * @return List<T>
     * 示例 mRechargeList.addAll(formatting(beans, 3));
     */
    public static <T> List<T> formatting(List data, int lineNumber) {
        List<List<T>> result = new ArrayList<>();
        List temporary = new ArrayList();
        temporary.addAll(data);
        List value;
        while (temporary.size() >= lineNumber) {
            value = new ArrayList();
            for (int index = 0; index < lineNumber; index++) {
                value.add(temporary.get(0));
                temporary.remove(0);
            }
            result.add(value);
        }
        value = new ArrayList();
        for (int i = 0; i < temporary.size(); i++) {
            value.add(temporary.get(i));
        }
        result.add(value);
        return (List<T>) result;
    }

    public static void listMapToStringList(List<Map<String, Object>> maps, String label, List<String> strings) {
        strings.clear();
        for (Map<String, Object> map : maps) {
            String value = map.get(label).toString();
            if (!value.isEmpty())
                strings.add(value);
        }
    }

    /**
     * 把List变为指定长度
     *
     * @param tList  tList
     * @param length length
     */
    public static <T> void listToLength(List<T> tList, int length) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (i < tList.size())
                result.add(tList.get(i));
        }
        tList.clear();
        tList.addAll(result);
    }
}
