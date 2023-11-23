package com.liuhuiyu.core.net.udp;

import com.liuhuiyu.core.thread.ThreadUtil;
import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-11-23 8:05
 */
class UdpServerTest extends AbstractTest {
    @Test
    public void test() {
        Consumer<DatagramPacket> operating = (v) -> {
            LOG.info("{};{};{}", v.getAddress(), v.getData().length, v.getLength());
            ThreadUtil.sleep(10_000);
            LOG.info("over:{};{};{}", v.getAddress(), v.getData().length, v.getLength());

            try {
                final int port = v.getPort();
                final InetAddress address = v.getAddress();
                DatagramSocket socket = new DatagramSocket();
                byte[] buf = "over".getBytes();
                //将数据打包
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
                socket.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        UdpServer server = new UdpServer(1234, "127.0.0.1", operating);
        server.start();
    }
}