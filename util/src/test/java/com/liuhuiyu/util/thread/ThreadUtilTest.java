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
        for (int i = 0; i < 100; i++) {
            List<Integer> list = new ArrayList<>(5);
            Collections.addAll(list, 5, 1, 6, 4, 3, 2, 7, 5, 6);
            ThreadUtil.asynchronousDataLoading(list, this::testExe);
        }
    }

    @Test
    public void asynchronousDataLoading2() {
//        for (int i = 0; i < 100; i++) {
            List<Integer> list = new ArrayList<>(5);
            Collections.addAll(list, 5, 1, 6, 4, 3, 2, 7, 5, 6);
            ThreadUtil.asynchronousDataLoading(list, this::testExe, ExecutorBuilder.create().corePoolSize(list.size()).threadName("test-").builder());
//        }
//        try {
//            Thread.sleep(20000);
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    private void testExe(Integer i) {
        try {
            log.info("开始：{}", i);
            Thread.sleep(i * 1000);
//            if (i == 3) {
//                throw new RuntimeException("3错误。");
//            }
            log.info("完成：{}", i);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}