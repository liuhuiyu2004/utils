package com.liuhuiyu.test;

import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.jupiter.api.BeforeAll;

/**
 * 测试基础类<p>
 * Created on 2025/3/12 21:34
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public abstract class AbstractTest {
    protected static Logger LOG;

    /**
     * 初始化<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/12 21:35
     */
    @BeforeAll
    public static void initializeLogger() {
        LOG = LogManager.getLogger();
        Configurator.setLevel(LOG, Level.DEBUG);
    }

    /**
     * 设置日志等级<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/12 21:36
     *
     * @param clazz 类对象，用于确定日志记录器的来源。
     * @param level 日志级别，指定日志的详细程度。
     */
    protected void setLoggerValue(final Class<?> clazz, Level level) {
        LOG = LogManager.getLogger(clazz);
        Configurator.setLevel(LOG, level);
    }

    /**
     * 设置日志等级<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/12 21:37
     *
     * @param level 级别
     */
    protected void setLoggerValue(Level level) {
        LOG = LogManager.getLogger();
        Configurator.setLevel(LOG, level);
    }

    /**
     * 打印Object序列化到json的信息<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/12 21:38
     *
     * @param obj 打印的对象
     */
    protected void printObjectJson(Object obj) {
        LOG.info(new Gson().toJson(obj));
    }
}
