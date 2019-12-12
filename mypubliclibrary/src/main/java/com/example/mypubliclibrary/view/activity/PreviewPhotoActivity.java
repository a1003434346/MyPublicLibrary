package com.example.mypubliclibrary.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.view.fragment.PreviewPhotoFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 预览照片
 */
public class PreviewPhotoActivity extends BasesActivity {
    //照片
    List<Object> photos;
    // 预览
    private ViewPager vpPreview;
    //照片位置
    private int photoPosition;


    public ViewPageAdapter viewPageAdapter;

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMsg message) {

    }

    @Override
    protected int onRegistered() {
        return R.layout.activity_preview_photo;
    }

    @Override
    protected void initView() {
        vpPreview = (ViewPager) bindId(R.id.vp_preview);

    }

    @Override
    protected void initData() {
        // 设置适配器,绑定每页要显示的内容
        photos = new ArrayList<>();
        photos.addAll((List<Object>) getIntent().getExtras().getSerializable("photos"));
        photos.remove("upload");
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        photoPosition = getIntent().getIntExtra("photoPosition", 0);
        vpPreview.setAdapter(viewPageAdapter);
        vpPreview.setCurrentItem(photoPosition);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void setStatusBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void finish() {
        super.finish();
        //设置进出动画
        overridePendingTransition(0, R.anim.photo_exit_out);
    }

    public class ViewPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();

        ViewPageAdapter(FragmentManager fm) {
            super(fm);
            for (int i = 0; i < photos.size(); i++) {
                fragments.add(new PreviewPhotoFragment(i, photos.get(i)));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

}
