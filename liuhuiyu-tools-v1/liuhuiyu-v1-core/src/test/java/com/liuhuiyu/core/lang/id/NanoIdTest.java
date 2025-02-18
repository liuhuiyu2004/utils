package com.liuhuiyu.core.lang.id;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-04 16:17
 */
class NanoIdTest extends AbstractTest {
    @Test
    public void id() {
        int i = 100;
        for (int i1 = 0; i1 < i; i1++) {
            final String s = NanoId.randomNanoId(30);
            LOG.info("{}", s);
        }
    }

    @Test
    public void id1() {
        int i = 100;
        for (int i1 = 0; i1 < i; i1++) {
            final String s = NanoId.randomNanoId(NanoId.NUMBER, 30);
            LOG.info("{}", s);
        }
    }

    @Test
    public void id2() {
        int i = 100;
        for (int i1 = 0; i1 < i; i1++) {
            final String s = NanoId.randomNanoId(NanoId.LOWERCASE_LETTERS,30);
            LOG.info("{}", s);
        }
    }
    @Test
    public void id3() {
        int i = 100;
        for (int i1 = 0; i1 < i; i1++) {
            final String s = NanoId.randomNanoId(NanoId.UPPERCASE_LETTERS,30);
            LOG.info("{}", s);
        }
    }
}