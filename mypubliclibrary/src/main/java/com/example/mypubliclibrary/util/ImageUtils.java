package com.example.mypubliclibrary.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.palette.graphics.Palette;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.mypubliclibrary.R;
import com.example.mypubliclibrary.base.BasesActivity;
import com.example.mypubliclibrary.view.activity.PreviewPhotoActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;

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
     * @param imageView      imageView
     * @param roundingRadius 圆角的度数
     */
    public static <T extends BitmapTransformation> void setImageCorners(ImageView imageView, Object path, T type, int roundingRadius) {
        //设置图片为圆角
        RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);//数字为圆角度数
        RequestOptions coverRequestOptions = new RequestOptions()
                .transforms(type, roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//磁盘缓存
                .skipMemoryCache(false);//跳过内存缓存
        //Glide 加载图片简单用法
        Glide.with(imageView).load(path)
                .apply(coverRequestOptions).into(imageView);
    }

    /**
     * 设置图片为圆形
     *
     * @param imageView imageView
     * @param path      path
     */
    public static void setImageCrop(ImageView imageView, Object path) {
        Glide.with(imageView).load(path)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    /**
     * 预览照片
     *
     * @param objects Photos
     */
    public static void previewPhoto(Context context, List<Object> objects, int... index) {
        BasesActivity activity = (BasesActivity) context;
        TreeMap<String, Object> photos = new TreeMap<>();
        if (index.length > 0)
            photos.put("photoPosition", index[0]);
        photos.put("photos", new BundleUtils().put("photos", (Serializable) objects));
        //设置进出动画
        activity.jumpActivity(PreviewPhotoActivity.class, photos);
//        activity.overridePendingTransition(R.anim.photo_enter_go, R.anim.photo_enter_out);
    }


    /**
     * 设置显示图片
     *
     * @param imageView imageView
     * @param path      path
     */
    public static void setImage(ImageView imageView, Object path) {
        Glide.with(imageView).load(path)
                .into(imageView);
    }

    /**
     * 设置Image的Drawable
     *
     * @param imageView imageView
     * @param resId     赋值R.drawable.XX
     */
    public static void setImageDrawable(ImageView imageView, int resId) {
        imageView.setImageResource(resId);
    }

    /**
     * 保存图片到指定目录
     *
     * @param context       context
     * @param saveDirectory 目录
     * @param bmp           bitmap
     * @return true/false
     * 调用示例： saveImageToDirectory(getContext(), "WaterAcademy/Image", bitmap);
     */
    public static boolean saveImageToDirectory(Context context, String saveDirectory, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + saveDirectory;
        File appDir = new File(storePath);
        if (FileUtils.createOrExistsDir(appDir)) {
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                //通过io流的方式来压缩保存图片
                boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                fos.flush();
                fos.close();
                //把文件插入到系统图库
                //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
                //保存图片后发送广播通知更新数据库
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                return isSuccess;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 设置Image的Bitmap
     *
     * @param imageView imageView
     * @param bitmap    bitmap
     */
    public static void setImageBitmap(BasesActivity activity, ImageView imageView, Bitmap bitmap) {
        imageView.setBackground(new BitmapDrawable(activity.getResources(), bitmap));
    }

    /**
     * 返回圆形图片
     *
     * @param src The source of bitmap.
     * @return the round bitmap
     */
    public static Bitmap toRound(final Bitmap src) {
        return toRound(src, 0, 0, false);
    }


    /**
     * Return the round bitmap.
     *
     * @param src         The source of bitmap.
     * @param recycle     True to recycle the source of bitmap, false otherwise.
     * @param borderSize  The size of border.
     * @param borderColor The color of border.
     * @return the round bitmap
     */
    public static Bitmap toRound(final Bitmap src,
                                 @IntRange(from = 0) int borderSize,
                                 @ColorInt int borderColor,
                                 final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int size = Math.min(width, height);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        float center = size / 2f;
        RectF rectF = new RectF(0, 0, width, height);
        rectF.inset((width - size) / 2f, (height - size) / 2f);
        Matrix matrix = new Matrix();
        matrix.setTranslate(rectF.left, rectF.top);
        if (width != height) {
            matrix.preScale((float) size / width, (float) size / height);
        }
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        Canvas canvas = new Canvas(ret);
        canvas.drawRoundRect(rectF, center, center, paint);
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            float radius = center - borderSize / 2f;
            canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        }
        if (recycle && !src.isRecycled() && ret != src) src.recycle();
        return ret;
    }

    /**
     * 将Bitmap转为圆角图片
     *
     * @param src    The source of bitmap.
     * @param radius The radius of corner.
     * @return the round corner bitmap
     */
    public static Bitmap toRoundCorner(final Bitmap src, final float radius) {
        return toRoundCorner(src, radius, 0, 0, false);
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(Utils.getApp().getResources(), bitmap);
    }

    /**
     * 返回圆角图片
     *
     * @param src         The source of bitmap.
     * @param radius      The radius of corner.
     * @param borderSize  The size of border.
     * @param borderColor The color of border.
     * @param recycle     True to recycle the source of bitmap, false otherwise.
     * @return the round corner bitmap
     */
    public static Bitmap toRoundCorner(final Bitmap src,
                                       final float radius,
                                       @IntRange(from = 0) int borderSize,
                                       @ColorInt int borderColor,
                                       final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        Canvas canvas = new Canvas(ret);
        RectF rectF = new RectF(0, 0, width, height);
        float halfBorderSize = borderSize / 2f;
        rectF.inset(halfBorderSize, halfBorderSize);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawRoundRect(rectF, radius, radius, paint);
        }
        if (recycle && !src.isRecycled() && ret != src) src.recycle();
        return ret;
    }

    private static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
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
            ImageUtils.setImageDrawable(imageView, notCheckDrawable);
            isCheck = false;
        } else {
            ImageUtils.setImageDrawable(imageView, checkDrawable);
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
     * Bytes to bitmap.
     *
     * @param bytes The bytes.
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return (bytes == null || bytes.length == 0)
                ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Bytes to drawable.
     *
     * @param bytes The bytes.
     * @return drawable
     */
    public static Drawable bytes2Drawable(final byte[] bytes) {
        return bitmap2Drawable(bytes2Bitmap(bytes));
    }

    /**
     * Bitmap to bytes.
     *
     * @param bitmap The bitmap.
     * @param format The format of bitmap. Bitmap.CompressFormat.JPEG
     * @return bytes
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 将网络图片转换为Bitmap
     *
     * @param imgUrl      图片Url
     * @param context     context
     * @param width       返回图片的宽度  原始大小设置为Target.SIZE_ORIGINAL
     * @param height      返回图片的高度  原始大小设置为Target.SIZE_ORIGINAL
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

    /**
     * 获取Bitmap,已经获取过，从缓存的bitmaps获取
     *
     * @param context context
     * @param bitmaps bitmaps
     * @param urls    urls
     * @param url     url
     * @return Bitmap
     */
    public static Bitmap urlToBitmapCaching(Context context, List<Bitmap> bitmaps, List<String> urls, String url) {
        for (int i = 0; i < urls.size(); i++) {
            if (urls.get(i).equals(url)) {
                return bitmaps.get(i);
            }
        }
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    //360*480,原始大小设置为Target.SIZE_ORIGINAL
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            if (bitmap != null) bitmaps.add(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
