package com.liuhuiyu.core.design.patterns.strategy;

import java.util.function.Function;

/**
 * 策略路由器
 *
 * @param <P> 路由参数
 * @param <R> 路由结果
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-04-07 17:11
 */
public abstract class StrategyRouter<P, R> {
    public StrategyRouter() {
        this.abstractInit();
    }

    /**
     * 策略的映射器，根据入参来路由到指定的策略执行者
     *
     * @param <P>
     * @param <R>
     */
    public interface StrategyMapper<P, R> {

        /**
         * 根据入参获取到对应的策略执行者
         *
         * @param param 入参
         * @return 具体的执行者
         */
        Function<P, R> get(P param);
    }

    /**
     * 在类初始化时，会调用初始化类（也就是实现类）的registerStrategyMapper()方法获取到接口的实现
     */
    private StrategyMapper<P, R> strategyMapper;

    /**
     * 类初始化时注册分发策略 Mapper
     */
    private void abstractInit() {
        //获取到StrategyMapper接口的实现
        strategyMapper = registerStrategyMapper();
        if (strategyMapper == null) {
            throw new NullPointerException("未找到策略分发者");
        }
    }

    /**
     * 执行审批方法
     *
     * @param param 入参
     * @return 返回值
     */
    public R strategyApprove(P param) {
        //调用实现StrategyMapper接口的匿名类的get方法，此时get方法会根据入参，返回对应的策略执行者，再调用approve方法
        return strategyMapper.get(param).apply(param);
    }

    /**
     * 分发策略的具体实现
     *
     * @return 策略映射器
     */
    public abstract StrategyMapper<P, R> registerStrategyMapper();

    public StrategyMapper<P, R> getStrategyMapper() {
        return strategyMapper;
    }
}
