package com.liuhuiyu.spring.scheduling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 功能<p>
 * Created on 2025/3/29 21:03
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@Component
public class DynamicCronTask {
    private static final Logger LOG = LogManager.getLogger(DynamicCronTask.class);
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    Map<String, ScheduledFuture<?>> scheduledFutureMap = new HashMap<>();

    /**
     * 注册<p>
     * LiuHuiYu
     *
     * @param name     注册名称
     * @param cron     cron表达式
     * @param runnable 运行方法
     */
    public void register(String name, String cron, Runnable runnable) {
        // 高版本使用 CronExpression，低版本使用 CronSequenceGenerator
        boolean validExpression = CronExpression.isValidExpression(cron);
        LOG.info("cron:[{}]是合法的吗:[{}]", cron, validExpression);

        CronExpression expression = CronExpression.parse(cron);
        LocalDateTime nextExecTime = expression.next(LocalDateTime.now());
        if (null != nextExecTime) {
            LOG.info("定时任务下次执行的时间为:[{}]", nextExecTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        final ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(runnable, new CronTrigger(cron));
        scheduledFutureMap.put(name, scheduledFuture);
    }

    /**
     * 取消注册<p>
     * LiuHuiYu
     *
     * @param name 注册名称
     * @return boolean  是否取消成功
     */
    public boolean unRegister(String name) {
        ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(name);
        if (null != scheduledFuture) {
            return scheduledFuture.cancel(false);
        }
        else {
            return false;
        }
    }
}
