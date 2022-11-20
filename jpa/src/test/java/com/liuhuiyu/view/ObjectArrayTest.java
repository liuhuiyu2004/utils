package com.liuhuiyu.view;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-04-02 8:19
 */

public class ObjectArrayTest {
    private static final Logger LOG= LogManager.getLogger(ObjectArrayTest.class);

    @Test
    public void get() {
        Object object = new Object[]{1, 30_000_000_000L, "bbbb", true};
        ObjectArray objectArray = new ObjectArray(object);
        LOG.info("原始：i={};l={},s={},b={}", 1, 30_000_000_000L, "bbbb", true);
        int i = objectArray.getInteger();
        Long l = objectArray.getLong();
        String s = objectArray.getString();
        boolean b = objectArray.getBoolean();
        LOG.info("i l s b；i={};l={},s={},b={}", i, l, s, b);
        objectArray.reset();
        b = objectArray.getBoolean(true);
        i = objectArray.getInteger(18);
        l = objectArray.getLong(100L);
        s = objectArray.getString("NONE");
        LOG.info("b i l s i={};l={},s={},b={}", i, l, s, b);
        objectArray.reset();
        s = objectArray.getString("NONE");
        b = objectArray.getBoolean(true);
        i = objectArray.getInteger(18);
        l = objectArray.getLong(100L);
        LOG.info("s b i l i={};l={},s={},b={}", i, l, s, b);
        objectArray.reset();
        s = objectArray.getString("NONE");
        i = objectArray.getInteger(18);
        b = objectArray.getBoolean(true);
        l = objectArray.getLong(100L);
        LOG.info("s i b l i={};l={},s={},b={}", i, l, s, b);
    }

    @Test
    public void get2() {
        int r1 = 1_000_000_000;
        Object object = new Object[]{1, 30_000_000_000L, "bbbb", true};
        ObjectArray objectArray = new ObjectArray(object);
        int i = 0;
        Long l = 0L;
        String s = "";
        boolean b = false;
        for (int r = 0; r <= r1; r++) {
            objectArray.reset();
            i = objectArray.getInteger();
            l = objectArray.getLong();
            s = objectArray.getString();
            b = objectArray.getBoolean();
        }
        LOG.info("s i b l i={};l={},s={},b={}", i, l, s, b);
    }
}