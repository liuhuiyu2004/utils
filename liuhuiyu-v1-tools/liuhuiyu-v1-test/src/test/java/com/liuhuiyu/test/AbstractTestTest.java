package com.liuhuiyu.test;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/2/13 8:55
 */
class AbstractTestTest extends AbstractTest{
    @Test
    public void t1() {
        super.setLoggerValue(Level.ALL);
        this.print("ALL");
        super.setLoggerValue(Level.INFO);
        this.print("INFO");
        super.setLoggerValue(this.getClass(),Level.WARN);
        this.print("WARN");
        super.setLoggerValue(this.getClass(),Level.ERROR);
        this.print("ERROR");
    }

    private void print(String info){
        LOG.trace(info);
        LOG.debug(info);
        LOG.info(info);
        LOG.warn(info);
        LOG.error(info);
        LOG.fatal(info);
    }
}