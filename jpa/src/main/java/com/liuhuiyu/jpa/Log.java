package com.liuhuiyu.jpa;

import lombok.extern.log4j.Log4j2;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-31 16:54
 */
@Log4j2
public class Log {
    public static boolean outLog = false;

    public static void i(String info) {
        if (outLog) {
            log.info(info);
        }
    }

    public static void d(String info) {
        if (outLog) {
            log.debug(info);
        }
    }
}
