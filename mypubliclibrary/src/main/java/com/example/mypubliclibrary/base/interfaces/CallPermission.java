package com.example.mypubliclibrary.base.interfaces;

public interface CallPermission {
    //申请成功
    void onPermissionSuccess(int requestCode);

    //申请失败
    void onPermissionError(int requestCode);

    //禁止申请
    void onPermissionForbid();
}
