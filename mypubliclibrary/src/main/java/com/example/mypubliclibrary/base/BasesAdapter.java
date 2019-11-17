package com.example.mypubliclibrary.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mypubliclibrary.base.interfaces.AdapterOnClick;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/8/19.
 */
public abstract class BasesAdapter<T> extends CommonAdapter<T> implements AdapterOnClick<T>, View.OnClickListener {
    protected BasesActivity mActivity;

    private List<AdapterOnClick> mAdapterOnClicks;

    private List<ClickBean> clickBeans;

    private View itemView;

    private class ClickBean {
        public View view;
        public T data;
    }

    public void setOnClickListener(int viewId, AdapterOnClick adapterOnClick, T t) {
        ClickBean clickBean = new ClickBean();
        View view = mActivity.bindId(itemView, viewId);
        clickBean.data = t;
        clickBean.view = view;
        view.setOnClickListener(this);
        mAdapterOnClicks.add(adapterOnClick);
        clickBeans.add(clickBean);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < clickBeans.size(); i++) {
            if (v.getId() == clickBeans.get(i).view.getId()) {
                mAdapterOnClicks.get(i).onClick(v, clickBeans.get(i).data);
            }
        }
    }

    public BasesAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
        itemView = LayoutInflater.from(context).inflate(layoutId, null);
        mActivity = (BasesActivity) context;
    }
}
