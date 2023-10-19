package com.liuhuiyu.test;

import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-04-19 14:10
 */
public abstract class AbstractTest {
    protected static Logger LOG;

    @BeforeAll
    public static void initializeLogger() {
        LOG = LogManager.getLogger();
        Configurator.setLevel(LOG, Level.DEBUG);
    }

    /**
     * 初始化 Logger
     *
     * @author LiuHuiYu
     * Created DateTime 2022-09-08 11:18
     */
    protected void setLoggerValue(final Class<?> clazz, Level level) {
        LOG = LogManager.getLogger(clazz);
        Configurator.setLevel(LOG, level);
    }

    /**
     * 初始化 Logger
     *
     * @param level 级别
     * @author LiuHuiYu
     * Created DateTime 2022-09-08 11:18
     */
    protected void setLoggerValue(Level level) {
        LOG = LogManager.getLogger();
        Configurator.setLevel(LOG, level);
    }

    /**
     * 打印Object序列化到json的信息
     *
     * @param obj 打印的对象
     * @author LiuHuiYu
     * Created DateTime 2022-09-08 11:21
     */
    protected void printObjectJson(Object obj) {
        LOG.info(new Gson().toJson(obj));
    }
}
