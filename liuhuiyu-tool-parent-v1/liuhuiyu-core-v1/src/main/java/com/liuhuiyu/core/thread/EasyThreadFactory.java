package com.liuhuiyu.core.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简易线程工厂
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-22 22:45
 */
public class EasyThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public EasyThreadFactory() {
        this("");
    }

    public EasyThreadFactory(String namePrefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        if (namePrefix == null || namePrefix.trim().length() == 0) {
            this.namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }
        else {
            this.namePrefix = namePrefix;
        }
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
