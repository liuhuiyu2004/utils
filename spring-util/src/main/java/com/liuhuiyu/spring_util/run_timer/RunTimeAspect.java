package com.liuhuiyu.spring_util.run_timer;


import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-02 11:56
 */
@Aspect
@Component
@Log4j2
public class RunTimeAspect {
    @Around(value = "@annotation(timer)")
    public Object logAround(ProceedingJoinPoint pjp, RunTimer timer) throws Throwable {
        TimerUtil tu = new TimerUtil();
        return tu.runTime(pjp, timer);
    }
}
