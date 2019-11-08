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

    public void updateListData(List<T> oldData, List<T> newData) {
        oldData.clear();
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
}
