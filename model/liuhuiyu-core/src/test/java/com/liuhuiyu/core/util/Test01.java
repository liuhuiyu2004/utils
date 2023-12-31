package com.liuhuiyu.core.util;

import com.liuhuiyu.core.lang.StringUtils;
import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-11-23 21:44
 */
public class Test01 extends AbstractTest {
    @Test
    public void test() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        LOG.info("noneMatch:{}",
                list.stream().filter(v -> {
                    LOG.info("过滤：{}", v);
                    return true;
                }).noneMatch(v -> v.equals(3)));
        LOG.info("anyMatch:{}",
                list.stream().filter(v -> {
                    LOG.info("过滤：{}", v);
                    return true;
                }).anyMatch(v -> v.equals(3)));
        LOG.info("allMatch:{}",
                list.stream().filter(v -> {
                    LOG.info("过滤：{}", v);
                    return true;
                }).allMatch(v -> v <= 3));
    }

    @Test
    public void test2() {
        LOG.info("{}", Integer.MAX_VALUE);//2,147,483,647
    }

    @Test
    public void test3() {
        String str = "f|";
        final String[] split = Arrays.stream(str.split("[,;；、:|]")).filter(StringUtils::hasText).toArray(String[]::new);
        LOG.info("{}", split);
    }

    @Test
    public void test4() {
        Integer a = null;
        Object b = null;
        Float c = null;
        LOG.info("a=b?{};b=c?{};a=c?{}", e(a,b),e(a,c), e(b,c));
    }

    public static boolean e(Object o1,Object o2){
        if (o1 == o2) {
            return true;
        } else if (o1 != null && o2 != null) {
            if (o1.equals(o2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
