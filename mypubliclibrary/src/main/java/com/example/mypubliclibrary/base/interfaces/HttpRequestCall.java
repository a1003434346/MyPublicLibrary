package com.example.mypubliclibrary.base.interfaces;

import com.example.mypubliclibrary.base.bean.EventMsg;

/**
 * 功能:
 * Created By leeyushi on 2019/12/27.
 */
public interface HttpRequestCall {

    //接口请求失败
    void onQuestError(EventMsg message);

    //接口请求成功
    void onQuestSuccess(EventMsg message);
}
