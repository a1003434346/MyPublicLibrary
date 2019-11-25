package com.example.mypubliclibrary.base.interfaces;

import android.view.View;

public interface AdapterOnClick<T> {

    void onClick(View v, T t, int position);
}
