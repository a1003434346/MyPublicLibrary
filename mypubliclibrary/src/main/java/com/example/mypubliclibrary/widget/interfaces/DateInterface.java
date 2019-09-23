package com.example.mypubliclibrary.widget.interfaces;

/**
 * 日期接口
 */
public interface DateInterface {
    interface GetDate {
        void getDate(String year, String month, String day);
    }

    interface GetTime {
        void getTime(String hours, String minutes);
    }
}
