package com.example.mypubliclibrary.base;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.provider.Settings;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.example.mypubliclibrary.base.interfaces.CodePublicLibrary;
import com.example.mypubliclibrary.base.interfaces.HttpRequestCall;
import com.example.mypubliclibrary.util.ColorUtils;
import com.example.mypubliclibrary.util.EventBusUtils;
import com.example.mypubliclibrary.util.ListUtils;
import com.example.mypubliclibrary.util.ObjectUtil;
import com.example.mypubliclibrary.util.SelectorUtils;
import com.example.mypubliclibrary.util.StringUtils;
import com.example.mypubliclibrary.util.ToastUtils;
import com.example.mypubliclibrary.util.WindowUtils;
import com.example.mypubliclibrary.util.constant.DataInterface;
import com.example.mypubliclibrary.widget.dialog.basic.InputDialog;
import com.example.mypubliclibrary.widget.dialog.basic.InputDialogTest;
import com.example.mypubliclibrary.widget.dialog.basic.WarningDialog;
import com.example.mypubliclibrary.widget.dialog.build.BuildInputAttribute;
import com.example.mypubliclibrary.widget.dialog.build.BuildIosAttribute;
import com.example.mypubliclibrary.widget.photo.GifSizeFilter;
import com.example.mypubliclibrary.widget.photo.MyGlideEngine;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.utils.MediaStoreCompat;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/5.
 */
public abstract class BasesActivity<T> extends SwipeBackActivity implements View.OnClickListener, CallPermission, HttpRequestCall {
    protected T mPresenter;
//    public CProgressDialog loadingDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public abstract void onEvent(EventMsg message);

    protected abstract int onRegistered();

    //初始化View
    protected abstract void initView();

    //初始化样式，紧跟着initView后面
    protected abstract void initStyle();

    //获取页面请求数据，在initView前面
    protected abstract void getPageRequestData();

    //初始化数据
    protected abstract void initData();

    //初始化事件
    protected abstract void initListener();

    /**
     * 可能会刷新的数据
     * 会在onResume回调里调用该方法
     */
    protected abstract void mayRefreshData();

    //Ui是否加载完成
    protected boolean mUiLoadDone;

    //管理Fragment
    private FragmentManager mFragmentManager;

    //mFragmentManager里面当前显示的Fragment
    private Fragment mCurrentShowFragment;
    //    //是否智能为状态栏设置背景色，默认为true
//    protected boolean isSetStatusColor;
    //是否开启跳转动画，默认为开启
    public boolean mJumpAnim;
    //是否允许滑动Finish
    protected boolean isSlideFinish;


    //拍照
    private MediaStoreCompat mMediaStoreCompat;
    //当前页面的唯一标识
    public String mOnlyMark;

    public int getDP(int px) {
        return WindowUtils.dip2px(this, px);
    }


    public int getResourcesColor(int color) {
        return getResources().getColor(color);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //避免按Home键 会重新实例化入口类的activity
//        if (!this.isTaskRoot()) {
//            Intent intent = getIntent();
//            if (intent != null) {
//                String action = intent.getAction();
//                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
//                    finish();
//                    return;
//                }
//            }
//        }
        super.onCreate(savedInstanceState);
        initAttribute();
        setContentView(onRegistered());
        //设置状态栏的背景色为title的背景色,如果有title,给title增加状态栏间距
        setStatusTitle();
        getPageRequestData();
        initView();
        initStyle();
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                mUiLoadDone = true;
                //Ui线程空闲下来后去执行（所有生命周期执行完以后才会去执行）
                mPresenter = ObjectUtil.getT(BasesActivity.this.getClass());
                initData();
                initListener();
                return false;
            }
        });
    }

    private void initSupportFragmentManager() {
        if (mFragmentManager == null) mFragmentManager = getSupportFragmentManager();
    }

    private void initAttribute() {
        mJumpAnim = true;
        setStatusBar();
        setSwipeBackEnable(!isSlideFinish);
        initSupportFragmentManager();
        mOnlyMark = System.nanoTime() + "";
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 拍照
     */
    public void pictures() {
        if (mMediaStoreCompat == null) mMediaStoreCompat = new MediaStoreCompat(this);
        mMediaStoreCompat.setCaptureStrategy(new CaptureStrategy(true,
                getPackageName() + ".fileprovider", "pictures"));
        mMediaStoreCompat.dispatchCaptureIntent(this, 199);
    }

    public void setJumpAnim(boolean isAnim) {
        mJumpAnim = isAnim;
    }


    /**
     * 选择照片
     * 观察者Type为pictures  代表拍照，Data返回的是一个String
     * 观察者Type为selectPhoto 代表相册选择 Data返回的是一个List<String>
     */
    public void selectPhoto(int maxSelect) {
        Matisse.from(this)
                //设置可选择类型
                .choose(MimeType.ofImage(), false)
                //序列化显示
                .countable(true)
                .captureStrategy(
                        new CaptureStrategy(true, getPackageName() + ".fileprovider", "test"))
                //可选择最大数量
                .maxSelectable(maxSelect)
                //设置最小宽高，和最大尺寸
                .addFilter(new GifSizeFilter(0, 0, 15 * Filter.K * Filter.K))
                //限制方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //非原图的压缩规模
                .thumbnailScale(0.85f)
                //设置图片引擎
                .imageEngine(new MyGlideEngine())
                //显示指定媒体类型
                .showSingleMediaType(true)
                //单击是否隐藏选项
                .autoHideToolbarOnSingleTap(true)
                //设置返回码
                .forResult(199);
    }

    /**
     * 获取拍照，选择照片的窗口
     *
     * @param maxSelect 最大选择几张
     *                  选择完成以后在EventBus的事件里接收，pictures为拍照，Data值为String（图片Path）
     *                  selectPhoto为选择相册，Data值为List<String>（图片Path）
     */
    public void getPhotoView(int maxSelect, int... textColor) {
        int themeColor = textColor.length == 0 ? Color.parseColor("#000000") : textColor[0];
        new BuildIosAttribute(this) {
            @Override
            protected void itemClick(Button button, int position) {
                switch (position) {
                    case 0:
                        selectPhoto(maxSelect);
                        break;
                    case 1:
                        pictures();
                        break;
                }
            }
        }.items(new ListUtils<String>().add("从手机相册选择", "拍照"))
                .isShowLine(false)
                .itemTextColor(themeColor)
                .createWindow()
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 199 && resultCode == RESULT_OK) {
            if (data == null) {
                //拍照的
                EventBusUtils.post(new EventMsg().setType(CodePublicLibrary.PICTURES).setData(mMediaStoreCompat.getCurrentPhotoPath()).setMessage(DataInterface.SUCCESS));
            } else {
                //选择相册的
                EventBusUtils.post(new EventMsg().setType(CodePublicLibrary.SELECT_PHOTO).setData(Matisse.obtainPathResult(data)).setMessage(DataInterface.SUCCESS));
            }
        }
    }


    /**
     * 带参数跳转到Activity，回退返回结果，自定义flags,0为默认FLAG_ACTIVITY_REORDER_TO_FRONT
     *
     * @param aClass      Activity的Class
     * @param requestCode 回调onActivityResult里的requestCode参数
     *                    传值：
     *                    getIntent().putExtra("useraddress",userAddressBeanList.get(position));
     *                    //必须在finish()前调用,否则resultCode会默认成为0
     *                    setResult(10,getIntent());
     *                    finish();
     */
    public void jumpActivity(Class aClass, TreeMap<String, Object> paramMap, int requestCode, int flags) {
        startActivityForResult(paramIntent(aClass, paramMap).addFlags(flags == 0 ? Intent.FLAG_ACTIVITY_REORDER_TO_FRONT : flags), requestCode);
        if (mJumpAnim) overridePendingTransition(R.anim.tran_enter_go, R.anim.tran_exit_go);
    }


    /**
     * 带参数Activity
     *
     * @param aClass   Activity的Class
     * @param paramMap TreeMap
     * @param code     code
     * @param isFlags  true为flags,false为requestCode
     */
    public void jumpActivity(Class aClass, TreeMap<String, Object> paramMap, int code, boolean isFlags) {
        if (isFlags) {
            startActivity(paramIntent(aClass, paramMap).addFlags(code == 0 ? Intent.FLAG_ACTIVITY_REORDER_TO_FRONT : code));
        } else {
            startActivityForResult(paramIntent(aClass, paramMap).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), code);
        }
        if (mJumpAnim) overridePendingTransition(R.anim.tran_enter_go, R.anim.tran_exit_go);
    }

    /**
     * 带参数跳转Activity
     *
     * @param aClass
     * @param paramMap
     */
    public void jumpActivity(Class aClass, TreeMap<String, Object> paramMap) {
        startActivity(paramIntent(aClass, paramMap).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
        if (mJumpAnim) overridePendingTransition(R.anim.tran_enter_go, R.anim.tran_exit_go);
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
    public void jumpActivity(Class aClass, int requestCode, int flags) {
        startActivityForResult(new Intent(this, aClass).addFlags(flags == 0 ? Intent.FLAG_ACTIVITY_REORDER_TO_FRONT : flags), requestCode);
        if (mJumpAnim) overridePendingTransition(R.anim.tran_enter_go, R.anim.tran_exit_go);
    }


    /**
     * 跳转到Activity
     *
     * @param aClass  Activity的Class
     * @param code    code
     * @param isFlags true为flags,false为requestCode
     */
    public void jumpActivity(Class aClass, int code, boolean isFlags) {
        if (isFlags) {
            startActivity(new Intent(this, aClass).addFlags(code == 0 ? Intent.FLAG_ACTIVITY_REORDER_TO_FRONT : code));
        } else {
            startActivityForResult(new Intent(this, aClass).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT), code);
        }
        if (mJumpAnim) overridePendingTransition(R.anim.tran_enter_go, R.anim.tran_exit_go);
//        if (mJumpAnim) overridePendingTransition(R.anim.tran_enter_out, R.anim.tran_exit_out);
//        if (noBack.length > 0) {
//            finish();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mayRefreshData();
    }

    /**
     * 跳转到Activity
     *
     * @param aClass Activity的Class
     */
    public void jumpActivity(Class aClass) {
        startActivity(new Intent(this, aClass).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
        if (mJumpAnim) overridePendingTransition(R.anim.tran_enter_go, R.anim.tran_exit_go);
    }

    /**
     * @param aClass   要跳转到的Activity类
     * @param paramMap 带参数跳转到Activity类
     *                 传递基本数据类型：
     *                 TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
     *                 treeMap.put("money", mPersonalCenterBean.getNow_money());
     *                 mActivity.jumpActivity(MyWalletActivity.class, treeMap);
     *                 接收值：
     *                 String money=getIntent().getStringExtra("money");
     *                 <p>
     *                 传递引用对象类型：
     *                 被传递对象类需要实现implements Serializable
     *                 TreeMap<String, Object> treeMap = new TreeMap<>();
     *                 treeMap.put("mVideoBean", new BundleUtils().put("mVideoBean", (Serializable) videoBeans));
     *                 treeMap.put("mPlayIndex", index);
     *                 activity.jumpActivity(VideoPlayActivity.class, treeMap);
     *                 接收值：
     *                 mVideoBean = (List<VideoBean>) getIntent().getExtras().getSerializable("mVideoBean");
     *                 <p>
     *                 传递bitmap类型:
     *                 treeMap.put("courseImage", ImageUtils.bitmap2Bytes(detailsActivity.mCourseImage, Bitmap.CompressFormat.JPEG));
     *                 接收值：
     *                 Bitmap bitmap=getIntent().getByteArrayExtra("courseImage");
     * @return
     */
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
            if (value instanceof Long) {
                intent.putExtra(key, (Long) paramMap.get(key));
            }
            if (value instanceof Double) {
                intent.putExtra(key, (Double) paramMap.get(key));
            }
            if (value instanceof Float) {
                intent.putExtra(key, (Float) paramMap.get(key));
            }
            if (value instanceof Boolean) {
                intent.putExtra(key, (Boolean) paramMap.get(key));
            }
            if (value instanceof byte[]) {
                intent.putExtra(key, (byte[]) paramMap.get(key));
            }

            if (value instanceof Bundle) {
                intent.putExtras((Bundle) value);
            }
        }
        return intent;
    }

//    /**
//     * 获取提示框
//     *
//     * @param showValue WarningDialog
//     *                  记得调用.show()方法显示
//     */
//    public WarningDialog getWarningDialog(String title, String showValue) {
//        return WindowUtils.getWarningDialog(this, title, showValue);
//    }

    @Override
    public void finish() {
        super.finish();
        if (mJumpAnim) overridePendingTransition(R.anim.tran_enter_out, R.anim.tran_exit_out);
    }

    /**
     * 请求权限，如果已经拥有权限，则会回调onPermissionSuccess
     *
     * @param params 权限列表
     */
    public void requestPermission(String... params) {
        //判断权限
        if (!isPermission(params)) {//请求权限
            upPermission(1, params);
        } else {
            onPermissionSuccess(1);
        }
    }

    /**
     * 请求权限，如果已经拥有权限，则会回调onPermissionSuccess
     *
     * @param params      权限列表
     * @param requestCode 请求权限码
     */
    public void requestPermission(int requestCode, String... params) {
        //判断权限
        if (!isPermission(params)) {//请求权限
            upPermission(requestCode, params);
        } else {
            onPermissionSuccess(requestCode);
        }

    }


    /**
     * 获取接口请求状态
     *
     * @param eventMsg     消息
     * @param currentValid 是否只对当前发起人有效
     */
    protected void getRequestStatus(EventMsg eventMsg, boolean currentValid, SmartRefreshLayout... srlRefreshHead) {
        if (EventBusUtils.isSuccess(this, eventMsg, mOnlyMark, currentValid, this, srlRefreshHead) && eventMsg.isRefresh()) {
            EventBusUtils.post(new EventMsg().setType("DeleteRequest").setRequest(eventMsg.getRequest()));
        }
    }


    /**
     * 设置背景色和圆角
     *
     * @param color     背景色
     * @param radius    圆角
     * @param isPressed 是否增加按下特效，默认为true,为true以后必须注册点击事件才会生效，否则不会有按下效果
     * @return StateListDrawable 记得要调用.create()
     */
    public SelectorUtils.ShapeSelector getBackRadius(int color, int radius, boolean... isPressed) {
        SelectorUtils.ShapeSelector selector = SelectorUtils.newShapeSelector().setDefaultBgColor(color).setCornerRadius(new float[]{getDP(radius)});
        if (isPressed.length > 0 && !isPressed[0]) {
            return selector;
        }
        selector.setPressedBgColor(ColorUtils.getTranslucentColor(0.85f, color));
        return selector;
    }

    public void setMaxLength(int resId, int maxLength) {
        ((TextView) bindId(resId)).setFilters(new InputFilter[]{new InputFilter.LengthFilter(10) {
        }});
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

    public void setBackground(int viewId, Bitmap bitmap) {

        bindId(viewId).setBackground(new BitmapDrawable(getResources(), bitmap));
    }

    public void setImage(int viewId, int resId) {
        ((ImageView) bindId(viewId)).setImageResource(resId);
    }

    public void setBackground(View view, Bitmap bitmap) {
        view.setBackground(new BitmapDrawable(getResources(), bitmap));
    }

    //设置沉浸式状态栏，并把状态栏颜色改为透明色
    protected void setStatusBar() {
        WindowUtils.setStatusBar(this);
    }


    private InputMethodManager imm;


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        View view = bindId(R.id.iv_back);
        if (view != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
    }

    private void setStatusTitle() {
        WindowUtils.setStatusTitle(this, ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0));
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
    protected void upPermission(int requestCode, String... permissions) {
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
        initSupportFragmentManager();
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

    protected void bindClick(Integer... ids) {
        bindClick(new ListUtils<Integer>().add(ids));
    }

    public void bindClick(View view) {
        view.setOnClickListener(this);
    }

    protected void bindClickViews(List<View> views) {
        for (View view : views) {
            bindClick(view);
        }
    }


    public void bindClick(View view, List<Integer> integers, View.OnClickListener listener) {
        for (int viewId : integers) {
            view.findViewById(viewId).setOnClickListener(listener == null ? this : listener);
        }
    }

    public <V extends View> V bindId(int viewId) {
        return (V) findViewById(viewId);
    }

    public <V extends View> V bindId(View view, int viewId) {
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
        ((TextView) bindId(viewId)).setTextColor(color);
    }

    /**
     * 获取TextColor
     *
     * @param viewId viewId
     * @return color
     */
    public int getTextColor(int viewId) {
        return ((TextView) bindId(viewId)).getCurrentTextColor();
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
//        TextView inputView = bindId(viewId);
//        BuildInputAttribute inputAttribute = new BuildInputAttribute(this) {
//            @Override
//            protected void onSubmitValue(String value) {
//                inputView.setText(value);
//            }
//        }.textHint(textHint);
//        if (connect.length == 0) {
//            //关联显示TextView,不显示提交按钮，并且键盘右下角显示为完成
//            inputAttribute.connectTextView(inputView);
//            inputAttribute.submitVisibility(false);
//        } else {
//            inputAttribute.submitText("提交");
//        }
//        inputAttribute.inputValue(inputView.getText().toString());
//        return inputAttribute.createWindow();

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
//            inputDialog.setSubmitVisibility(false);
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
     * 显示/隐藏view
     *
     * @param isShow true/false
     */
    public void setVisibility(int viewId, boolean isShow) {
        bindId(viewId).setVisibility(isShow ? View.VISIBLE : View.GONE);
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
