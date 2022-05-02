package com.liuhuiyu.jpa;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-31 16:54
 */
public class Log {
    private static final Logger LOG= LogManager.getLogger(Log.class);
    public static boolean outLog = false;

    public static void i(String info) {
        if (outLog) {
            LOG.info(info);
        }
    }

    public static void d(String info) {
        if (outLog) {
            LOG.debug(info);
        }
    }
}
