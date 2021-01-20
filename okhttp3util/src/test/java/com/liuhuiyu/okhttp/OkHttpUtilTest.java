package com.liuhuiyu.okhttp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-12 19:11
 */
public class OkHttpUtilTest {

    @Test
    public void test() {
        Map<String, Object> m1 = new HashMap<>();
        Map<String, Object> m2 = new HashMap<>();
        Map<String, Object> m3 = new HashMap<>();
        List<Number> list1 = new ArrayList<>();
        list1.add(1.0);
        list1.add(2.0);
        list1.add(3.0);
        list1.add(4.1);
        list1.add(5.0);
        m3.put("m3_1", list1);
        m3.put("m3_2", 2.0);
        m3.put("m3_3", 3.0);
        m2.put("m2_1", m3);
        m2.put("m2_2", 5.0);
        m2.put("m2_3", 6.1);
        m1.put("m1_1", m2);
        m1.put("m1_2", 4.0);
        m1.put("m1_3", 1.1);
        Map<String, Object> map = OkHttpUtil.mapDoubleToInt(m1);

    }
}