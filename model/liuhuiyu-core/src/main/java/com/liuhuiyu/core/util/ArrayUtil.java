package com.liuhuiyu.core.util;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/4 8:53
 */
public class ArrayUtil {
    /**
     * 数组是否为空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
}
