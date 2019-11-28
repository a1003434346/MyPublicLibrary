package com.example.mypubliclibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.mypubliclibrary.base.BasesActivity;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/16.
 */
public class ImageUtils {

    public interface UrlToBitmap {
        void onSuccess(Bitmap bitmap);
    }

    public interface ImageColor {
        void onSuccess(int color);
    }


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
     * @param imageView imageView
     * @param resId     resId
     */
    public static void setImageDrawable(BasesActivity activity, ImageView imageView, int resId) {
        imageView.setImageResource(resId);
    }


    //判断图片是否使用某一个图片
    public static boolean isEqualsDrawable(BasesActivity activity, ImageView imageView, int drawableId) {
        return imageView.getDrawable().getConstantState().equals(activity.getResources().getDrawable(drawableId).getConstantState());
    }

    /**
     * 设置选中和未选中的图片
     *
     * @param activity         activity
     * @param view             view
     * @param checkDrawable    选中的图片
     * @param notCheckDrawable 未选中的图片
     *                         请求示例：
     *                         ImageUtils.setCheckImage(this, R.id.iv_drag_play, R.drawable.drag_play, R.drawable.drag_pause);
     * @return 当前选择后是否为选中状态
     */
    public static boolean setCheckImage(BasesActivity activity, View view, int checkDrawable, int notCheckDrawable) {
        //是否选中
        boolean isCheck;
        ImageView imageView = (ImageView) view;
        if (ImageUtils.isEqualsDrawable(activity, imageView, checkDrawable)) {
            ImageUtils.setImageDrawable(activity, imageView, notCheckDrawable);
            isCheck = false;
        } else {
            ImageUtils.setImageDrawable(activity, imageView, checkDrawable);
            isCheck = true;
        }
        return isCheck;
    }

    /**
     * 获取图片里面比较活跃的颜色
     *
     * @param bitmap bitmap
     */
    public static void getImageColor(Bitmap bitmap, ImageColor imageColor) {
        Palette.Builder builder = Palette.from(bitmap);
        //异步任务
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取主颜色
                imageColor.onSuccess(ColorUtils.setColorBurn(palette.getDominantSwatch().getRgb(), 1.0f));
            }
        });

    }

    /**
     * 将网络图片转换为Bitmap
     *
     * @param imgUrl      图片Url
     * @param context     context
     * @param width       返回图片的宽度
     * @param height      返回图片的高度 T
     * @param urlToBitmap
     */
    public static void urlToBitmap(final String imgUrl, final Context context, int width, int height, UrlToBitmap urlToBitmap) {

        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(context)
                            .asBitmap()
                            .load(imgUrl)
                            //360*480,原始大小设置为Target.SIZE_ORIGINAL
                            .submit(width, height).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                urlToBitmap.onSuccess(bitmap);
            }
        }.execute();
    }

    /**
     * 将网络图片转换为Bitmap
     *
     * @param imgUrl      图片Url
     * @param context     context
     * @param urlToBitmap
     */
    public static void urlToBitmap(final String imgUrl, final Context context, UrlToBitmap urlToBitmap) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(context)
                            .asBitmap()
                            .load(imgUrl)
                            //360*480,原始大小设置为Target.SIZE_ORIGINAL
                            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                urlToBitmap.onSuccess(bitmap);
            }
        }.execute();
    }

}
