package com.example.mypubliclibrary.base.bean;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/10.
 */
public class EventMsg {
    //发起人是谁，一般用来区分发起人
    private String mInitiator;
    //传递消息,成功对应CannotLittle里的常量,失败返回错误信息
    private String mMessage;
    //事件类型，一般用来处理逻辑
    private String mType;
    //当前请求的Request，对应请求数据接口的枚举类型（可选）
    private Object mRequest;

    private Object mData;

    public <T> T getRequest() {
        return (T) mRequest;
    }

    public String getType() {
        return mType == null ? "" : mType;
    }

    public EventMsg setType(String mType) {
        this.mType = mType;
        return this;
    }

    public void setRequest(Object mRequest) {
        this.mRequest = mRequest;
    }

    public <T> T getData() {
        return (T) mData;
    }

    public EventMsg setData(Object data) {
        this.mData = data;
        return this;
    }

    public String getInitiator() {
        return mInitiator == null ? "" : mInitiator;
    }

    public EventMsg setInitiator(String initiator) {
        this.mInitiator = initiator;
        return this;
    }

    public String getMessage() {
        return mMessage == null ? "" : mMessage;
    }

    public EventMsg setMessage(String message) {
        this.mMessage = message;
        return this;
    }
}
