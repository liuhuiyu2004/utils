package com.liuhuiyu.core.util;

/**
 * 字符串工具类
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/8/28 8:49
 */
public class StringUtil {

    public static boolean hasText(String str) {
        return str != null && !str.isEmpty() && containsText(str);
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
