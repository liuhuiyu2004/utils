package com.liuhuiyu.util.string;

import com.liuhuiyu.util.asserts.LhyAssert;

/**
 * 字符串工具
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-01-24 9:08
 */
public class StringUtil {

    /**
     * 子串截取
     *
     * @param value      原始字符
     * @param beginIndex 开始索引（0开始）
     * @param len        截取长度（注意这里是截取的最大长度>0）
     * @return java.lang.String
     * @author LiuHuiYu
     * Created DateTime 2022-01-24 9:10
     */
    public static String substring(String value, int beginIndex, int len) {
        if (value == null) {
            return "";
        }
        if (len <= 0) {
            len = value.length();
        }
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (beginIndex + len > value.length()) {
            return value.substring(beginIndex, beginIndex + len);
        }
        else if (beginIndex > value.length()) {
            return "";
        }
        else {
            return value.substring(beginIndex);
        }
    }
}
