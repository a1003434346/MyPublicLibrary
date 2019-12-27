package com.example.mypubliclibrary.view.fragment;


import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.BasesFragment;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.util.ImageUtils;
import com.example.mypubliclibrary.view.activity.PreviewPhotoActivity;
import com.github.chrisbanes.photoview.PhotoView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 预览图片
 * A simple {@link Fragment} subclass.
 */
public class PreviewPhotoFragment extends BasesFragment {
    //索引
    private int index;
    //照片
    private Object photo;

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMsg message) {

    }

    @Override
    protected int onRegistered() {
        return R.layout.fragment_preview_photo;
    }

    @Override
    protected void initView() {
        PhotoView photoView = (PhotoView) bindId(R.id.preview_image);
        bindClick(R.id.preview_image);
        setTextValue(R.id.tv_image_page, index + "/" + ((PreviewPhotoActivity) getContext()).viewPageAdapter.getCount());
        ImageUtils.setImage(photoView, photo);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void mayRefreshData() {

    }

    public PreviewPhotoFragment(int index, Object photo) {
        this.photo = photo;
        this.index = index + 1;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.preview_image) {
            getActivity().finish();
        }
    }
}
