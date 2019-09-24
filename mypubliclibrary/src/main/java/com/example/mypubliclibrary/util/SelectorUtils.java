package com.example.mypubliclibrary.util;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;

public class SelectorUtils {


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
        }


        public SelectorUtils.ShapeSelector setDefaultBgColor(@ColorInt int color) {
            mDefaultBgColor = color;
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
         * 是否设置了按下禁用背景色以及禁用状态的描边色
         *
         * @return true/false
         */
        private boolean setDisabledBgColor() {
            return mDisabledBgColor != Color.TRANSPARENT || mDisabledStrokeColor != Color.TRANSPARENT;
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


}
