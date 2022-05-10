package com.liuhuiyu.util.crontab;

/**
 * 定时任务注册接口
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-10 18:18
 */
public interface CrontabReg {
    /**
     * 定时任务注册接口
     *
     * @param name          任务名称
     * @param executionTask 定时任务
     * @author LiuHuiYu
     * Created DateTime 2022-05-10 18:24
     */
    void reg(String name, BaseExecutionTask executionTask);
}
