package com.liuhuiyu.core.design.patterns.strategy;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

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

        LOG.info("执行：s2.runStrategy(true)");
        s2.runStrategy(true);
        LOG.info("执行：s2.runStrategy(false)");
        s2.runStrategy(false);
        LOG.info("执行：s.runStrategy(true)");
        s.runStrategy(true);
        LOG.info("执行：s.runStrategy(false)");
        s.runStrategy(false);
        StrategyRouter<Boolean, Void> f = new StrategyRouter<Boolean, Void>() {
            @Override
            public Function<Boolean, Void> registerStrategyMapper() {
                return (param) -> {
                    if (param) {
                        LOG.info("执行了 New：true");
                        return /*(Function<Boolean, Void>)*/ s2.runStrategy(param);
                    }
                    else {
                        LOG.info("执行了 New：false");
                        return s.runStrategy(param);
                    }
                };
            }
        };
        LOG.info("执行：new StrategyRouter(true)");
        f.runStrategy(true);
        LOG.info("执行：new StrategyRouter(false)");
        f.runStrategy(false);
        super.printObjectJson(f.getStrategyMapper());
    }

    static class S1 extends StrategyRouter<Boolean, Void> {
        @Override
        public Function<Boolean, Void> registerStrategyMapper() {
            return param -> {

                if (param) {
                    LOG.info("执行了S1：true");
                    return null;
                }
                else {
                    LOG.info("执行了S1：false");
                    new S2().runStrategy(param);
                }
                return null;
            };
        }
    }

    static class S2 extends StrategyRouter<Boolean, Void> {
        @Override
        public Function<Boolean, Void> registerStrategyMapper() {
            return param -> {
                if (param) {
                    LOG.info("执行了S2：true");
                }
                else {
                    LOG.info("执行了S2：false");
                }
                return null;
            };
        }
    }
}