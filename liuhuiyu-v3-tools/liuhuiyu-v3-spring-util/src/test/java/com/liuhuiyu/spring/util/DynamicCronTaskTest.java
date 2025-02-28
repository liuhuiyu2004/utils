package com.liuhuiyu.spring.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author LiuHuiYu - LiuHuiYu *
 * @version v1.0.0.0
 * Created DateTime 2024/6/10 下午1:27
 */
class DynamicCronTaskTest {
    private static final Logger LOG = LogManager.getLogger(DynamicCronTaskTest.class);

    public void test() {
        String cron = "0/1 * * * * ?";
        String name = "test";
        DynamicCronTask dynamicCronTask = new DynamicCronTask();
        dynamicCronTask.register(name, cron, () -> {
            LOG.info("定时任务");
        });
        final boolean b = dynamicCronTask.unRegister(name);
        LOG.info(b);
    }
}