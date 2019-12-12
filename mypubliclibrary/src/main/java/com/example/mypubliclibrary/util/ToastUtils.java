package com.example.mypubliclibrary.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.mypubliclibrary.widget.dialog.basic.AlarmDailog;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/9/24.
 */
public class ToastUtils {
    private static AlarmDailog alarmDialog;

    public static void showShortToast(Context context, String showMsg) {
        if (null != alarmDialog) {
            alarmDialog = null;
        }
        alarmDialog = new AlarmDailog(context);
        alarmDialog.setShowText(showMsg);
        alarmDialog.setDuration(Toast.LENGTH_SHORT);
        alarmDialog.show();

    }

    public static void showLongToast(Context context, String showMsg) {
        if (null != alarmDialog) {
            alarmDialog = null;
        }
        alarmDialog = new AlarmDailog(context);
        alarmDialog.setShowText(showMsg);
        alarmDialog.show();
    }

    public static void showMomentToast(final Activity activity, final Context context, final String showMsg) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (null == alarmDialog) {
                    alarmDialog = new AlarmDailog(context);
                    alarmDialog.setShowText(showMsg);
                    alarmDialog.setDuration(Toast.LENGTH_SHORT);
                    alarmDialog.show();
                } else {
                    alarmDialog.setShowText(showMsg);
                    alarmDialog.show();
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (null != alarmDialog) {
                            alarmDialog.cancel();
                        }
                    }
                }, 2000);
            }
        });
    }
}
