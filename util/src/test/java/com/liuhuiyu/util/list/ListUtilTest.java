package com.liuhuiyu.util.list;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-05-25 19:16
 */
@Log4j2
public class ListUtilTest {
    @Test
    public void objectToList() {
        Object o = getList();
        List<Object> list = ListUtil.objectToList(o);
        log.info("转换List:{}", list);
        Object o1 = getArray();
        List<Object> list1 = ListUtil.objectToList(o1);
        log.info("转换List:{}", list1);
        Object o2 = new int[]{};
        List<Object> list2 = ListUtil.objectToList(o2);
        log.info("转换List:{}", list2);
    }

    @Test
    public void objectToListT() {
        Object o = getList();
        List<Integer> list = ListUtil.objectToListT(o, (obj) -> {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            else {
                return 0;
            }
        });
        log.info("转换List:{}", list);
        Object o1 = getArray();
        List<Integer> list1 = ListUtil.objectToListT(o1, (obj) -> {
            if (obj instanceof Integer) {
                return (Integer) obj;
            }
            else {
                return 0;
            }
        });
        log.info("转换List:{}", list1);
    }


    @Test
    public void getSortList() {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer[] sorts = {1, 3, 5, 7, 9, 4, 8};
        BiFunction<Integer, Integer, Boolean> compare = Integer::equals;
        List<Integer> res;
        res = ListUtil.getSortList(list, sorts, compare);
        log.info(res);
        res = ListUtil.getSortList(list, sorts, compare, true, false, false);
        log.info(res);
        res = ListUtil.getSortList(list, sorts, compare, true, false, true);
        log.info(res);
        res = ListUtil.getSortList(list, sorts, compare, true, true, false);
        log.info(res);
        res = ListUtil.getSortList(list, sorts, compare, true, true, true);
        log.info(res);
        res = ListUtil.getSortList(list, sorts, compare, false, false, false);
        log.info(res);
        res = ListUtil.getSortList(list, sorts, compare, false, false, true);
        log.info(res);
        res = ListUtil.getSortList(list, sorts, compare, false, true, false);
        log.info(res);
        res = ListUtil.getSortList(list, sorts, compare, false, true, true);
        log.info(res);
    }

    @Test
    public void listToList() {
        List<Integer> integerList = this.getList();
        List<Long> longList = ListUtil.listToList(integerList, Integer::longValue);
        List<String> strList = ListUtil.listToList(integerList, (i) -> "'" + i.toString() + "'");
        log.info("{}->{}->{}", integerList, longList, strList);
    }

    @Test
    public void listToArray() {
        List<Integer> integerList = this.getList();
        Integer[] is = ListUtil.listToArray(integerList);
        log.info("{}->{}", integerList, is);
    }

    @Test
    public void arrayToList() {
        Integer[] array = getArray();
        List<Integer> list = ListUtil.arrayToList(array);
        log.info("{}->{}", array, list);
    }

    private List<Integer> getList() {
        List<Integer> integerList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            integerList.add(i);
        }
        return integerList;
    }

    private Integer[] getArray() {
        return new Integer[]{1, 2, 3, 4, 5, 6, 7};
    }
}