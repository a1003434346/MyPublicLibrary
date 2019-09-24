package com.example.mypubliclibrary.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/9/20.
 */
public class ObjectUtil {
    /**
     * 深度拷贝对象
     *
     * @param obj 目标对象
     * @param <T> 对象
     * @return T
     */
    public static <T extends Serializable> T deepCopy(T obj) {
        T cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            //分配内存,写入原始对象,生成新对象
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());//获取上面的输出字节流
            ObjectInputStream ois = new ObjectInputStream(bais);

            //返回生成的新对象
            cloneObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }

    public static <T> T getT(Class<?> cClass) {
        Type genType = cClass.getGenericSuperclass();
        T t = null;
        //如果泛型不等于Null
        if (!genType.getClass().getName().equals("java.lang.Class")) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            Class<?> aClass = (Class) params[0];
            try {
                //实例化泛型
                t = (T) aClass.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
}
