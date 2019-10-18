package com.example.mypubliclibrary.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

public class SelectorUtils {
    /**
     * 调用示例
     * SelectorUtils.newShapeSelector().setDefaultBgColor(getColor(R.color.colorTextHint))
     * .setCornerRadius(new float[]{getDP(180)}).create()
     */
    public static ShapeSelector newShapeSelector() {
        return new SelectorUtils.ShapeSelector();
    }

    public static final class ShapeSelector {
        //默认背景颜色
        private int mDefaultBgColor;
        //圆角
        private float[] mCornerRadius;
        //形状
        private int mShape;
        //描边宽度
        private int mStrokeWidth;
        //默认描边颜色
        private int mDefaultStrokeColor;
        //按下背景颜色
        private int mPressedBgColor;
        //按下描边颜色
        private int mPressedStrokeColor;
        //设置禁用状态的背景颜色
        private int mDisabledBgColor;
        //设置禁用描边的颜色
        private int mDisabledStrokeColor;
        //选中背景颜色
        private int mSelectedBgColor;
        //选中描边颜色
        private int mSelectedStrokeColor;
        //设置聚焦时的背景色
        private int mFocusedBgColor;
        //设置聚焦时的描边色
        private int mFocusedStrokeColor;


        //构造方法，初始化参数
        private ShapeSelector() {
            mDefaultBgColor = Color.TRANSPARENT;
            //默认为矩形
            mShape = GradientDrawable.RECTANGLE;
            //描边宽度默认为0
            mStrokeWidth = Color.TRANSPARENT;
            //默认描边颜色
            mDefaultStrokeColor = Color.TRANSPARENT;
            //按下背景颜色
            mPressedBgColor = Color.TRANSPARENT;
            //默认按下描颜色
            mPressedStrokeColor = Color.TRANSPARENT;
            //默认禁用背景颜色
            mDisabledBgColor = Color.TRANSPARENT;
            //默认禁用描边的颜色
            mDisabledStrokeColor = Color.TRANSPARENT;
            //选中背景颜色
            mSelectedBgColor = Color.TRANSPARENT;
            //选中描边颜色
            mSelectedStrokeColor = Color.TRANSPARENT;
            //聚焦时的背景色
            mFocusedBgColor = Color.TRANSPARENT;
            //聚焦时的描边色
            mFocusedStrokeColor = Color.TRANSPARENT;
            //初始化默认圆角
            mCornerRadius[0] = 0;
        }


        public SelectorUtils.ShapeSelector setDefaultBgColor(@ColorInt int color) {
            mDefaultBgColor = color;
            if (mDisabledBgColor == Color.TRANSPARENT)
                mDisabledBgColor = color;
            if (mPressedBgColor == Color.TRANSPARENT)
                mPressedBgColor = color;
            if (mSelectedBgColor == Color.TRANSPARENT)
                mSelectedBgColor = color;
            if (mFocusedBgColor == Color.TRANSPARENT)
                mFocusedBgColor = color;
            return this;
        }

        /**
         * 圆角
         *
         * @param radius 一个长度代表四个角度一致,1个以上长度设置四个角半径：
         *               1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
         *               例如 new float[]{16, 16, 16, 16, 0, 0, 0, 0}
         * @return
         */
        public SelectorUtils.ShapeSelector setCornerRadius(@Dimension float[] radius) {
            mCornerRadius = radius;
            return this;
        }

        /**
         * 创建ShapeSelector
         *
         * @return
         */
        public StateListDrawable create() {
            //声明选择器
            StateListDrawable selector = new StateListDrawable();
            //如果设置了选中背景色，或者选中描边色
            if (setSelectedBgColor()) {
                GradientDrawable selectedShape = getItemShape(mShape, mCornerRadius,
                        mSelectedBgColor, mStrokeWidth, mSelectedStrokeColor);
                //设置选中状态
                selector.addState(new int[]{android.R.attr.state_selected}, selectedShape);
            }

            //如果设置了聚焦时的背景色，或者聚焦时的描边色
            if (setFocusedBgColor()) {
                GradientDrawable focusedShape = getItemShape(mShape, mCornerRadius,
                        mFocusedBgColor, mStrokeWidth, mFocusedStrokeColor);
                selector.addState(new int[]{android.R.attr.state_focused}, focusedShape);
            }


            //如果设置了禁用背景色，或者禁用描边色
            if (setDisabledBgColor()) {
                GradientDrawable disabledShape = getItemShape(mShape, mCornerRadius,
                        mDisabledBgColor, mStrokeWidth, mDisabledStrokeColor);
                //设置禁用背景色状态
                selector.addState(new int[]{-android.R.attr.state_enabled}, disabledShape);
            }

            //如果设置了按下背景色或者设置了按下描边背景色
            if (setPressedBgColor()) {
                GradientDrawable pressedShape = getItemShape(mShape, mCornerRadius,
                        mPressedBgColor, mStrokeWidth, mPressedStrokeColor);
                //设置按下改变背景色状态
                selector.addState(new int[]{android.R.attr.state_pressed}, pressedShape);
            }

            GradientDrawable defaultShape = getItemShape(mShape, mCornerRadius,
                    mDefaultBgColor, mStrokeWidth, mDefaultStrokeColor);
            //添加默认状态
            selector.addState(new int[]{}, defaultShape);

            return selector;
        }

        /**
         * 是否设置了按下背景色以及按下描边背景色
         *
         * @return true/false
         */
        private boolean setPressedBgColor() {
            return mPressedBgColor != Color.TRANSPARENT || mPressedStrokeColor != Color.TRANSPARENT;
        }

        /**
         * 是否设置了按下禁用背景色或者禁用状态的描边色
         *
         * @return true/false
         */
        private boolean setDisabledBgColor() {
            return mDisabledBgColor != Color.TRANSPARENT || mDisabledStrokeColor != Color.TRANSPARENT;
        }

        /**
         * 是否设置了选中背景色或者选中描边色
         *
         * @return true/false
         */
        private boolean setSelectedBgColor() {
            return mSelectedBgColor != Color.TRANSPARENT || mSelectedStrokeColor != Color.TRANSPARENT;
        }

        /**
         * 是否设置了聚焦时的背景色或者聚焦时的描边色
         *
         * @return true/false
         */
        private boolean setFocusedBgColor() {
            return mFocusedBgColor != Color.TRANSPARENT || mFocusedStrokeColor != Color.TRANSPARENT;
        }


        /**
         * 设置禁用状态的背景颜色
         *
         * @param color color
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setDisabledBgColor(@ColorInt int color) {
            mDisabledBgColor = color;
            return this;
        }

        /**
         * 设置禁用描边的颜色
         *
         * @param color color
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setDisabledStrokeColor(@ColorInt int color) {
            mDisabledStrokeColor = color;
            return this;
        }

        /**
         * 选中背景颜色
         *
         * @param color color
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setSelectedBgColor(@ColorInt int color) {
            mSelectedBgColor = color;
            return this;
        }

        /**
         * 选中描边颜色
         *
         * @param color color
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setSelectedStrokeColor(@ColorInt int color) {
            mSelectedStrokeColor = color;
            return this;
        }

        /**
         * 设置聚焦时的背景色
         *
         * @param color color
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setFocusedBgColor(@ColorInt int color) {
            mFocusedBgColor = color;
            return this;
        }

        /**
         * 设置聚焦时的描边色
         *
         * @param color color
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setFocusedStrokeColor(@ColorInt int color) {
            mFocusedStrokeColor = color;
            return this;
        }

        /**
         * 设置按下描边颜色
         *
         * @param color color
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setPressedStrokeColor(@ColorInt int color) {
            mPressedStrokeColor = color;
            return this;
        }

        /**
         * 设置按下背景颜色
         *
         * @param color color
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setPressedBgColor(@ColorInt int color) {
            mPressedBgColor = color;
            return this;
        }

        /**
         * 设置默认描边颜色
         *
         * @param color color
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setDefaultStrokeColor(@ColorInt int color) {
            mDefaultStrokeColor = color;
            if (mDisabledStrokeColor == Color.TRANSPARENT)
                mDisabledStrokeColor = color;
            if (mPressedStrokeColor == Color.TRANSPARENT)
                mPressedStrokeColor = color;
            if (mSelectedStrokeColor == Color.TRANSPARENT)
                mSelectedStrokeColor = color;
            if (mFocusedStrokeColor == Color.TRANSPARENT)
                mFocusedStrokeColor = color;
            return this;
        }

        /**
         * 设置形状
         *
         * @param shape shape
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setShape(int shape) {
            mShape = shape;
            return this;
        }


        /**
         * 设置描边宽度
         *
         * @param width 宽度
         * @return ShapeSelector
         */
        public SelectorUtils.ShapeSelector setStrokeWidth(@Dimension int width) {
            mStrokeWidth = width;
            return this;
        }

        private GradientDrawable getItemShape(int shape, float[] cornerRadius,
                                              int solidColor, int strokeWidth, int strokeColor) {
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(shape);
            drawable.setStroke(strokeWidth, strokeColor);
            if (cornerRadius.length == 8) {
                drawable.setCornerRadii(cornerRadius);
            } else {
                drawable.setCornerRadius((int) cornerRadius[0]);
            }
            drawable.setColor(solidColor);
            return drawable;
        }
    }

    //给TextColor赋值的
    public static ColorSelector newColorSelector() {
        return new ColorSelector();
    }

    public static final class ColorSelector {

        private int mDefaultColor;
        private int mDisabledColor;
        private int mPressedColor;
        private int mSelectedColor;
        private int mFocusedColor;

        private boolean hasSetDisabledColor = false;
        private boolean hasSetPressedColor = false;
        private boolean hasSetSelectedColor = false;
        private boolean hasSetFocusedColor = false;

        private ColorSelector() {
            mDefaultColor = Color.BLACK;
            mDisabledColor = Color.GRAY;
            mPressedColor = Color.BLACK;
            mSelectedColor = Color.BLACK;
            mFocusedColor = Color.BLACK;
        }

        public ColorSelector setDefaultColor(@ColorInt int color) {
            mDefaultColor = color;
            if (!hasSetDisabledColor)
                mDisabledColor = color;
            if (!hasSetPressedColor)
                mPressedColor = color;
            if (!hasSetSelectedColor)
                mSelectedColor = color;
            if (!hasSetFocusedColor)
                mFocusedColor = color;
            return this;
        }

        public ColorSelector setDisabledColor(@ColorInt int color) {
            mDisabledColor = color;
            hasSetDisabledColor = true;
            return this;
        }

        public ColorSelector setPressedColor(@ColorInt int color) {
            mPressedColor = color;
            hasSetPressedColor = true;
            return this;
        }

        public ColorSelector setSelectedColor(@ColorInt int color) {
            mSelectedColor = color;
            hasSetSelectedColor = true;
            return this;
        }

        public ColorSelector setFocusedColor(@ColorInt int color) {
            mFocusedColor = color;
            hasSetFocusedColor = true;
            return this;
        }

        public ColorStateList create() {
            int[] colors = new int[]{
                    hasSetDisabledColor ? mDisabledColor : mDefaultColor,
                    hasSetPressedColor ? mPressedColor : mDefaultColor,
                    hasSetSelectedColor ? mSelectedColor : mDefaultColor,
                    hasSetFocusedColor ? mFocusedColor : mDefaultColor,
                    mDefaultColor
            };
            int[][] states = new int[5][];
            states[0] = new int[]{-android.R.attr.state_enabled};
            states[1] = new int[]{android.R.attr.state_pressed};
            states[2] = new int[]{android.R.attr.state_selected};
            states[3] = new int[]{android.R.attr.state_focused};
            states[4] = new int[]{};
            return new ColorStateList(states, colors);
        }
    }

    public static DrawableSelector newDrawableSelector() {
        return new DrawableSelector();
    }

    //给Drawable赋值的
    public static final class DrawableSelector {

        private Drawable mDefaultDrawable;
        private Drawable mDisabledDrawable;
        private Drawable mPressedDrawable;
        private Drawable mSelectedDrawable;
        private Drawable mFocusedDrawable;

        private boolean hasSetDisabledDrawable = false;
        private boolean hasSetPressedDrawable = false;
        private boolean hasSetSelectedDrawable = false;
        private boolean hasSetFocusedDrawable = false;

        private DrawableSelector() {
            mDefaultDrawable = new ColorDrawable(Color.TRANSPARENT);
        }

        public DrawableSelector setDefaultDrawable(Drawable drawable) {
            mDefaultDrawable = drawable;
            if (!hasSetDisabledDrawable)
                mDisabledDrawable = drawable;
            if (!hasSetPressedDrawable)
                mPressedDrawable = drawable;
            if (!hasSetSelectedDrawable)
                mSelectedDrawable = drawable;
            if (!hasSetFocusedDrawable)
                mFocusedDrawable = drawable;
            return this;
        }

        public DrawableSelector setDisabledDrawable(Drawable drawable) {
            mDisabledDrawable = drawable;
            hasSetDisabledDrawable = true;
            return this;
        }

        public DrawableSelector setPressedDrawable(Drawable drawable) {
            mPressedDrawable = drawable;
            hasSetPressedDrawable = true;
            return this;
        }

        public DrawableSelector setSelectedDrawable(Drawable drawable) {
            mSelectedDrawable = drawable;
            hasSetSelectedDrawable = true;
            return this;
        }

        public DrawableSelector setFocusedDrawable(Drawable drawable) {
            mFocusedDrawable = drawable;
            hasSetFocusedDrawable = true;
            return this;
        }

        public StateListDrawable create() {
            StateListDrawable selector = new StateListDrawable();
            if (hasSetDisabledDrawable)
                selector.addState(new int[]{-android.R.attr.state_enabled}, mDisabledDrawable);
            if (hasSetPressedDrawable)
                selector.addState(new int[]{android.R.attr.state_pressed}, mPressedDrawable);
            if (hasSetSelectedDrawable)
                selector.addState(new int[]{android.R.attr.state_selected}, mSelectedDrawable);
            if (hasSetFocusedDrawable)
                selector.addState(new int[]{android.R.attr.state_focused}, mFocusedDrawable);
            selector.addState(new int[]{}, mDefaultDrawable);
            return selector;
        }

        //overload
        public DrawableSelector setDefaultDrawable(Context context, @DrawableRes int drawableRes) {
            return setDefaultDrawable(ContextCompat.getDrawable(context, drawableRes));
        }

        //overload
        public DrawableSelector setDisabledDrawable(Context context, @DrawableRes int drawableRes) {
            return setDisabledDrawable(ContextCompat.getDrawable(context, drawableRes));
        }

        //overload
        public DrawableSelector setPressedDrawable(Context context, @DrawableRes int drawableRes) {
            return setPressedDrawable(ContextCompat.getDrawable(context, drawableRes));
        }

        //overload
        public DrawableSelector setSelectedDrawable(Context context, @DrawableRes int drawableRes) {
            return setSelectedDrawable(ContextCompat.getDrawable(context, drawableRes));
        }

        //overload
        public DrawableSelector setFocusedDrawable(Context context, @DrawableRes int drawableRes) {
            return setFocusedDrawable(ContextCompat.getDrawable(context, drawableRes));
        }
    }
}
