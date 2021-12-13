package com.liuhuiyu.okhttp.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.Map;


/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-10-13 14:03
 */
public class ArrangeTransformUtilTest {
    private final static Logger LOG = LogManager.getLogger(ArrangeTransformUtilTest.class);

    @Test
    public void getStringObjectMap() {
        String json = "a32132131";
        Map<String, Object> map = ArrangeTransformUtil.getStringObjectMap(json);
        LOG.info("map={}", map);
    }
}