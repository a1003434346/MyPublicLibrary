package com.example.mypubliclibrary.util;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/25.
 */
public class ViewUtils {

    /**
     * 单选模式更新选择状态
     *
     * @param view               布局容器,null为当前类的全局布局文件
     * @param checkId            想要选中的Id
     * @param viewContainer      需要单选的控件容器
     * @param checkColor         选中的颜色
     * @param notCheckColor      未选中的文本颜色
     * @param checkBackground    选中的BackGround
     * @param notCheckBackground 未选中的BackGround
     * @return 选择的索引(对应容器里面的顺序, 下标从0开始)
     */
    public static int checkType(View view, int checkId, List<Integer> viewContainer, int checkColor, int notCheckColor, Drawable checkBackground, Drawable notCheckBackground) {
        boolean isCheckId = false;
        boolean updateColor = false;
        boolean updateBackground = false;
        int checkIndex = -1;
        for (int viewId : viewContainer) {
            checkIndex++;
            if (checkId == viewId) {
                isCheckId = true;
                if (checkColor != 0) updateColor = true;
                if (checkBackground != null) updateBackground = true;
                break;
            }
        }
        //对选中的设置选中状态,未选中的还原初始状态
        if (isCheckId) {
            for (int viewId : viewContainer) {
                if (updateColor)
                    setTvColor(view, viewId, checkId != viewId ? notCheckColor : checkColor);
                if (updateBackground)
                    setBackground(view, viewId, checkId != viewId ? notCheckBackground : checkBackground);
            }
        }
        return checkIndex;
    }

    /**
     * 单选模式更新选择状态
     *
     * @param views              TextView容器
     * @param checkId            想要选中的ID
     * @param checkColor         选中的颜色
     * @param notCheckColor      未选中的文本颜色
     * @param checkBackground    选中的BackGround
     * @param notCheckBackground 未选中的BackGround
     * @return 选择的索引(对应容器里面的顺序)
     */
    public static int checkTextView(List<TextView> views, int checkId, int checkColor, int notCheckColor, Drawable checkBackground, Drawable notCheckBackground) {
        boolean isCheckId = false;
        boolean updateColor = false;
        boolean updateBackground = false;
        int checkIndex = -1;
        for (TextView view : views) {
            checkIndex++;
            if (checkId == view.getId()) {
                isCheckId = true;
                if (checkColor != 0) updateColor = true;
                if (checkBackground != null) updateBackground = true;
                break;
            }
        }
        //对选中的设置选中状态,未选中的还原初始状态
        if (isCheckId) {
            for (TextView view : views) {
                if (updateColor)
                    setTvColor(view, checkId == view.getId() ? checkColor : notCheckColor);
                if (updateBackground)
                    setBackground(view, checkId == view.getId() ? checkBackground : notCheckBackground);
            }
        }
        return checkIndex;
    }


    /**
     * 设置TextView的文字颜色
     *
     * @param view   哪一个布局容器里的view
     * @param viewId viewId
     * @param color  设置的颜色
     */
    public static void setTvColor(View view, int viewId, int color) {
        ((TextView) view.findViewById(viewId)).setTextColor(view.getResources().getColor(color));
    }

    /**
     * 给view设置margin间距
     *
     * @param v view
     * @param l left
     * @param t top
     * @param r right
     * @param b bottom
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.setLayoutParams(p);
        }
    }

    /**
     * 设置TextView的文本Color
     *
     * @param view  哪一个TextView
     * @param color 设置的颜色
     */
    public static void setTvColor(TextView view, int color) {
        view.setTextColor(view.getResources().getColor(color));
    }


    /**
     * 设置view的背景颜色
     *
     * @param view     哪一个布局容器里的view
     * @param viewId   viewId
     * @param drawable 设置的drawable
     */
    public static void setBackground(View view, int viewId, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.findViewById(viewId).setBackground(drawable);
        }
    }

    /**
     * 设置view的背景颜色
     *
     * @param view     哪一个view
     * @param drawable 设置的drawable
     */
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        }
    }

    /**
     * 返回简短ViewId，用来区分返回Code唯一码
     *
     * @param id R.id.tv_add_teacher_info
     * @return viewId
     */
    public static int getViewIdCode(int id) {
        return Integer.parseInt((id + "").substring(6));
    }
}
