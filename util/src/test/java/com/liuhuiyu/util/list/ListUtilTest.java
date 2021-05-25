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
    public void getSortList() {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer[] sorts = {1, 3, 5, 7, 9, 4, 8};
        BiFunction<Integer, Integer, Boolean> compare = Integer::equals;
        List<Integer> res;
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

}