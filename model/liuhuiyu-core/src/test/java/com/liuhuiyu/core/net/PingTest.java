package com.liuhuiyu.core.net;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-10 16:38
 */
class PingTest extends TestBase {

    @DisplayName("ping ip")
    @ParameterizedTest()
    @MethodSource("ipGenerator")
    public void ping(String ipAddress) throws Exception {
        final boolean ping = Ping.ping(ipAddress);
        LOG.info("ping:{} {}", ipAddress, ping);
        final boolean ping2 = Ping.windowsPing(ipAddress, 5, 5000);
        LOG.info("windows ping:{} {}", ipAddress, ping2);
    }

    static Stream<String> ipGenerator() {
        List<String> list = new ArrayList<>();
        list.add("127.0.0.1");
        list.add("128.0.0.1");
        return list.stream();
    }
}