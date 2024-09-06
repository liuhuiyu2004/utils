package com.liuhuiyu.core.util;

import com.liuhuiyu.core.text.StrFormatter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;

/**
 * 字符串工具类
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/8/28 8:49
 */
public class StringUtil extends CharSequenceUtil {
    /**
     * 字符串是否包含文字<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/8/31 10:00
     * <pre>
     *   str = null -> false
     *   str = "" -> false
     *   str = " " -> false
     *   str = "a" -> true
     * </pre>
     * 检测空白字符串请使用 {@link #isEmpty(Object)}
     * 检测不含字符的字符串请使用 {@link #isBlank(CharSequence)}
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean hasText(String str) {
        return str != null && !str.isEmpty() && containsText(str);
    }

    /**
     * 检测空白字符串<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/8/31 10:08
     * <pre>
     *     str = null -> true
     *     str = "" -> true
     *     str = " " -> false
     *     str = "a" -> false
     * </pre>
     * 检测字符串包含文字请使用 {@link #hasText(String)}
     * 检测不含字符的字符串请使用 {@link #isBlank(CharSequence)}
     *
     * @param str 检测对象
     * @return boolean
     */
    public static boolean isEmpty(Object str) {
        return str == null || CharSequenceUtil.EMPTY.equals(str);
    }

    /**
     * 检测不含字符的字符串<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/8/31 10:08
     * <pre>
     *     str = null -> true
     *     str = "" -> true
     *     str = " " -> false
     *     str = "a" -> false
     * </pre>
     * 检测字符串包含文字请使用 {@link #hasText(String)}
     * 检测空白字符串请使用 {@link #isEmpty(Object)}
     *
     * @param str 检测对象
     * @return boolean
     */
    public static boolean isBlank(CharSequence str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        else {
            return true;
        }
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

    /**
     * 将对象转为字符串<br>
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param data 字节码
     * @return 字符串
     */
    public static Optional<String> utf8Str(byte[] data) {
        return str(data, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static Optional<String> str(byte[] data, Charset charset) {
        if (data == null) {
            return Optional.empty();
        }
        if (null == charset) {
            return Optional.of(new String(data));
        }
        return Optional.of(new String(data, charset));
    }

    /**
     * 将对象转为字符串<br>
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj 对象
     * @return 字符串
     */
    public static Optional<String> utf8Str(Object obj) {
        return strObject(obj, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static Optional<String> strObject(Object obj, Charset charset) {
        if (null == obj) {
            return Optional.empty();
        }
        if (obj instanceof String) {
            return Optional.of((String) obj);
        }
        else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        }
        else if (obj instanceof Byte[]) {
            Byte[] bytes = (Byte[]) obj;
            return str(PrimitiveUtil.convertPrimitiveArrayToBoxedArray(bytes), charset);
        }
        else if (obj instanceof ByteBuffer) {
            ByteBuffer buf = (ByteBuffer) obj;
            return str(buf.array(), charset);
        }
//        else if (ArrayUtil.isArray(obj)) {
//            return ArrayUtil.toString(obj);
//        }

        return Optional.ofNullable(obj.toString());
    }

    /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串<br>
     * 字符填充于字符串前
     *
     * @param str        被填充的字符串
     * @param filledChar 填充的字符
     * @param len        填充长度
     * @return 填充后的字符串
     */
    public static String fillBefore(String str, char filledChar, int len) {
        return fill(str, filledChar, len, true);
    }

    /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串<br>
     * 字符填充于字符串后
     *
     * @param str        被填充的字符串
     * @param filledChar 填充的字符
     * @param len        填充长度
     * @return 填充后的字符串
     * @since 3.1.2
     */
    public static String fillAfter(String str, char filledChar, int len) {
        return fill(str, filledChar, len, false);
    }

    /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串
     *
     * @param str        被填充的字符串
     * @param filledChar 填充的字符
     * @param len        填充长度
     * @param isPre      是否填充在前
     * @return 填充后的字符串
     * @since 3.1.2
     */
    public static String fill(String str, char filledChar, int len, boolean isPre) {
        final int strLen = str.length();
        if (strLen > len) {
            return str;
        }

        String filledStr = repeat(filledChar, len - strLen);
        return isPre ? filledStr.concat(str) : str.concat(filledStr);
    }

    /**
     * 重复某个字符
     *
     * <pre>
     * CharSequenceUtil.repeat('e', 0)  = ""
     * CharSequenceUtil.repeat('e', 3)  = "eee"
     * CharSequenceUtil.repeat('e', -2) = ""
     * </pre>
     *
     * @param c     被重复的字符
     * @param count 重复的数目，如果小于等于0则返回""
     * @return 重复字符字符串
     */
    public static String repeat(char c, int count) {
        if (count <= 0) {
            return EMPTY;
        }
        char[] result = new char[count];
        Arrays.fill(result, c);
        return new String(result);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is {} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示，如果模板为null，返回"null"
     * @param params   参数值
     * @return 格式化后的文本，如果模板为null，返回"null"
     */
    public static String format(CharSequence template, Object... params) {
        if (null == template) {
            return NULL;
        }
        if (ArrayUtil.isEmpty(params) || isBlank(template)) {
            return template.toString();
        }
        return StrFormatter.format(template.toString(), params);
    }
}
