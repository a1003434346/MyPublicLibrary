package com.example.mypubliclibrary.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mypubliclibrary.base.BasesActivity;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/16.
 */
public class ImageUtils {

    /**
     * 设置图片为圆角
     *
     * @param context        context
     * @param imageView      imageView
     * @param roundingRadius 圆角的度数
     */
    public static void setImageCorners(Context context, ImageView imageView, Object path, int roundingRadius) {
        //设置图片为圆角
        RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);//数字为圆角度数
        RequestOptions coverRequestOptions = new RequestOptions()
                .transforms(new CenterCrop(), roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                .skipMemoryCache(false);//跳过内存缓存
        //Glide 加载图片简单用法
        Glide.with(context).load(path)
                .apply(coverRequestOptions).into(imageView);
    }

    /**
     * 设置图片为圆形
     *
     * @param context   context
     * @param imageView imageView
     * @param path      path
     */
    public static void setImageCrop(Context context, ImageView imageView, Object path) {
        Glide.with(context).load(path)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    /**
     * 设置显示图片
     *
     * @param context   context
     * @param imageView imageView
     * @param path      path
     */
    public static void setImage(Context context, ImageView imageView, Object path) {
        Glide.with(context).load(path)
                .into(imageView);
    }

    /**
     * @param imageView imageView
     * @param resId     赋值R.drawable.XX
     */
    public static void setImageDrawable(ImageView imageView, int resId) {
        imageView.setImageResource(resId);
    }

    /**
     * 设置Image的Drawable
     *
     * @param viewId viewId
     * @param resId  resId
     */
    public static void setImageDrawable(BasesActivity activity, int viewId, int resId) {
        ((ImageView) activity.bindId(viewId)).setImageResource(resId);
    }


    //判断图片是否使用某一个图片
    public static boolean isEqualsDrawable(BasesActivity activity, int imageViewId, int drawableId) {
        return ((ImageView) activity.bindId(imageViewId)).getDrawable().getConstantState().equals(activity.getResources().getDrawable(drawableId).getConstantState());
    }

    /**
     * 设置选中和未选中的图片
     *
     * @param activity         activity
     * @param viewId           viewId
     * @param checkDrawable    选中的图片
     * @param notCheckDrawable 未选中的图片
     *                         请求示例：
     *                         ImageUtils.setCheckImage(this, R.id.iv_drag_play, R.drawable.drag_play, R.drawable.drag_pause);
     * @return 当前选择后是否为选中状态
     */
    public static boolean setCheckImage(BasesActivity activity, int viewId, int checkDrawable, int notCheckDrawable) {
        //是否选中
        boolean isCheck;
        if (ImageUtils.isEqualsDrawable(activity, viewId, checkDrawable)) {
            ImageUtils.setImageDrawable(activity, viewId, notCheckDrawable);
            isCheck = false;
        } else {
            ImageUtils.setImageDrawable(activity, viewId, checkDrawable);
            isCheck = true;
        }
        return isCheck;
    }

}
