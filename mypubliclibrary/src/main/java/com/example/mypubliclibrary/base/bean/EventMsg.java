package com.example.mypubliclibrary.base.bean;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/10.
 */
public class EventMsg<T, I> {
    //发起人是谁，一般用来区分发起人
    private String mInitiator;
    //传递消息,成功对应CannotLittle里的常量,失败返回错误信息
    private String mMessage;
    //事件类型，一般用来处理逻辑
    private String mType;
    //当前请求的Request，对应请求数据接口的枚举类型（可选）
    private I mRequest;

    private T mData;

    public I getRequest() {
        return mRequest;
    }

    public String getType() {
        return mType;
    }

    public EventMsg setType(String mType) {
        this.mType = mType;
        return this;
    }

    public void setRequest(I mRequest) {
        this.mRequest = mRequest;
    }

    public T getData() {
        return mData;
    }

    public EventMsg<T, I> setData(T data) {
        this.mData = data;
        return this;
    }

    public String getInitiator() {
        return mInitiator;
    }

    public EventMsg<T, I> setInitiator(String initiator) {
        this.mInitiator = initiator;
        return this;
    }

    public String getMessage() {
        return mMessage;
    }

    public EventMsg<T, I> setMessage(String message) {
        this.mMessage = message;
        return this;
    }
}
