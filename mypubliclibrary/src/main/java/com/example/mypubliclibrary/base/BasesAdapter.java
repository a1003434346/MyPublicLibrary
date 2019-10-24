package com.example.mypubliclibrary.base;

import android.content.Context;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/8/19.
 */
public abstract class BasesAdapter<T> extends CommonAdapter<T> implements View.OnClickListener{
    protected BasesActivity mActivity;

    public BasesAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
        mActivity = (BasesActivity) context;
    }
}
