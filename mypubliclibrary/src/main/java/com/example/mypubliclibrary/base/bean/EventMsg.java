package com.example.mypubliclibrary.base.bean;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/10.
 */
public class EventMsg<T, I> {
    //事件类型,用于细分事件类型
    private String type;
    //传递消息,成功对应CannotLittle里的常量,失败返回错误信息
    private String message;
    //当前请求的Request，对应请求数据接口的枚举类型（可选）
    private I mRequest;

    private T data;

    public I getRequest() {
        return mRequest;
    }

    public void setRequest(I mRequest) {
        this.mRequest = mRequest;
    }

    public T getData() {
        return data;
    }

    public EventMsg<T,I> setData(T data) {
        this.data = data;
        return this;
    }

    public String getType() {
        return type;
    }

    public EventMsg<T,I> setType(String type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public EventMsg<T,I> setMessage(String message) {
        this.message = message;
        return this;
    }
}
