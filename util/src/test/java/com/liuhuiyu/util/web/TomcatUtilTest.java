package com.liuhuiyu.util.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-09-29 10:52
 */
class TomcatUtilTest {
    private static final Logger LOG = LogManager.getLogger(TomcatUtilTest.class);

    @Test
    public void getTomcatPort() {
        final Integer port = TomcatUtil.getTomcatPort().orElse(0);
        LOG.info("端口：{}", port);
    }

}