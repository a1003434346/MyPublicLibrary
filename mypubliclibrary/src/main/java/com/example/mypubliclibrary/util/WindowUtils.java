package com.example.mypubliclibrary.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.PowerManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.widget.dialog.basic.WarningDialog;
import com.example.mypubliclibrary.widget.interfaces.DataInterface;
import com.example.mypubliclibrary.widget.interfaces.DateInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/30.
 */
public class WindowUtils {

    /**
     * 设置背景阴影(蒙色)
     *
     * @param mContext context
     * @param bgAlpha  不传值为清除灰蒙背景
     */
    public static void setBackgroundAlpha(Context mContext, float... bgAlpha) {
        if (bgAlpha.length == 0) {
            clearDim((Activity) mContext);
        } else {
            applyDim((Activity) mContext, bgAlpha[0]);
        }
    }

    /**
     * 获取PopupWindow
     *
     * @param context context
     * @param view    View  popView = LayoutInflater.from(context).inflate(R.layout.dialog_comment_input, null);
     * @param width   width  LinearLayout.LayoutParams.MATCH_PARENT、 LinearLayout.LayoutParams.WRAP_CONTENT、int
     * @param height  height LinearLayout.LayoutParams.MATCH_PARENT、 LinearLayout.LayoutParams.WRAP_CONTENT、int
     * @return PopupWindow   记得调用   show显示窗口   popWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 0);
     * 显示蒙色  WindowUtils.setBackgroundAlpha(context, 0.5f);
     */
    public static PopupWindow getPopupWindow(final Context context, View view, int width, int height) {
        final PopupWindow popupWindow = new PopupWindow(view, width, height, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //不设置动画,默认为此动画
        popupWindow.setAnimationStyle(R.style.popWindowAnimation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(context);
            }
        });
        return popupWindow;
    }

    /**
     * 给Title设置状态栏的高度为间距，以及是否把状态栏的颜色设置为Title的背景色
     *
     * @param context  context
     * @param rootView 窗口的根布局
     *                 Activity传入((ViewGroup)findViewById(android.R.id.content)).getChildAt(0)
     *                 Fragment传入 myView
     *                 调用示例
     *                 WindowUtils.setStatusTitle(getContext(), bindId(R.id.ctl_title));
     */
    public static void setStatusTitle(Context context, View rootView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View titleView = rootView.findViewById(R.id.ctl_title);
            if (titleView != null) {
                int statusHeight = getStatusBarHeight(context);
                ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
                //设置间距
                setLayoutMargin(layoutParams, 0, statusHeight, 0, 0);
                //设置状态栏的背景色
                ColorDrawable colorDrawable = (ColorDrawable) titleView.getBackground();
                if (colorDrawable != null) {
                    //是否有添加状态栏View
                    View statusView = rootView.findViewById(R.id.status_back);
                    if (statusView == null) {
                        statusView = new View(context);
                        statusView.setId(R.id.status_back);
                        //添加状态栏View
                        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, 0);
                        params.topToTop = ConstraintSet.PARENT_ID;
                        params.bottomToTop = titleView.getId();
                        if (rootView instanceof ConstraintLayout) {
                            ((ConstraintLayout) rootView).addView(statusView, params);
                        }
                    }
                    int titleColor = colorDrawable.getColor();
                    //改变状态栏电量时间颜色
                    setStatusBarColor((Activity) context, titleColor != 0 && ColorUtils.isLightColor(titleColor));
                    //设置状态栏背景色为标题背景色
                    statusView.setBackgroundColor(titleColor);
                    //设置状态栏背景色为标题背景色
//                        ((Activity) context).getWindow().setStatusBarColor(colorDrawable.getColor());
                }
            } else {
                //设置状态栏背景色为透明色
                ((Activity) context).getWindow().setStatusBarColor(Color.TRANSPARENT);
                setStatusBarColor((Activity) context, false);
            }
        }
    }


    /**
     * 设置间距
     *
     * @param layoutParams LayoutParams
     * @param left         left
     * @param top          top
     * @param right        right
     * @param bottom       bottom
     */
    public static void setLayoutMargin(ViewGroup.LayoutParams layoutParams, int left, int top, int right, int bottom) {
        //设置间距
        if (layoutParams instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams) layoutParams).setMargins(left, top, right, bottom);
        }
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) layoutParams).setMargins(left, top, right, bottom);
        }
        if (layoutParams instanceof FrameLayout.LayoutParams) {
            ((FrameLayout.LayoutParams) layoutParams).setMargins(left, top, right, bottom);
        }
        if (layoutParams instanceof RelativeLayout.LayoutParams) {
            ((RelativeLayout.LayoutParams) layoutParams).setMargins(left, top, right, bottom);
        }
    }

    /**
     * 改变状态栏电量时间颜色
     *
     * @param activity activity
     * @param isWhite  是否设置为白色
     */
    public static void setStatusBarColor(Activity activity, boolean isWhite) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (!isWhite && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                option += View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(option);
        }
    }

    /**
     * 设置沉浸式状态栏，并把状态栏颜色改为透明色
     */
    public static void setStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                option += View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                //改成白色是SYSTEM_UI_FLAG_VISIBLE
            }
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * APP是否运行在前台
     *
     * @param ctx context
     * @return true/false
     */
    public static boolean isAppRunningForeground(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();
            if (runningProcesses == null) {
                return false;
            }
            final String packageName = ctx.getPackageName();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        } else {
            try {
                List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
                if (tasks == null || tasks.size() < 1) {
                    return false;
                }
                boolean b = ctx.getPackageName().equalsIgnoreCase(tasks.get(0).baseActivity.getPackageName());
                return b;
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取日期选择窗口
     *
     * @param context context
     * @param getDate onDateSetListener 回调的结果分别为年、月、日
     * @param date    默认日期,分别为年、月、日，不传值为当天的年月日
     * @return DatePickerDialog 返回后记得调用show
     */
    public static DatePickerDialog getDatePickerDialog(Context context, final DateInterface.GetDate getDate, int... date) {
        if (date.length > 0) {
            return new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    getDate.getDate(DateUtils.timeFormatString(i), DateUtils.timeFormatString(i1 + 1), DateUtils.timeFormatString(i2));
                }
            }, date[0], date[1] - 1, date[2]);
        }
        return new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                getDate.getDate(DateUtils.timeFormatString(i), DateUtils.timeFormatString(i1 + 1), DateUtils.timeFormatString(i2));
            }
        }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDatDay());
    }


//    /**
//     * 显示警示弹框
//     *
//     * @param context   context
//     * @param showValue 显示内容
//     *                  button1默认内容返回
//     *                  button2默认内容确定
//     * @return WarningDialog
//     * 如果设置居中的按钮显示，将自动隐藏左右按钮。记得调用.show()方法显示
//     */
//    public static WarningDialog getWarningDialog(Context context, String title, String showValue) {
//        return new WarningDialog(context, showValue).setTitle(title);
//    }

//    /**
//     * 获取选择图片和视频的窗口
//     *
//     * @param activity     activity
//     * @param selectImage  是否可以选择image
//     * @param selectVideo  是否可以选择video
//     * @param resultCode   回调Code,可选用PictureConfig.CHOOSE_REQUEST里面的常量
//     * @param maxSelectNum 最大选择数量
//     *                     需要再调用的activity里重写 onActivityResult方法，返回结果为resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST
//     *                     对于该框架resultCode要判断等于-1,否则只判断requestCode取消选择也会进去
//     *                     返回值为 List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
//     */
//    public static void getSelectImageVideo(Activity activity, boolean selectImage, boolean selectVideo, int resultCode, int maxSelectNum) {
//        int type = 0;
//        if (selectImage && !selectVideo) type = 1;
//        if (!selectImage && selectVideo) type = 2;
//        PictureSelector.create(activity).openGallery(type)
//                .theme(R.style.picture_QQ_style)
//                .isCamera(false)
//                .maxSelectNum(maxSelectNum).selectionMode(PictureConfig.MULTIPLE).previewImage(selectImage).previewVideo(selectVideo)
//                .forResult(resultCode);
//    }

    /**
     * 获取时间选择窗口
     *
     * @param context     context
     * @param getTime     回调的结果分别为字符串的小时、分
     * @param defaultTime 默认时间,不传默认为12点
     * @return TimePickerDialog 返回后记得调用show
     */
    public static TimePickerDialog getTimePickerDialog(Context context, final DateInterface.GetTime getTime, int... defaultTime) {
        if (defaultTime.length > 0)
            return new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    getTime.getTime(DateUtils.timeFormatString(i), DateUtils.timeFormatString(i1));
                }
            }, defaultTime[0], defaultTime[1], true);

        return new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                getTime.getTime(DateUtils.timeFormatString(i), DateUtils.timeFormatString(i1));
            }
        }, 12, 0, true);
    }

    /**
     * 屏幕是否是亮的
     *
     * @param activity activity
     * @return true为亮，（onStart里判断即为解锁），false为暗（onStop里判断即为锁屏）
     */
    public static boolean isWindowLight(BasesActivity activity) {
        return ((PowerManager) activity.getSystemService(Context.POWER_SERVICE)).isScreenOn();
    }

//    /**
//     * 打开照片
//     *
//     * @param objects Photos
//     */
//    public void openPhoto(Context context, List<Object> objects, int... index) {
//        BaseActivity activity = (BaseActivity) context;
//        TreeMap<String, Object> photos = new TreeMap<>();
//        photos.put("photoPosition", index[0]);
//        photos.put("photos", new BundleUtils().put("photos", (Serializable) objects));
//        //设置进出动画
//        activity.jumpActivity(DynamicPreviewActivity.class, photos);
//        activity.overridePendingTransition(R.anim.dynamic_preview_enter, R.anim.dynamic_preview_exit);
//    }

    /**
     * 多选框控件 原生多选的选择控件,不是很友好,暂时用,以后替换掉
     *
     * @param context context
     * @param title   title
     * @param values  value
     * @param getList getList
     * @return AlertDialog
     */
    public static AlertDialog.Builder getCheckBoxWindow(Context context, String title, final List<String> values, final DataInterface.GetList<String> getList) {
        final ArrayList<String> yourChoices = new ArrayList<>();
        String[] strings = new String[values.size()];
        values.toArray(strings);
        AlertDialog.Builder multiChoiceDialog =
                new AlertDialog.Builder(context);
        multiChoiceDialog.setTitle(title);
        multiChoiceDialog.setMultiChoiceItems(strings, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            yourChoices.add(values.get(which));
                        } else {
                            yourChoices.remove(values.get(which));
                        }
                    }
                });
        multiChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getList.getList(yourChoices);
                    }
                });

        return multiChoiceDialog;
    }


    private static void clearDim(Activity activity) {
        if (Build.VERSION.SDK_INT >= 18) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            ViewGroupOverlay overlay = parent.getOverlay();
            overlay.clear();
        }
    }

    private static void applyDim(Activity activity, float bgAlpha) {
        if (Build.VERSION.SDK_INT >= 18) {
            ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            Drawable dim = new ColorDrawable(-16777216);
            dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
            dim.setAlpha((int) (255.0F * bgAlpha));
            ViewGroupOverlay overlay = parent.getOverlay();
            overlay.clear();
            overlay.add(dim);
        }
    }


    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 根据手机的分辨率把数字转换为dp单位
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 给TextView设置字体大小
     *
     * @param textView textView
     * @param value    字体大小
     */
    public static void setTextSP(TextView textView, float value) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, value);
    }

    //    private String strJson;
//    private ChineseRegionBean chineseRegion;
//    //地区二级选择是否增加全部选项
//    private boolean initAllOption = true;
//    private SelectView selectViewAddress;
//    //地区选择器
//    public SelectView selectRegion;

//    /**
//     * 选择城市地区
//     *
//     * @param myContext        context
//     * @param isShoInland      是否显示国内选项
//     * @param pAllOption       是否显示省内全部选项
//     * @param selectDoneRegion 回调接口
//     * @return SelectView
//     * 记得调用.show()显示
//     */
//    public SelectView getCityRegion(Context myContext, boolean isShoInland, boolean pAllOption, ProjectInterface.SelectDoneRegion selectDoneRegion) {
//        if (DataConfig.getInstance().chineseRegion == null)
//            DataConfig.getInstance().chineseRegion = BeanUtils.jsonFromBean(AssetsUtils.getJson(myContext, "Address.json"), ChineseRegionBean.class);
//        List<ChineseRegionBean.Province> list = ListUtils.deepCopy(DataConfig.getInstance().chineseRegion.getData().getList());
//        //是否显示国内选项
//        if (isShoInland) {
//            ChineseRegionBean.Province province = DataConfig.getInstance().chineseRegion.new Province();
//            province.setId("0");
//            province.setName("国内");
//            ChineseRegionBean.City city = DataConfig.getInstance().chineseRegion.new City();
//            city.setId("0");
//            city.setName("国内");
//            province.setCity(new ArrayList<>());
//            province.getCity().add(0, city);
//            list.add(0, province);
//        }
//        //是否显示省内全部选项
//        if (pAllOption) {
//            ChineseRegionBean.City city = DataConfig.getInstance().chineseRegion.new City();
//            city.setId("0");
//            city.setName("全部");
//            for (ChineseRegionBean.Province province : list) {
//                if (!province.getName().equals("国内"))
//                    province.getCity().add(0, city);
//            }
//        }
//        return new SelectView(myContext) {
//            @Override
//            protected List<ChineseRegionBean.Province> getDataListOne() {
//                return list;
//            }
//
//            @Override
//            public List<ChineseRegionBean.City> getDataListTwo() {
//                return list.get(selectIndexOne).getCity();
//            }
////不显示区
////                @Override
////                public List<ChineseRegionBean.Town> getDataListThree() {
////                    return chineseRegion.getData().getList().get(selectIndexOne).getCity().get(selectIndexTwo).getTown();
////                }
//
//            @Override
//            public void onChanged(SelectWheelView selectView, int lastIndex, int selectIndex) {
//                super.onChanged(selectView, lastIndex, selectIndex);
//                if (selectView == wlvSelectOne) {
//                    dataListTwo = list.get(selectIndexOne).getCity();
//                    updateAdapterTwo();
//                }
//                //不显示区
////                    if (selectView == wlvSelectOne || selectView == wlvSelectTwo) {
////                        dataListThree = chineseRegion.getData().getList().get(selectIndexOne).getCity().get(selectIndexTwo).getTown();
////                        updateAdapterThree();
////                    }
//            }
//
//            @Override
//            protected void selectAchieve() {
//                selectDoneRegion.selectDoneRegion((ChineseRegionBean.Province) selectValueOne, (ChineseRegionBean.City) selectValueTwo);
//            }
//        }.setLongData().setItemResource(R.layout.dialog_address_item).setCyclic().setTitle("选择地区").show();
//    }


}
