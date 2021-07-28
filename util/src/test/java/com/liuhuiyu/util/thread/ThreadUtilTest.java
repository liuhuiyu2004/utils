package com.liuhuiyu.util.thread;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-17 10:36
 */
@Log4j2
public class ThreadUtilTest {
    @Test
    public void asynchronousDataLoading() {
            List<Integer> list = new ArrayList<>(5);
            Collections.addAll(list, 5, 1, 6, 4, 3, 2, 7, 5, 6);
            ThreadUtil.asynchronousDataLoading(list, this::testExe);
    }

    @Test
    public void asynchronousDataLoading2() {
        List<Integer> list = new ArrayList<>(5);
        Collections.addAll(list, 5, 1, 6, 4, 3, 2, 7, 5, 6);
        ThreadUtil.asynchronousDataLoading(list, this::testExe, ExecutorBuilder.create().corePoolSize(list.size()).threadName("test-").builder());
    }


    private void testExe(Integer i) {
        try {
            log.info("开始：{}", i);
            Thread.sleep(i * 1000);
            log.info("完成：{}", i);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void s() {
        List<String> list = new ArrayList<String>() {{
            this.add("1");
            this.add("2");
            this.add("3");
            this.add("3");
            this.add("32");
        }};
        String[] array = list.toArray(new String[0]);
        log.info("{},{}", list,list.size());
        log.info(":{};{};", array, array.length);
    }
}