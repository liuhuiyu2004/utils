package com.liuhuiyu.util.list;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-06-25 9:02
 */
class IfRunTest {
    @Test
    public void testIf() {
        Integer s = ThreadLocalRandom.current().nextInt(2);
        final Optional<Object> run = IfRun.ifRun(s)
                .ifRun(s.equals(1), this::ss)
                .ifRun(s.equals(0), this::ss2)
                .run();
    }

    public Void ss(){
        return null;
    }
    public Void ss2(){
        return null;
    }

}