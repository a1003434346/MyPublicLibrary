package com.example.mypubliclibrary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.dialog.CProgressDialog;
import com.example.mypubliclibrary.widget.dialog.WarningDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

    protected abstract void initView();

    protected abstract void initData();

    protected View myView;

    //开启数据更新
    protected boolean DataUpdate;

    protected BasesActivity activity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (myView == null) {
            myView = inflater.inflate(onRegistered(), container, false);
            activity = (BasesActivity) getActivity();
            mPresenter = ObjectUtil.getT(this.getClass());
            initView();
            initData();
        } else if (DataUpdate) {
            initData();
        }
        return myView;
    }


    @Override
    public void onStart() {
//        WindowUtils.setStatusBar(getActivity());
//        setStatusBar();
        WindowUtils.setStatusTitle(getContext(), bindId(R.id.ctl_title));

        super.onStart();
        EventBusUtils.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBusUtils.unregister(this);
    }


    public String getTextValue(int textId) {
        return ((TextView) bindId(textId)).getText().toString();
    }


    /**
     * 显示加载提示，可取消为透明背景色，不可取消为蒙色背景
     */
    public CProgressDialog showLoading(boolean cancelable) {
        if (activity.loadingDialog == null)
            activity.loadingDialog = new CProgressDialog(getContext());
        if (!activity.loadingDialog.isShowing()) {
            activity.loadingDialog.cancelable(cancelable);
            activity.loadingDialog.show();
        }
        return activity.loadingDialog;

//        loadingDialog.setProgressText(text.length > 0 ? text[0] : "加载中...");
    }

    /**
     * 关闭加载等待
     */
    public void dismissLoading() {
        if (activity.loadingDialog != null && activity.loadingDialog.isShowing()) {
            activity.loadingDialog.dismiss();
        }
    }


    public void jumpActivity(Class<?> aClass) {
        startActivity(new Intent(new Intent(getActivity(), aClass)).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    /**
     * 跳转到Activity 带参数跳转
     *
     * @param aClass Activity的Class
     */
    public void jumpActivity(Class<?> aClass, TreeMap<String, Object> paramMap) {
        Intent intent = new Intent(getActivity(), aClass).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            if (value instanceof String) {
                intent.putExtra(key, (String) paramMap.get(key));
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (Integer) paramMap.get(key));
            }
        }
        startActivity(intent);
    }

    /**
     * 获取提示框
     *
     * @param showValue WarningDialog
     *                  记得调用.show()方法显示
     */
    public WarningDialog getWarningDialog(String title, String showValue) {
        return WindowUtils.getWarningDialog(getContext(), title, showValue);
    }

    /**
     * 跳转到Fragment
     *
     * @param containerViewId 所在布局ID
     * @param fragment        fragment
     */
    public void jumpFragment(int containerViewId, Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    /**
     * 带参数跳转Fragment,Bundle传值时给TreeMap的value里new BundleUtils().put("recruit", recruitBean)
     * 取值时User user = (User) getArguments().getSerializable(key);
     *
     * @param containerViewId id
     * @param fragment        fragment
     * @param paramMap        参数
     *                        跳转过去以后取值
     *                        getArguments().getString(key)
     */
    public void jumpFragment(int containerViewId, Fragment fragment, TreeMap<String, Object> paramMap) {
        Intent intent = new Intent();
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            if (value instanceof String) {
                intent.putExtra(key, (String) paramMap.get(key));
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (Integer) paramMap.get(key));
            }
            if (value instanceof Bundle) {
                intent.putExtras((Bundle) value);
            }
        }
        fragment.setArguments(intent.getExtras());
        getActivity().getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, fragment.getClass().getSimpleName()).commit();
    }


    public void bindClick(int viewId) {
        myView.findViewById(viewId).setOnClickListener(this);
    }

    public void bindClick(View view, int viewId, View.OnClickListener listener) {
        view.findViewById(viewId).setOnClickListener(listener == null ? this : listener);
    }

    public void bindClick(View view, View.OnClickListener listener, List<Integer> integers) {
        for (int viewId : integers) {
            bindClick(view, viewId, listener);
        }
    }

    public View bindId(int viewId) {
        return myView.findViewById(viewId);
    }

    public int getResourcesColor(int color) {
        return getResources().getColor(color);
    }

    public View bindId(View view, int viewId) {
        return view.findViewById(viewId);
    }

    public int getDP(int px) {
        return WindowUtils.dip2px(myView.getContext(), px);
    }


    /**
     * 赋值Text,显示text,隐藏editText
     *
     * @param textView text
     * @param editText editText
     */
    protected void setTextHideEditText(TextView textView, EditText editText, String value) {
        setTextView(textView, value);
        showView(textView.getId());
        hideView(editText.getId());
    }

    /**
     * 给view设置Text内容
     *
     * @param viewId viewId
     * @param value  value
     */
    public void setTextValue(int viewId, String value) {
        ((TextView) bindId(viewId)).setText(value);
    }

    /**
     * 给TextView设置内容
     *
     * @param text  textView
     * @param value value
     */
    protected void setTextView(TextView text, String value) {
        text.setText(value);
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
//        setSoftInput(etId, false);
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(etId.getWindowToken(), 0);
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
