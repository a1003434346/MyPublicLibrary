package com.example.mypubliclibrary.base;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.base.interfaces.CallPermission;
import com.example.mypubliclibrary.util.EventBusUtils;
import com.example.mypubliclibrary.util.ObjectUtil;
import com.example.mypubliclibrary.util.SelectorUtils;
import com.example.mypubliclibrary.util.ToastUtils;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.widget.dialog.CProgressDialog;
import com.example.mypubliclibrary.widget.dialog.InputDialog;
import com.example.mypubliclibrary.widget.dialog.WarningDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/5.
 */
public abstract class BasesActivity<T> extends SwipeBackActivity implements View.OnClickListener, CallPermission {
    protected T mPresenter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public abstract void onEvent(EventMsg message);

    protected abstract int onRegistered();

    protected abstract void initView();

    protected abstract void initData();


    /**
     * 跳转到Activity
     *
     * @param aClass Activity的Class
     * @param noBack 是否不可以返回,默认false
     */
    public void jumpActivity(Class<?> aClass, boolean... noBack) {
        if (noBack.length > 0) {
//            startActivity(new Intent(this, aClass).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
            startActivity(new Intent(this, aClass).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            startActivity(new Intent(this, aClass).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
        }

    }


    public int getDP(int px) {
        return WindowUtils.dip2px(this, px);
    }

//    private CountDownTimer countDownTimer;

    public int getResourcesColor(int color) {
        return getResources().getColor(color);
    }

//    /**
//     * 开始倒计时验证码
//     */
//    protected void startSendCode(TextView textView, String originalText) {
//        //发送验证码
//        new CountDownTimer(60 * 1000, 1000) {
//            int time = 60;
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//                //当前任务每完成一次倒计时间隔回调
//                textView.setText((--time + "S后重试"));
//            }
//
//            @Override
//            public void onFinish() {
//                //执行完成
//                textView.setText(originalText);
//            }
//        }.start();
//    }

    /**
     * 跳转到Activity
     *
     * @param aClass      Activity的Class
     * @param requestCode 回调onActivityResult里的requestCode参数
     */
    public void jumpActivity(Class<?> aClass, int requestCode) {
        startActivityForResult(new Intent(this, aClass).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode);
    }

    /**
     * 带参数跳转到Activity，回退返回结果
     *
     * @param aClass      Activity的Class
     * @param requestCode 回调onActivityResult里的requestCode参数
     */
    public void jumpActivity(Class<?> aClass, TreeMap<String, Object> paramMap, int requestCode) {
        startActivityForResult(paramIntent(aClass, paramMap).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode);
    }

    /**
     * 跳转到Activity 带参数跳转,Bundle传值时给TreeMap的value里new BundleUtils().put("recruit", recruitBean),如果是list数据,需要强制转换为
     * new BundleUtils().put("recruit", (Serializable)list);
     * 取值时User user = (User) getIntent().getExtras().getSerializable(key);
     *
     * @param aClass Activity的Class
     */
    public void jumpActivity(Class<?> aClass, TreeMap<String, Object> paramMap) {
        startActivity(paramIntent(aClass, paramMap).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    /**
     * 不带启动模式的跳转Activity
     *
     * @param aClass
     * @param paramMap
     */
    public void startActivity(Class<?> aClass, TreeMap<String, Object> paramMap) {
        startActivity(paramIntent(aClass, paramMap));
    }

    private Intent paramIntent(Class<?> aClass, TreeMap<String, Object> paramMap) {
        Intent intent = new Intent(this, aClass);
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
        return intent;
    }

    /**
     * 获取提示框
     *
     * @param showValue WarningDialog
     *                  记得调用.show()方法显示
     */
    public WarningDialog getWarningDialog(String title, String showValue) {
        return WindowUtils.getWarningDialog(this, title, showValue);
    }


    /**
     * protected Boolean formValidation(TreeMap<Integer, String> editMap, String type) {
     * switch (type) {
     * case "EditText":
     * for (Integer key : editMap.keySet()) {
     * if (((EditText) findViewById(key)).getText().toString().trim().isEmpty()) {
     * ToastUtils.showLongToast(this, editMap.get(key) + "为空");
     * return false;
     * }
     * }
     */

    public CProgressDialog loadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(onRegistered());
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mPresenter = ObjectUtil.getT(this.getClass());
        initView();
        initData();
    }

    /**
     * 设置背景色和圆角
     *
     * @param color  背景色
     * @param radius 圆角
     * @return StateListDrawable
     */
    public StateListDrawable getBackRadius(int color, int radius) {
        return SelectorUtils.newShapeSelector().setDefaultBgColor(getResourcesColor(color)).setCornerRadius(new float[]{getDP(radius)}).create();
    }

    public void setBackground(int viewId, Drawable backGround) {
        bindId(viewId).setBackground(backGround);
    }

    //设置沉浸式状态栏，并把状态栏颜色改为透明色
    protected void setStatusBar() {
        WindowUtils.setStatusBar(this);
    }


    private InputMethodManager imm;

//    /**
//     * 关闭/打开键盘
//     *
//     * @param editText editText
//     */
//    public void setSoftInput(EditText editText, Boolean isOpen) {
//        if (imm != null && imm.isActive()) {
//            if (isOpen) {
//                imm.showSoftInput(editText, 0);
//            } else {
//                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
//            }
//        }
//    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ImageView imageView = (ImageView) findViewById(R.id.iv_back);
        if (imageView != null)
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        //设置状态栏的背景色为title的背景色,如果有title,给title增加状态栏间距
        setStatusTitle();
    }

    private void setStatusTitle() {
        WindowUtils.setStatusTitle(this, bindId(R.id.ctl_title));
    }

    //    public boolean checkPermission() {
//        boolean isGranted = true;
//        if (android.os.Build.VERSION.SDK_INT >= 23) {
////            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////                //如果没有写sd卡权限
////                isGranted = false;
////            }
////            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////                isGranted = false;
////            }
//            //Fragment调用((Activity) mCPContext).requestPermissions
//            if (!isPermission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                requestPermissions(
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
//                                .ACCESS_FINE_LOCATION,
//                                Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        102);
//            }
//        }
//
//        return isGranted;
//
//    }

    /**
     * 请求权限
     * 默认拒绝请求处理：
     * 如果不再询问状态会自动跳转到权限设置，拒绝会Toast未获得权限
     *
     * @param requestCode 请求码,如果要对拒绝请求做处理，可以用requestCode判断来自于哪一个申请
     * @param permissions Manifest.permission.XX(权限名称)
     */
    protected void requestPermission(int requestCode, String... permissions) {
        if (!isPermission(permissions) && android.os.Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permissions, requestCode);
        }
    }

    /**
     * 是否已经注册权限
     *
     * @param permissions 权限
     * @return true/false
     */
    protected boolean isPermission(String... permissions) {
        if (permissions.length > 0 && android.os.Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                    return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isSuccess = true;
        if (grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        //禁用的回调
                        onPermissionForbid();
                    } else {
                        //调用失败的回调
                        onPermissionError(requestCode);
                    }
                    isSuccess = false;
                    break;
                }
            }
        }
        //调用成功的回调
        if (isSuccess)
            onPermissionSuccess(requestCode);
    }


    /**
     * 权限请求成功的回调
     */
    @Override
    public void onPermissionSuccess(int requestCode) {

    }

    /**
     * 权限请求失败的回调
     */
    @Override
    public void onPermissionError(int requestCode) {
        ToastUtils.showLongToast(this, "未获得权限");
    }

    /**
     * 禁止申请的回调
     */
    @Override
    public void onPermissionForbid() {
        ToastUtils.showLongToast(this, "请手动打开权限");
        // 延迟2s再发送
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 0x006);
                this.cancel();
            }
        }, 2000);// 这里百毫秒
    }


    public void setViewBackGround(View myView, int viewId, GradientDrawable gradientDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            bindId(myView, viewId).setBackground(gradientDrawable);
        }
    }


    /**
     * 显示加载提示，可取消为透明背景色，不可取消为蒙色背景
     */
    public CProgressDialog showLoading(boolean cancelable) {
        if (loadingDialog == null) loadingDialog = new CProgressDialog(this);
        if (!loadingDialog.isShowing()) {
            loadingDialog.cancelable(cancelable);
            loadingDialog.show();
        }
        return loadingDialog;
//        loadingDialog.setProgressText(text.length > 0 ? text[0] : "加载中...");
    }


    /**
     * 清楚Edit的Text内容
     */
    public void clearEditText(List<Integer> viewIdList) {
        if (viewIdList.size() > 0)
            for (int viewId : viewIdList) {
                ((EditText) findViewById(viewId)).setText("");
            }
    }

    /**
     * 关闭加载等待
     */
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


    /**
     * 验证EditText表单是否为空
     *
     * @param editMap editMap
     * @return true为通过, false为不通过
     */
    public Boolean formValidation(LinkedHashMap<Integer, String> editMap) {
        for (Integer key : editMap.keySet()) {
            if (((TextView) findViewById(key)).getText().toString().trim().isEmpty()) {
                ToastUtils.showLongToast(this, editMap.get(key) + "为空");
                return false;
            }
        }
        return true;
    }

    /**
     * 验证EditText表单是否为空
     *
     * @param editMap editMap
     * @return true为通过, false为不通过
     */
    public Boolean formValidation(View view, LinkedHashMap editMap) {
        for (Object key : editMap.keySet()) {
            if (((TextView) bindId(view, (Integer) key)).getText().toString().trim().isEmpty()) {
                ToastUtils.showLongToast(this, editMap.get(key) + "为空");
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBusUtils.unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBusUtils.register(this);
    }


    /**
     * 跳转到Fragment
     *
     * @param containerViewId 所在布局ID
     * @param fragment        fragment
     */
    public void jumpFragment(int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    public void bindClick(int viewId) {
        findViewById(viewId).setOnClickListener(this);
    }

    protected void bindClick(List<Integer> integers) {
        for (int viewId : integers) {
            bindClick(viewId);
        }
    }

    public void bindClick(View view, List<Integer> integers, View.OnClickListener listener) {
        for (int viewId : integers) {
            view.findViewById(viewId).setOnClickListener(listener == null ? this : listener);
        }
    }

    public <T extends View> T bindId(int viewId) {
        return (T) findViewById(viewId);
    }

    public <T extends View> T bindId(View view, int viewId) {
        return view.findViewById(viewId);
    }


//    /**
//     * 赋值Text,显示text,隐藏editText
//     *
//     * @param textView text
//     * @param editText editText
//     */
//    protected void setTextHideEditText(TextView textView, EditText editText, String value) {
//        setTextValue(textView.getId(), value);
//        showView(textView);
//        hideView(editText);
//    }


    /**
     * 给TextView设置内容
     *
     * @param viewId viewId
     * @param value  value
     */
    public void setTextValue(int viewId, CharSequence value) {
        ((TextView) bindId(viewId)).setText(value);
    }

    /**
     * 给TextView设置Hide内容
     *
     * @param viewId viewId
     * @param value  value
     */
    public void setTextHide(int viewId, String value) {
        ((TextView) bindId(viewId)).setHint(value);
    }

    /**
     * 给view设置Text内容
     *
     * @param viewId viewId
     * @param value  value
     */
    public void setTextValue(View view, int viewId, CharSequence value) {
        ((TextView) bindId(view, viewId)).setText(value);
    }


    /**
     * 获取输入窗口
     *
     * @param textHint hint
     * @param viewId   要给哪个文本框显示
     * @param connect  是否关联实时显示,不设置默认为true
     * @return InputDialog
     */
    public InputDialog getInputDialog(String textHint, final int viewId, boolean... connect) {
        TextView inputView = bindId(viewId);
        InputDialog inputDialog = new InputDialog(this) {
            @Override
            protected void submitValue(String value) {
                inputView.setText(value);
            }
        }.setTextHint(textHint);
        if (connect.length == 0) {
            //关联显示TextView,不显示提交按钮，并且键盘右下角显示为完成
            inputDialog.connectView(inputView);
            inputDialog.setSubmitVisibility(false);
        } else {
            inputDialog.setSubmitText("提交");
        }
        inputDialog.setInputValue(inputView.getText().toString());
        return inputDialog;

    }

    public String getTextValue(int textId) {
        return ((TextView) bindId(textId)).getText().toString();
    }

    public String getTextValue(View view, int textId) {
        return ((TextView) bindId(view, textId)).getText().toString();
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
     * 隐藏view
     *
     * @param views viewId
     */
    public void hideView(List<Integer> views) {
        for (Integer viewId : views) {
            bindId(viewId).setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏view
     *
     * @param view view
     */
    public void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    /**
     * 是否隐藏view
     *
     * @param viewId viewId
     */
    public boolean isHide(View view, int viewId) {
        return bindId(view, viewId).getVisibility() == View.GONE;
    }

    /**
     * 隐藏view
     *
     * @param viewId viewId
     */
    public void hideView(View myView, int viewId) {
        bindId(myView, viewId).setVisibility(View.GONE);
    }

    /**
     * 隐藏view
     *
     * @param viewIds viewIds
     */
    public void hideViews(List<Integer> viewIds) {
        for (Integer view : viewIds) {
            bindId(view).setVisibility(View.GONE);
        }
    }

    /**
     * 显示view
     *
     * @param viewId viewId
     */
    public void showView(int viewId) {
        bindId(viewId).setVisibility(View.VISIBLE);
    }

    /**
     * 显示view
     *
     * @param view view
     */
    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 显示view
     *
     * @param viewId viewId
     */
    public void showView(View view, int viewId) {
        bindId(view, viewId).setVisibility(View.VISIBLE);
    }

    /**
     * 关闭键盘
     *
     * @param etId 对应EditText
     */
    public void closeKeyboard(EditText etId) {
//        setSoftInput(etId, false);
        imm.hideSoftInputFromWindow(etId.getWindowToken(), 0);
    }

    /**
     * view获得焦点
     *
     * @param view view
     */
    public void setFocusable(View view) {
        view.setFocusable(true);
        //姓名edit获得焦点
        view.requestFocus();
    }

    /**
     * 打开键盘
     *
     * @param etId 对应EditText
     */
    public void openKeyboard(EditText etId) {
//        setSoftInput(etId, true);
        imm.showSoftInput(etId, 0);
    }
}
