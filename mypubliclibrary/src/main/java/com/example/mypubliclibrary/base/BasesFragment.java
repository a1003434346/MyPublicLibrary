package com.example.mypubliclibrary.base;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.util.EventBusUtils;
import com.example.mypubliclibrary.util.ObjectUtil;
import com.example.mypubliclibrary.util.SelectorUtils;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.dialog.CProgressDialog;
import com.example.mypubliclibrary.widget.dialog.WarningDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.TreeMap;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/5.
 */
public abstract class BasesFragment<T> extends Fragment implements View.OnClickListener {
    protected T mPresenter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public abstract void onEvent(EventMsg message);

    protected abstract int onRegistered();

    //初始化View
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //初始化事件
    protected abstract void initListener();

    //访问接口以后设置数据
    protected abstract void setData();

//    protected abstract void getBaseActivity();

    protected View myView;

//    public CProgressDialog loadingDialog;

    //开启数据更新
    protected boolean DataUpdate;
    //    //是否智能设置状态栏，默认为true
//    protected boolean isSetStatus;
//    //是否智能为状态栏设置背景色，默认为true
//    protected boolean isSetStatusColor;
    /**
     * 当前Fragment是否在显示状态
     * 非ViewPage状态下：
     * 如果有多个Fragment，这个属性会在构造函数里都初始化为true,但是只有当前显示的页面会走onCreateView方法（所以虽然所有都为true,但是只有一个页面会显示UI）
     * 如果切换页面，当前页面的mFragmentIsShow会自动设置为false
     * VIewPage状态下：
     * 如果有多个Fragment，这个属性会在构造函数里都初始化为true，之后会依次调用setUserVisibleHint函数（调用的次数跟懒加载的设置有关
     * 顺序为：如果懒加载是1，那么默认先走第一个回调为false(第一个页面),第二个回调为false(懒加载的页面),第三个回调为true(第一个页面)），
     * 简单点讲就是把当前显示页面设置为true，其它未显示的设置为false
     */
    protected boolean mFragmentIsShow;
    //Ui是否加载完成
    protected boolean mUiLoadDone;

    public BasesFragment() {
        mFragmentIsShow = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (myView == null) {
            myView = inflater.inflate(onRegistered(), container, false);
//            if (isSetStatus)
            WindowUtils.setStatusTitle(getContext(), myView);
            initView();
//            mPresenter = ObjectUtil.getT(this.getClass());
//            initData();
//            initEvent();
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    mUiLoadDone = true;
                    //Ui线程空闲下来后去执行（所有生命周期执行完以后才会去执行）
                    mPresenter = ObjectUtil.getT(BasesFragment.this.getClass());
                    initData();
                    initListener();
                    if (mFragmentIsShow) onShowFragment();
                    return false;
                }
            });

        } else if (DataUpdate) {
            initData();
        }
        return myView;
    }

    /**
     * 设置背景色和圆角
     *
     * @param color  背景色
     * @param radius 圆角
     * @return StateListDrawable
     */
    public StateListDrawable getBackRadius(int color, int radius) {
        return SelectorUtils.newShapeSelector().setDefaultBgColor(color).setCornerRadius(new float[]{getDP(radius)}).create();
    }

    public void setBackground(View view, Drawable backGround) {
        view.setBackground(backGround);
    }


    public void setBackground(int viewId, Drawable backGround) {
        bindId(viewId).setBackground(backGround);
    }


    @Override
    public void onStart() {
//        WindowUtils.setStatusBar(getActivity());
//        setStatusBar();

        super.onStart();
    }

    public String getTextValue(int textId) {
        return ((TextView) bindId(textId)).getText().toString();
    }


//    /**
//     * 显示加载提示，可取消为透明背景色，不可取消为蒙色背景
//     */
//    public void showLoading(boolean... cancelable) {
//        activity.showLoading(cancelable);
////        if (loadingDialog == null)
////            loadingDialog = new CProgressDialog(getContext());
////        if (!loadingDialog.isShowing()) {
////            loadingDialog.cancelable(cancelable);
////            loadingDialog.show();
////        }
////        return loadingDialog;
//
//
////        loadingDialog.setProgressText(text.length > 0 ? text[0] : "加载中...");
//    }

//    /**
//     * 关闭加载等待
//     */
//    public void dismissLoading() {
////        if (loadingDialog != null && loadingDialog.isShowing()) {
////            loadingDialog.dismiss();
////        }
//
//        activity.dismissLoading();
//    }

//
//    /**
//     * 跳转到Activity 带参数跳转,Bundle传值时给TreeMap的value里new BundleUtils().put("recruit", recruitBean),如果是list数据,需要强制转换为
//     * new BundleUtils().put("recruit", (Serializable)list);
//     * 取值时User user = (User) getIntent().getExtras().getSerializable(key);
//     *
//     * @param aClass Activity的Class
//     */
//    public void jumpActivity(Class<?> aClass, TreeMap<String, Object> paramMap) {
//        startActivity(paramIntent(aClass, paramMap).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
//    }
//
//    /**
//     * 跳转到Activity
//     *
//     * @param aClass      Activity的Class
//     * @param requestCode 回调onActivityResult里的requestCode参数
//     */
//    public void jumpActivity(Class<?> aClass, int requestCode) {
//        startActivityForResult(new Intent(getContext(), aClass).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode);
//    }
//
//    /**
//     * 带参数跳转到Activity，回退返回结果
//     *
//     * @param aClass      Activity的Class
//     * @param requestCode 回调onActivityResult里的requestCode参数
//     */
//    public void jumpActivity(Class<?> aClass, TreeMap<String, Object> paramMap, int requestCode) {
//        startActivityForResult(paramIntent(aClass, paramMap).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode);
//    }
//
//    /**
//     * 跳转到Activity
//     *
//     * @param aClass Activity的Class
//     * @param noBack 是否不可以返回,默认false
//     */
//    public void jumpActivity(Class<?> aClass, boolean... noBack) {
//        startActivity(new Intent(getContext(), aClass).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
//        if (noBack.length > 0) {
//            getActivity().finish();
//        }
//    }
//
//    private Intent paramIntent(Class<?> aClass, TreeMap<String, Object> paramMap) {
//        Intent intent = new Intent(getContext(), aClass);
//        for (String key : paramMap.keySet()) {
//            Object value = paramMap.get(key);
//            if (value instanceof String) {
//                intent.putExtra(key, (String) paramMap.get(key));
//            }
//            if (value instanceof Integer) {
//                intent.putExtra(key, (Integer) paramMap.get(key));
//            }
//            if (value instanceof Bundle) {
//                intent.putExtras((Bundle) value);
//            }
//        }
//        return intent;
//    }
//
//    /**
//     * 获取提示框
//     *
//     * @param showValue WarningDialog
//     *                  记得调用.show()方法显示
//     */
//    public WarningDialog getWarningDialog(String title, String showValue) {
//        return WindowUtils.getWarningDialog(getContext(), title, showValue);
//    }
//
//    /**
//     * 跳转到Fragment
//     *
//     * @param containerViewId 所在布局ID
//     * @param fragment        fragment
//     */
//    public void jumpFragment(int containerViewId, Fragment fragment) {
//        getActivity().getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, fragment.getClass().getSimpleName()).commit();
//    }
//
//    /**
//     * 带参数跳转Fragment,Bundle传值时给TreeMap的value里new BundleUtils().put("recruit", recruitBean)
//     * 取值时User user = (User) getArguments().getSerializable(key);
//     *
//     * @param containerViewId id
//     * @param fragment        fragment
//     * @param paramMap        参数
//     *                        跳转过去以后取值
//     *                        getArguments().getString(key)
//     */
//    public void jumpFragment(int containerViewId, Fragment fragment, TreeMap<String, Object> paramMap) {
//        Intent intent = new Intent();
//        for (String key : paramMap.keySet()) {
//            Object value = paramMap.get(key);
//            if (value instanceof String) {
//                intent.putExtra(key, (String) paramMap.get(key));
//            }
//            if (value instanceof Integer) {
//                intent.putExtra(key, (Integer) paramMap.get(key));
//            }
//            if (value instanceof Bundle) {
//                intent.putExtras((Bundle) value);
//            }
//        }
//        fragment.setArguments(intent.getExtras());
//        getActivity().getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, fragment.getClass().getSimpleName()).commit();
//    }


    public void bindClick(int viewId) {
        myView.findViewById(viewId).setOnClickListener(this);
    }

    protected void bindClick(List<Integer> integers) {
        for (int viewId : integers) {
            bindClick(viewId);
        }
    }

    public void bindClick(View view, int viewId, View.OnClickListener listener) {
        view.findViewById(viewId).setOnClickListener(listener == null ? this : listener);
    }

    public void bindClick(View view, View.OnClickListener listener, List<Integer> integers) {
        for (int viewId : integers) {
            bindClick(view, viewId, listener);
        }
    }


    public <T extends View> T bindId(int viewId) {
        return myView.findViewById(viewId);
    }

    public int getResourcesColor(int color) {
        return getResources().getColor(color);
    }

    public <T extends View> T bindId(View view, int viewId) {
        return view.findViewById(viewId);
    }

    /**
     * 设置TextColor
     *
     * @param viewId viewId
     * @param color  color
     */
    public void setTextColor(int viewId, int color) {
        ((TextView) bindId(viewId)).setTextColor(getResourcesColor(color));
    }


    /**
     * 跟ViewPage有关的Fragment，不会调用这个方法，其它情况下会调用
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mFragmentIsShow = !hidden;
        if (hidden) {
            onHideFragment();
        } else {
            onShowFragment();
        }
    }

    /**
     * 跟ViewPage有关的Fragment会调用，其它情况下不会调用
     *
     * @param isVisibleToUser 是否显示
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mUiLoadDone)
            if (isVisibleToUser) {
                //当前页面要成为显示状态
                onShowFragment();
            } else if (mFragmentIsShow) {
                //当前页面要成为隐藏状态，但必须是由显示状态转换为隐藏状态（本身就是隐藏的不会调用onHideFragment回调）
                mFragmentIsShow = isVisibleToUser;
                onHideFragment();
            }
        mFragmentIsShow = isVisibleToUser;
    }

    /**
     * 单Fragment状态下：
     * 1.初始化状态下：
     * 初始化Fragment会走onShowFragment回调（onShowFragment会在UI处理完毕后紧挨着initListener回调后面执行）
     * 切换Fragment当前Fragment会先回调onHideFragment函数，新Fragment会按装状态1走
     * 2.缓存状态下：
     * 初始化完毕的Fragment跳转到缓存Fragment，当前Fragment会执行onHideFragment函数，新缓存Fragment会执行onShowFragment函数
     * <p>
     * 单ViewPage状态下：
     * 1.初始化状态下：
     * 初始化Fragment会走onShowFragment回调（只有当前显示的页面会回调onShowFragment，懒加载等未显示的不会调用onShowFragment回调，也
     * 不会调用onHideFragment回调）（onShowFragment会在UI处理完毕后紧挨着initListener回调后面执行）
     * 2.缓存状态下：
     * 初始化完毕的Fragment跳转到缓存Fragment，当前Fragment会执行onHideFragment函数，新缓存Fragment会执行onShowFragment函数，懒加载
     * 之类的本身就是隐藏状态不会调用onHideFragment回调
     * <p>
     * Fragment嵌套ViewPage状态下:
     * 1.初始化状态下：
     * 初始化Fragment会走onShowFragment回调,ViewPage里面显示的Page会回调onShowFragment，ViewPage切换跟单ViewPage回调状态是一样的
     * 外层Fragment切换跟单Fragment回调状态是一样的
     * 2.缓存状态下：
     * 当ViewPage的对应页面已经是缓存状态下，从其它Fragment切换到有ViewPage的Fragment，只会执行单Fragment状态，ViewPage里面本身就是
     * 显示状态的，所以不会调用onShowFragment或者onHideFragment，只有单独对Fragment里面的Page页面做切换时，才会进入单ViewPage状态，
     * 不对ViewPage做切换，只操作外层的Fragment切换，不会触发ViewPage的onShowFragment或者onHideFragment
     */
    protected void onHideFragment() {
        Log.i("BasesFragment", "执行了onHideFragment:" + this.getClass().getSimpleName());
    }

    /**
     * 单Fragment状态下：
     * 1.初始化状态下：
     * 初始化Fragment会走onShowFragment回调（onShowFragment会在UI处理完毕后紧挨着initListener回调后面执行）
     * 切换Fragment当前Fragment会先回调onHideFragment函数，新Fragment会按装状态1走
     * 2.缓存状态下：
     * 初始化完毕的Fragment跳转到缓存Fragment，当前Fragment会执行onHideFragment函数，新缓存Fragment会执行onShowFragment函数
     * <p>
     * 单ViewPage状态下：
     * 1.初始化状态下：
     * 初始化Fragment会走onShowFragment回调（只有当前显示的页面会回调onShowFragment，懒加载等未显示的不会调用onShowFragment回调，也
     * 不会调用onHideFragment回调）（onShowFragment会在UI处理完毕后紧挨着initListener回调后面执行）
     * 2.缓存状态下：
     * 初始化完毕的Fragment跳转到缓存Fragment，当前Fragment会执行onHideFragment函数，新缓存Fragment会执行onShowFragment函数，懒加载
     * 之类的本身就是隐藏状态不会调用onHideFragment回调
     * <p>
     * Fragment嵌套ViewPage状态下:
     * 1.初始化状态下：
     * 初始化Fragment会走onShowFragment回调,ViewPage里面显示的Page会回调onShowFragment，ViewPage切换跟单ViewPage回调状态是一样的
     * 外层Fragment切换跟单Fragment回调状态是一样的
     * 2.缓存状态下：
     * 当ViewPage的对应页面已经是缓存状态下，从其它Fragment切换到有ViewPage的Fragment，只会执行单Fragment状态，ViewPage里面本身就是
     * 显示状态的，所以不会调用onShowFragment或者onHideFragment，只有单独对Fragment里面的Page页面做切换时，才会进入单ViewPage状态，
     * 不对ViewPage做切换，只操作外层的Fragment切换，不会触发ViewPage的onShowFragment或者onHideFragment
     */
    protected void onShowFragment() {
        Log.i("BasesFragment", "执行了onShowFragment:" + this.getClass().getSimpleName());
        if (myView != null)
            WindowUtils.setStatusTitle(getContext(), myView);
//        if (isSetStatus)
//        WindowUtils.setStatusTitle(getContext(), bindId(R.id.ctl_title));
    }


    public int getDP(int px) {
        return WindowUtils.dip2px(myView.getContext(), px);
    }


    /**
     * 给view设置Text内容
     *
     * @param viewId viewId
     * @param value  value
     */
    public void setTextValue(int viewId, CharSequence value) {
        ((TextView) bindId(viewId)).setText(value);
    }


    /**
     * 隐藏view
     *
     * @param viewId viewId
     */
    public void hideView(int viewId) {
        bindId(viewId).setVisibility(View.GONE);
    }

    /**
     * 显示view
     *
     * @param viewId viewId
     */
    protected void showView(int viewId) {
        bindId(viewId).setVisibility(View.VISIBLE);
    }

    /**
     * 关闭键盘
     *
     * @param etId 对应EditText
     */
    protected void closeKeyboard(EditText etId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etId.getWindowToken(), 0);
        }
    }

    /**
     * view获得焦点
     *
     * @param view view
     */
    protected void setFocusable(View view) {
        view.setFocusable(true);
        //姓名edit获得焦点
        view.requestFocus();
    }

    /**
     * 打开键盘
     *
     * @param etId 对应EditText
     */
    protected void openKeyboard(EditText etId) {
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(etId, 0);
    }

}
