package com.liuhuiyu.core.util;

/**
 * 字符序列工具类
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/8/28 8:50
 */
public class CharSequenceUtil {

    public static final int INDEX_NOT_FOUND = -1;

    /**
     * 字符串常量：{@code "null"} <br>
     * 注意：{@code "null" != null}
     */
    public static final String NULL = "null";

    /**
     * 字符串常量：空字符串 {@code ""}
     */
    public static final String EMPTY = "";

    /**
     * 字符串常量：空格符 {@code " "}
     */
    public static final String SPACE = " ";
    /**
     * 字符串常量：空 JSON {@code "{}"}
     */
    public static final String EMPTY_JSON = "{}";
    /**
     * 字符常量：反斜杠 {@code '\\'}
     */
    public static final char C_BACKSLASH = '\\';

    public static boolean hasText(CharSequence str) {
        return str != null && str.length() > 0 && containsText(str);
    }

    public static boolean hasText(String str) {
        return str != null && !str.isEmpty() && containsText(str);
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();

        for (int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }


}
