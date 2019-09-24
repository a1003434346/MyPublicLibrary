package com.example.mypubliclibrary.util;


import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/11.
 */
public class BeanUtils {
    /**
     * 把Map 转换为实体类
     *
     * @param object    想要操作的map对象
     * @param beanClass 实体类
     * @return 实体类
     * @throws Exception
     */
    public static <T> T mapToObject(Object object, Class<?> beanClass) {
        Map<String, Object> map;
        if (object == null) {
            return null;
        } else {
            map = (Map<String, Object>) object;
        }

        Object obj = null;
        try {
            obj = beanClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            //把double转换为String
            Object value = map.get(field.getName());
            if (value instanceof Double) {
                value = value.toString();
            }
            try {
                field.set(obj, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) obj;
    }

    /**
     * 字符串Json返回实体类
     *
     * @param strJson 字符串Json
     * @param <T>     类型.class
     * @return 实体类
     */
    public static <T> T jsonFromBean(String strJson, Class<T> aClass) {
        return new Gson().fromJson(strJson, aClass);
    }

}
