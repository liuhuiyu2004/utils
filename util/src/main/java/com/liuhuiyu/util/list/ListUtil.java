package com.liuhuiyu.util.list;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 对象转换成List
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-04-14 10:59
 */
public class ListUtil {
    public static final String ARRAY_HEAD = "[";

    /**
     * 将对象 转换成 List
     *
     * @param obj List对象 或者 array对象
     * @return java.util.List<java.lang.Object>
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 9:26
     */
    public static List<Object> objectToList(Object obj) {
        if (obj == null) {
            return new ArrayList<>(0);
        }
        else if (obj instanceof List<?>) {
            List<Object> resList;
            List<?> list = (List<?>) obj;
            resList = new ArrayList<>(list.size());
            resList.addAll(list);
            return resList;
        }
        else if (obj instanceof Object[]) {
            Object[] objects = (Object[]) obj;
            List<Object> resList = new ArrayList<>(objects.length);
            Collections.addAll(resList, objects);
            return resList;
        }
        else if (obj.getClass().getName().indexOf(ARRAY_HEAD) == 0) {
            return new ArrayList<>(0);
        }
        else {
            throw new RuntimeException("无法转换");
        }

    }

    /**
     * 将对象 转换成 List
     *
     * @param obj      List对象
     * @param function 转换函数
     * @return java.util.List<T>
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 9:26
     */
    public static <T> List<T> objectToListT(Object obj, Function<Object, T> function) {
        if (obj == null) {
            return new ArrayList<>(0);
        }
        else if (obj instanceof List<?>) {
            List<T> resList;
            List<?> list = (List<?>) obj;
            resList = new ArrayList<>(list.size());
            list.forEach((o) -> resList.add(function.apply(o)));
            return resList;
        }
        else if (obj instanceof Object[]) {
            Object[] objects = (Object[]) obj;
            if (objects.length == 0) {
                return new ArrayList<>(0);
            }
            else {
                List<T> resList = new ArrayList<>(objects.length);
                for (Object object : objects) {
                    resList.add(function.apply(object));
                }
                return resList;
            }
        }
        else if (obj.getClass().getName().indexOf(ARRAY_HEAD) == 0) {
            return new ArrayList<>(0);
        }
        else {
            throw new RuntimeException("无法转换");
        }
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

    /**
     * 获取排序后的列表
     *
     * @param list    要排序的列表
     * @param sorts   排序的排列顺序
     * @param compare 排序匹配规则
     * @return java.util.List<T>
     */
    public static <T, L> List<T> getSortList(List<T> list, L[] sorts, BiFunction<T, L, Boolean> compare) {
        return getSortList(list, sorts, compare, true, true, true);
    }

    /**
     * 获取排序后的列表
     *
     * @param list              要排序的列表
     * @param sorts             排序的排列顺序
     * @param compare           排序匹配规则
     * @param asc               按照排序顺序输出
     * @param notMatchedPostfix 未匹配的元素放到最后
     * @param notMatchedAsc     未匹配的元素顺序输出
     * @return java.util.List<T>
     * @author LiuHuiYu
     * Created DateTime 2021-05-25 19:13
     */
    public static <T, L> List<T> getSortList(List<T> list, L[] sorts, BiFunction<T, L, Boolean> compare, Boolean asc, Boolean notMatchedPostfix, Boolean notMatchedAsc) {

        List<T> resList = new ArrayList<>(list.size());
        list.stream().sorted((a1, a2) -> {
            Integer index1 = ListUtil.getIndex(a1, sorts, compare);
            Integer index2 = ListUtil.getIndex(a2, sorts, compare);
            if (index1 == -1 && index2 == -1) {
                return notMatchedPostfix ? 1 : -1;
            }
            else if (index1 == -1) {
                return notMatchedAsc ? 1 : -1;
            }
            else if (index2 == -1) {
                return notMatchedAsc ? -1 : 1;
            }
            else if (asc) {
                return index1.compareTo(index2);
            }
            else {
                return index2.compareTo(index1);
            }
        }).forEach(resList::add);
        return resList;
    }

    /**
     * List 转换
     *
     * @param tList    原始list
     * @param function 转换规则
     * @return java.util.List<R>
     * @author LiuHuiYu
     * Created DateTime 2021-06-04 16:08
     */
    public static <T, R> List<R> listToList(List<T> tList, Function<T, R> function) {
        List<R> rList = new ArrayList<>(0);
        for (T item : tList) {
            rList.add(function.apply(item));
        }
        return rList;
    }

    /**
     * list 转换成 数组
     *
     * @param list List
     * @return T[]
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 9:17
     */
    public static <T> T[] listToArray(List<T> list) {
        if (list.size() == 0) {
            @SuppressWarnings("unchecked") final T[] a1 = (T[]) new Object[0];
            return a1;
        }
        Class<?> z = list.get(0).getClass();
        @SuppressWarnings("unchecked") final T[] a = (T[]) Array.newInstance(z, list.size());
        for (int i = 0; i < list.size(); i++) {
            a[i] = list.get(i);
        }
        return a;
    }

    /**
     * 数组转换 List
     *
     * @param array 数组
     * @return java.util.List<T>
     * @author LiuHuiYu
     * Created DateTime 2021-07-17 9:17
     */
    public static <T> List<T> arrayToList(T[] array) {
        List<T> list = new ArrayList<>(array.length);
        Collections.addAll(list, array);
        return list;
    }
}
