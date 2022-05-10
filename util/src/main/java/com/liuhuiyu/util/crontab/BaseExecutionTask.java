package com.liuhuiyu.util.crontab;

import com.liuhuiyu.util.thread.ExecutorBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时执行任务基础类
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-03-29 10:36
 */
public abstract class BaseExecutionTask {
    private static final Logger LOG= LogManager.getLogger(BaseExecutionTask.class);
    static final ThreadPoolTaskExecutor THREAD_POOL = ExecutorBuilder.create().maxPoolSize(30).threadName("task-loop-").builder();

    private ScheduledExecutorService ses;
    private Runnable runnable;
    private long initialDelay;
    private long period;
    private TimeUnit unit;
    private ScheduledFuture<?> scheduledFuture;

    public BaseExecutionTask(CrontabReg appStartupRunner, Boolean ignore) {
        if (ignore) {
            return;
        }
        LOG.debug("注册自己的名称：{}", this.getClass().getName());
        appStartupRunner.reg(this.getClass().getName(), this);
        this.runnable = () -> {
            try {
                this.execution();
            }
            catch (Exception e) {
                LOG.error("定时任务执行失败", e);
            }
        };
        this.initialDelay = 0;
        this.period = 5;
        this.unit = TimeUnit.SECONDS;
        this.ses = new ScheduledThreadPoolExecutor(1, THREAD_POOL);
    }

    /**
     * 启动
     *
     * @author LiuHuiYu
     * Created DateTime 2022-03-29 10:45
     */
    public void run() {
        LOG.debug("启动{}", this.getClass().getName());
        if (period > 0) {
            this.scheduledFuture = this.ses.scheduleWithFixedDelay(runnable, initialDelay, period, unit);
        }
    }

    /**
     * 停止服务
     *
     * @author LiuHuiYu
     * Created DateTime 2022-03-29 15:53
     */
    public void stop() {
        this.scheduledFuture.cancel(true);
    }

    /**
     * 设定定时执行参数
     *
     * @param initialDelay 第一次执行时间
     * @param period       执行周期
     * @param unit         周期单位
     * @author LiuHuiYu
     * Created DateTime 2022-03-29 10:54
     */
    protected void setScheduleWithFixedDelayParameter(long initialDelay, long period, TimeUnit unit) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
    }

    /**
     * 执行任务
     *
     * @author LiuHuiYu
     * Created DateTime 2022-03-29 10:46
     */
    protected abstract void execution();
}
