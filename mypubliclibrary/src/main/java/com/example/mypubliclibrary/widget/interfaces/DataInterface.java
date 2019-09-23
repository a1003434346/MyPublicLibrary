package com.example.mypubliclibrary.widget.interfaces;

import java.util.List;

public interface DataInterface {
    interface GetList<T> {
        void getList(List<T> list);
    }
}
