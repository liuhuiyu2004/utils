package com.liuhuiyu.util.list;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * 对象转换成List
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-04-14 10:59
 */
public class ListUtil {

    public static List<Object> objectToList(Object obj) {
        List<Object> resList;
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            resList = new ArrayList<>(list.size());
            resList.addAll(list);
        }
        else {
            throw new RuntimeException("无法转换");
        }
        return resList;
    }

    /**
     * 获取元素t 在列表中 对应的索引
     *
     * @param t          查询的对象
     * @param list       列表对象
     * @param comparable 列表比较函数式
     * @return java.lang.Integer
     * @author LiuHuiYu
     * Created DateTime 2021-05-19 22:48
     */
    public static <T, U> Integer getIndex(T t, U[] list, BiFunction<T, U, Boolean> comparable) {
        for (int i = 0; i < list.length; i++) {
            if (comparable == null) {
                if (t.equals(list[i])) {
                    return i;
                }
            }
            else if (comparable.apply(t, list[i])) {
                return i;
            }
        }
        return -1;
    }
}
