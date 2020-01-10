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
    //接口请求的行为
    private String mRequestBehavior;

    //当前请求的Request，对应请求数据接口的枚举类型（可选）
    private Object mRequest;

    private Object mData;
    //是否为刷新
    private boolean isRefresh;
    //发起人的页面
    private Object mInitiatorPage;

    public <T> T getRequest() {
        return (T) mRequest;
    }

    public String getType() {
        return mType == null ? "" : mType;
    }

    public <T> T getInitiatorPage() {
        return (T) mInitiatorPage;
    }

    public void setInitiatorPage(Object initiatorPage) {
        this.mInitiatorPage = initiatorPage;
    }

    public EventMsg setType(String mType) {
        this.mType = mType;
        return this;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public EventMsg setRequest(Object mRequest) {
        this.mRequest = mRequest;
        return this;
    }

    public <T> T getData() {
        return (T) mData;
    }

    public EventMsg setData(Object data) {
        this.mData = data;
        return this;
    }

    public String getInitiator() {
        return mInitiator;
    }

    public EventMsg setInitiator(String initiator) {
        this.mInitiator = initiator == null ? "" : initiator;
        return this;
    }

    public String getMessage() {
        return mMessage == null ? "" : mMessage;
    }

    public EventMsg setMessage(String message) {
        this.mMessage = message;
        return this;
    }

    public String getRequestBehavior() {
        return mRequestBehavior == null ? "" : mRequestBehavior;
    }

    public EventMsg setRequestBehavior(String mRequestBehavior) {
        this.mRequestBehavior = mRequestBehavior;
        return this;
    }
}
