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
import android.os.Looper;
import android.os.MessageQueue;
import android.os.PersistableBundle;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.bean.EventMsg;
import com.example.mypubliclibrary.base.interfaces.CallPermission;
import com.example.mypubliclibrary.util.ObjectUtil;
import com.example.mypubliclibrary.util.SelectorUtils;
import com.example.mypubliclibrary.util.ToastUtils;
import com.example.mypubliclibrary.util.WindowUtils;
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
//    public CProgressDialog loadingDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public abstract void onEvent(EventMsg message);

    protected abstract int onRegistered();

    //初始化View
    protected abstract void initView();

    //初始化事件
    protected abstract void initData();

    //初始化事件
    protected abstract void initListener();


    //管理Fragment
    private FragmentManager mFragmentManager;

    //mFragmentManager里面当前显示的Fragment
    private Fragment mCurrentShowFragment;
//    //是否智能为状态栏设置背景色，默认为true
//    protected boolean isSetStatusColor;


    public int getDP(int px) {
        return WindowUtils.dip2px(this, px);
    }


    public int getResourcesColor(int color) {
        return getResources().getColor(color);
    }


    /**
     * 跳转到Activity
     *
     * @param aClass      Activity的Class
     * @param requestCode 回调onActivityResult里的requestCode参数
     *                    传值：
     *                    getIntent().putExtra("useraddress",userAddressBeanList.get(position));
     *                    //必须在finish()前调用,否则resultCode会默认成为0
     *                    setResult(10,getIntent());
     *                    finish();
     */
    public void jumpActivity(Class<?> aClass, int requestCode) {
        startActivityForResult(new Intent(this, aClass).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode);
    }

    /**
     * 跳转到Activity
     *
     * @param aClass Activity的Class
     * @param noBack 是否不可以返回,默认false
     */
    public void jumpActivity(Class<?> aClass, boolean... noBack) {
        startActivity(new Intent(this, aClass).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
        if (noBack.length > 0) {
            finish();
        }
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
     * 带参数跳转到Activity，回退返回结果
     *
     * @param aClass      Activity的Class
     * @param requestCode 回调onActivityResult里的requestCode参数
     *                    传值：
     *                    getIntent().putExtra("useraddress",userAddressBeanList.get(position));
     *                    //必须在finish()前调用,否则resultCode会默认成为0
     *                    setResult(10,getIntent());
     *                    finish();
     */
    public void jumpActivity(Class<?> aClass, TreeMap<String, Object> paramMap, int requestCode) {
        startActivityForResult(paramIntent(aClass, paramMap).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), requestCode);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //避免按Home键 会重新实例化入口类的activity
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
        super.onCreate(savedInstanceState);
        isSetStatusColor = true;
        setStatusBar();
        setContentView(onRegistered());
        initView();
        mFragmentManager = getSupportFragmentManager();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mPresenter = ObjectUtil.getT(this.getClass());
        initData();
        initListener();
//        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
//            @Override
//            public boolean queueIdle() {
//                //Ui线程空闲下来后去执行（所有生命周期执行完以后才会去执行）
//                mFragmentManager = getSupportFragmentManager();
//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                mPresenter = ObjectUtil.getT(this.getClass());
//                initData();
//                initEvent();
//                return false;
//            }
//        });


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

    /**
     * 获取Drawable
     *
     * @param id DrawableId
     */
    public Drawable getDrawableRes(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getDrawable(id);
        } else {
            return getResources().getDrawable(id);
        }
    }

    public void setBackground(int viewId, Drawable backGround) {
        bindId(viewId).setBackground(backGround);
    }

    public void setBackground(View view, Drawable backGround) {
        view.setBackground(backGround);
    }

    //设置沉浸式状态栏，并把状态栏颜色改为透明色
    protected void setStatusBar() {
        WindowUtils.setStatusBar(this);
    }


    private InputMethodManager imm;


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


    /**
     * 请求权限，一般配合isPermission使用，调用示例：
     * //判断权限
     * if (!isPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO
     * , Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
     * )) {
     * //请求权限
     * requestPermission(1, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO
     * , Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
     * );
     * }
     * 请求成功后如果需要对成功的结果做回调，需要重写onPermissionSuccess方法
     * 用户拒绝权限请求，默认会弹出“未获得权限”的提示，如果需要额外对拒绝请求做出回调，需要重写onPermissionError方法
     * 如果用户勾选了不再询问，那么默认会提醒用户打开权限，并在2秒后自动跳转到设置权限页面，如果需要对不再询问单独做处理，需要重写onPermissionForbid方法
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


//    /**
//     * 显示加载提示，true为透明背景色，false为蒙色背景，不传默认为false
//     */
//    public void showLoading(boolean... cancelable) {
//        if (loadingDialog == null) loadingDialog = new CProgressDialog(this);
//        if (!loadingDialog.isShowing()) {
//            loadingDialog.cancelable(cancelable.length == 0 || cancelable[0]);
//            loadingDialog.show();
//        }
////        loadingDialog.setProgressText(text.length > 0 ? text[0] : "加载中...");
//    }


    /**
     * 清楚Edit的Text内容
     */
    public void clearEditText(List<Integer> viewIdList) {
        if (viewIdList.size() > 0)
            for (int viewId : viewIdList) {
                ((EditText) findViewById(viewId)).setText("");
            }
    }

//    /**
//     * 关闭加载等待
//     */
//    public void dismissLoading() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
//    }


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


    /**
     * 跳转到Fragment
     *
     * @param containerViewId 所在布局ID
     * @param fragment        fragment
     */
    public void jumpFragment(int containerViewId, Fragment fragment) {
        if (!mFragmentManager.getFragments().contains(fragment)) {
            //未添加Fragment
            try {
                if (fragment == null)
                    fragment = fragment.getClass().newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (mCurrentShowFragment != null)
                transaction.hide(mCurrentShowFragment);
            transaction.add(containerViewId, fragment, fragment.getClass().getSimpleName()).commit();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentShowFragment).show(fragment).commit();
        }
        mCurrentShowFragment = fragment;
//        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //FragmentManager中的所有Fragment都清理掉，避免旋转屏幕的时候出现重叠
        mFragmentManager.getFragments().clear();
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

    public final <T extends View> T bindId(int viewId) {
        return (T) findViewById(viewId);
    }

    public final <T extends View> T bindId(View view, int viewId) {
        return view.findViewById(viewId);
    }


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
     * 设置TextColor
     *
     * @param viewId viewId
     * @param color  color
     */
    public void setTextColor(int viewId, int color) {
        ((TextView) bindId(viewId)).setTextColor(getResourcesColor(color));
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
        imm.showSoftInput(etId, 0);
    }
}
