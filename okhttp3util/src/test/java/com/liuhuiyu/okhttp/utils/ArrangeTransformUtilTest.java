package com.liuhuiyu.okhttp.utils;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-10-13 14:03
 */
public class ArrangeTransformUtilTest {

    @Test
    public void getStringObjectMap() {
        String json="a32132131";
        Map<String,Object> map=ArrangeTransformUtil.getStringObjectMap(json);
    }
}