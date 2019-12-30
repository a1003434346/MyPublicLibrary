package com.example.mypubliclibrary.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mypubliclibrary.base.interfaces.AdapterOnClick;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/8/19.
 */
public abstract class BasesAdapter<T> extends CommonAdapter<T> implements AdapterOnClick<T>, View.OnClickListener {
    protected BasesActivity mActivity;
    public String mInitiator;
    private AdapterOnClick<T> mAdapterOnClicks;

    //如果Adapter里需要访问接口，实现该方法来设置发起人是谁
    public abstract <A> A setInitiator(String initiator);

    public void setOnClickListener(View view, int position) {
        view.setTag(position);
        view.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        mAdapterOnClicks.onClick(v, mDatas.get(position), position);
    }

    public BasesAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
        mActivity = (BasesActivity) context;
        mAdapterOnClicks = this;
    }

}
