package com.liuhuiyu;

import com.liuhuiyu.test.TestBase;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestBase {
    @Test
    public void t1() {
        super.setLoggerValue(Level.ALL);
        LOG.trace("s");
        LOG.debug("s");
        LOG.info("s");
        LOG.warn("s");
        LOG.error("s");
        LOG.fatal("s");
        super.setLoggerValue(Level.INFO);
        LOG.trace("s1");
        LOG.debug("s1");
        LOG.info("s1");
        LOG.warn("s1");
        LOG.error("s1");
        LOG.fatal("s1");
        super.setLoggerValue(this.getClass(),Level.WARN);
        LOG.trace("s2");
        LOG.debug("s2");
        LOG.info("s2");
        LOG.warn("s2");
        LOG.error("s2");
        LOG.fatal("s2");
        super.setLoggerValue(this.getClass(),Level.ERROR);
        LOG.trace("s3");
        LOG.debug("s3");
        LOG.info("s3");
        LOG.warn("s3");
        LOG.error("s3");
        LOG.fatal("s3");
    }

    @Test
    public void t2() {
        String s = "24:0";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
        LocalTime t1 = LocalTime.parse(s, formatter);
        t1 = t1.plusNanos(-1);
        LOG.info(t1);
        this.printObjectJson(t1);
    }
}
