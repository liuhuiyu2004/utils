package com.liuhuiyu.core.net;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/6/29 9:59
 */
class IpConnectivityCheckerTest extends AbstractTest {
    @Test
    public void test() {
        final boolean hostAvailable = IpConnectivityChecker.isHostAvailable("10.20.6.95", 10000);
        LOG.info("hostAvailable:{}",hostAvailable);
        final boolean ping = IpConnectivityChecker.ping("10.20.6.95");
        LOG.info("ping:{}",ping);
    }

}