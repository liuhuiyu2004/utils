package com.liuhuiyu.core.thread;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-12-01 10:36
 */
class ThreadPoolExecutorBuilderTest extends AbstractTest {
    @Test
    public void t1() {
        final ExecutorService builder = ThreadPoolExecutorBuilder.create().maxPoolSize(10).corePoolSize(3).builder();
        List<Future<?>> futureList = new ArrayList<>();
        for (int i = 0; i <= 20000; i++) {
            LOG.info("list->{}",i);
            final Integer index = i;
            futureList.add(builder.submit(() -> {
                        Thread thread = Thread.currentThread();
                        String name = thread.getName();
                        //输出
//                        int activeCount = builder.getActiveCount();
//                        System.out.println("任务："+j+"-----,线程名称:"+name+"-----活跃线程数:"+activeCount);


                    LOG.info("run:{}", index);
                ThreadUtil.sleep(3_000);
                LOG.info("runOver:{}", index);
//                return index;
            }));
        }
        LOG.info("OK");
        for (int i=futureList.size()-1;i>=0;i--) {
            try {
                final Object o = futureList.get(i).get();
                LOG.info("future OK {}",o);
            }
            catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        LOG.info("Over");
    }
}