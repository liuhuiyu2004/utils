package com.liuhuiyu.util.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-09 11:35
 */
public class JsUtil {
    private static final String BASE_64_HASH = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    public static final String VALUE_RANGE_REGEX = "([^\\u0000-\\u00ff])";

    public static boolean isMatcher(String inStr, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(inStr);
        return matcher.matches();
    }

    /**
     * btoa method
     *
     * @param inStr 字符串
     * @return 加密后的字符串
     */
    public static String btoa(String inStr) {
        if (inStr == null || isMatcher(inStr, VALUE_RANGE_REGEX)) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        int i = 0;
        int mod = 0;
        int ascii;
        int prev = 0;
        while (i < inStr.length()) {
            ascii = inStr.charAt(i);
            mod = i % 3;
            switch (mod) {
                case 0:
                    result.append(BASE_64_HASH.charAt(ascii >> 2));
                    break;
                case 1:
                    result.append(BASE_64_HASH.charAt((prev & 3) << 4 | (ascii >> 4)));
                    break;
                case 2:
                    result.append(BASE_64_HASH.charAt((prev & 0x0f) << 2 | (ascii >> 6)));
                    result.append(BASE_64_HASH.charAt(ascii & 0x3f));
                    break;
                default:
                    break;
            }
            prev = ascii;
            i++;
        }

        if (mod == 0) {
            result.append(BASE_64_HASH.charAt((prev & 3) << 4));
            result.append("==");
        }
        else if (mod == 1) {
            result.append(BASE_64_HASH.charAt((prev & 0x0f) << 2));
            result.append("=");
        }
        return result.toString();
    }


    /**
     * // atob method
     * // 逆转encode的思路即可
     *
     * @param inStr 加密字符串
     * @return 解密后字符串
     */
    public static String atob(String inStr) {
        if (inStr == null) {
            return null;
        }
        inStr = inStr.replaceAll("\\s|=", "");
        StringBuilder result = new StringBuilder();
        int cur;
        int prev = -1;
        int mod;
        int i = 0;
        while (i < inStr.length()) {
            cur = BASE_64_HASH.indexOf(inStr.charAt(i));
            mod = i % 4;
            switch (mod) {
                case 1:
                    result.append((char) (prev << 2 | cur >> 4));
                    break;
                case 2:
                    result.append((char) ((prev & 0x0f) << 4 | cur >> 2));
                    break;
                case 3:
                    result.append((char) ((prev & 3) << 6 | cur));
                    break;
                default:
                    break;
            }
            prev = cur;
            i++;
        }
        return result.toString();
    }
}
