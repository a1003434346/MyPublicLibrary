package com.example.myapplication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mypubliclibrary.base.BasesFragment;
import com.example.mypubliclibrary.base.bean.EventMsg;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BasesFragment<TestFragment> {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMsg message) {

    }

    @Override
    protected int onRegistered() {
        return R.layout.fragment_blank;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void requestData() {

    }


    @Override
    public void onClick(View v) {

    }
}
