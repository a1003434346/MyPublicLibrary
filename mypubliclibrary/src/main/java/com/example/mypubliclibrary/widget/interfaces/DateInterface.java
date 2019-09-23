package com.example.mypubliclibrary.widget.interfaces;

/**
 *
 */
public interface DateInterface {
    interface GetDate {
        void getDate(String year, String month, String day);
    }

    interface GetTime {
        void getTime(String hours, String minutes);
    }
}
