package com.liuhuiyu.core.util;

import com.liuhuiyu.core.lang.StringUtils;
import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

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
        LOG.info("a=b?{};b=c?{};a=c?{}", e(a, b), e(a, c), e(b, c));
    }

    public static boolean e(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        else if (o1 != null && o2 != null) {
            if (o1.equals(o2)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Test
    public void test5() {
        List<String> list = new ArrayList<>();
        list.add("00524642");
        list.add("00522773");
        list.add("00522483;00524985");
        list.add("00524985;00524628;00522775;00523126;00524224;00524226;00524492;00522773;00524642;00524971;00522883;00524596;00522483");
        list.add("00522773;00524489;00524641;00524642;00523122;00524596;00524623;00522766;00522483;00524985;00522775;00524224");
        list.add("00523137;00524640;00524641;00524642;00523122;00522766;00524596;00524623;00523131;00522775;00524224");
        list.add("00523126;00524641;00524640;00522773;00524642;00523122;00523123;00522766;00524596;00522483;00522775");
        list.add("00523131;00523129;00524224;00523126;00523137;00524640;00524489;00522773;00524642;00524971;00523123;00524989;00524596;00524973;00522483");
        list.add("00522775;00524224;00523129;00524226;00523138;00523126;00523137;00524640;00522773;00524642;00523122;00522766;00524971;00523123;00524596");
        list.add("00524973;00522483;00522775;00523131;00524224;00523129;00524226;00524983;00523138;00523126;00524223;00524641;00523137;00524640;00522773;00524642;00523122");
        list.add("00523123;00522483;00524224;00523131;00523129;00524226;00524983;00524641");
        list.add("00524642;00523123;00522775;00524224;00523129;00524226");
        list.add("00522773;00524642;00522775;00524224");
        list.add("00524642");
        list.add("00522483;00524985");
        list.add("00524224;00522773;00523123;00523131");
        list.add("00522766;00523131;00522775;00524983;00524224;00524226;00523137;00522773;00524642;00523123;00524641");
        list.add("00524596;00522883;00524973;00522483;00522775;00523129;00523138;00524224;00524226;00523137;00524640;00522773;00524642");
        list.add("00524641;00523122;00524596;00522766;00522483;00523131;00524985;00522775;00524224;00524226;00523137");
        list.add("00524642");
        list.add("00524642");
        list.add("00524642");
        list.add("00524642");
        final List<String> collect = list.stream().flatMap(s -> Arrays.stream(s.split(";"))).collect(Collectors.toList());
        final Set<String> array2=new HashSet<>(collect);

    }
}
