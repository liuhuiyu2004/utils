package com.liuhuiyu.core.design.patterns.strategy;

import com.google.gson.Gson;
import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-04-07 17:19
 */
class StrategyRouterTest extends TestBase {
    @Test
    public void t1() {
        S1 s = new S1();
        S2 s2 = new S2();

        LOG.info("执行：s2.strategyApprove(true)");
        s2.strategyApprove(true);
        LOG.info("执行：s2.strategyApprove(false)");
        s2.strategyApprove(false);
        LOG.info("执行：s.strategyApprove(true)");
        s.strategyApprove(true);
        LOG.info("执行：s.strategyApprove(false)");
        s.strategyApprove(false);
        StrategyRouter<Boolean, Void> f= new StrategyRouter<Boolean, Void>() {
            @Override
            public StrategyMapper<Boolean, Void> registerStrategyMapper() {
                return (param)-> {
                    if (param) {
                        LOG.info("执行了 New：true");
                        return (Function<Boolean, Void>) s::strategyApprove;
                    }
                    else {
                        LOG.info("执行了 New：false");
                        return (Function<Boolean, Void>) s::strategyApprove;
                    }
                };
            }
        };
        LOG.info("执行：new StrategyRouter(true)");
        f.strategyApprove(true);
        LOG.info("执行：new StrategyRouter(false)");
        f.strategyApprove(false);
        super.printObjectJson( f.getStrategyMapper());
    }

    static class S1 extends StrategyRouter<Boolean, Void> {
        @Override
        public StrategyMapper<Boolean, Void> registerStrategyMapper() {
            return param -> {
                if (param) {
                    LOG.info("执行了S1：true");
                    return param1 -> null;
                }
                else {
                    LOG.info("执行了S1：false");
                    return (Function<Boolean, Void>) param12 -> new S2().strategyApprove(param12);
                }
            };
        }
    }

    static class S2 extends StrategyRouter<Boolean, Void> {
        @Override
        public StrategyMapper<Boolean, Void> registerStrategyMapper() {
            return param -> {
                if (param) {
                    LOG.info("执行了S2：true");
                }
                else {
                    LOG.info("执行了S2：false");
                }
                return param1 -> null;
            };
        }
    }
}