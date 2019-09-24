package com.example.mypubliclibrary.util;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/7/18.
 */
public class StringUtils {
//    /**
//     * 指定字符区间的颜色
//     *
//     * @param value  字符串
//     * @param colors 区间颜色 索引包含开始,不包含结束
//     * @return 直接复制给TextView.setText()即可
//     */
//    public SpannableStringBuilder setStringColor(St1ring value, StringColor... colors) {
//        SpannableStringBuilder style = new SpannableStringBuilder(value);
//        for (StringColor color : colors) {
//            style.setSpan(new ForegroundColorSpan(color.getColor()), color.getStartIndex(), color.getEndIndex(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        return style;
//    }

    /**
     * 字符串去除指定内容
     *
     * @param value    字符串
     * @param replaceS 去除的内容
     * @return 去除后的值
     */
    public String removeString(String value, String... replaceS) {
        for (String replace : replaceS) {
            value = value.replace(replace, "");
        }
        return value;
    }

    public static boolean isEmpty(String value) {
        if (value == null) return true;
        return value.isEmpty();
    }
}
