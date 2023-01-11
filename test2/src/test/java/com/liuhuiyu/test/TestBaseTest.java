package com.liuhuiyu.test;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-09-08 11:36
 */
public class TestBaseTest extends TestBase{
    @Test
    public void t1(){
        super.setLoggerValue(Level.ALL);
        LOG.trace("s");
        LOG.debug("s");
        LOG.info("s");
        LOG.warn("s");
        LOG.error("s");
        LOG.fatal("s");
    }
    @Test
    public void t2(){
        String s="24:0";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
        LocalTime t1=LocalTime.parse(s, formatter);
        t1=t1.plusNanos(-1);
        LOG.info(t1);
    }
}
