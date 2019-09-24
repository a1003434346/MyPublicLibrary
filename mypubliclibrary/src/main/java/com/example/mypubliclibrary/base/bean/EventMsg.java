package com.example.mypubliclibrary.base.bean;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/10.
 */
public class EventMsg<T> {
    //事件类型
    private String type;
    //传递消息,成功对应CannotLittle里的常量,失败返回错误信息
    private String message;

    private T data;


    public T getData() {
        return data;
    }

    public EventMsg<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getType() {
        return type;
    }

    public EventMsg<T> setType(String type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public EventMsg<T> setMessage(String message) {
        this.message = message;
        return this;
    }
}
