package com.liuhuiyu.core.net;

import com.liuhuiyu.core.thread.ThreadPoolExecutorBuilder;
import com.liuhuiyu.core.thread.ThreadUtil;
import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-10 16:38
 */
class PingTest extends AbstractTest {

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

    @Test
    public void s() throws UnknownHostException, SocketException {
        final ExecutorService builder = ThreadPoolExecutorBuilder.create().builder();
        DatagramSocket ds = null; // 监听指定端口
        int udpPort = 17888;
        String udpHost = "127.0.0.1";
        ds = new DatagramSocket(udpPort, InetAddress.getByName(udpHost));
        for (; ; ) { // 无限循环
            // 数据缓冲区:
            byte[] buffer = new byte[11];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                MyThread1 myThread1 = new MyThread1(packet, (v) -> {
                });
                ds.receive(packet); // 收取一个UDP数据包
                myThread1.start();
            }
            catch (IOException e) {
                LOG.error("接受消息错误", e);
            }
//            byte[] bytes = packet.getData();
            //byte[] bytes = {2, 3, 4, 5, 0, 17, 0, 1, 65, -31, 1, 0, 0, 7, -3, 78, -107}; //接收到的数据
            //02 03 04 05 00 FC 00 01 41 97 30 00 00 07 F8 AB 00 00 07 F9 C9 00 00 07 FA E3 00 00 07 FF AC 00 00 08 01 CD 00 00 07 FE F7 00 00 07 F8 C0 00 00 07 FE 6E 00 00 07 FB FD 00 00 07 FE CF 00 00 08 01 47 00 00 08 01 62 00 00 07 FF 40 00 00 07 F9 B9 00 00 07 FB 78 00 00 07 FE 3B 00 00 07 FE D1 00 00 07 FD 50 00 00 08 00 90 00 00 07 FF 07 00 00 07 FB F6 00 00 07 FE 25 00 00 08 00 A8 00 00 07 FE AB 00 00 08 01 A8 00 00 07 FF 0D 00 00 07 FE A9 00 00 07 FE F2 00 00 07 FF 8E 00 00 07 FB 2B 00 00 07 FB A9 00 00 08 01 39 00 00 07 F8 A4 00 00 07 FE 5C 00 00 07 FA E8 00 00 07 FE 07 00 00 07 F8 B2 00 00 07 FD 40 00 00 07 FB AE 00 00 07 FD 23 00 00 07 FC 0D 00 00 07 FD 00 00 00 07 FE 3D 00 00 07 F9 CC 00 00 07 FC 30 00 00 07 FF 84 00 00 07 FE F1 00 00 07 F9 07 70
            //bytes[10]:第11个字节为卡号数量
            // 17字节udp数据包为卡号数据
//            if (bytes[10] > 0) {
//                taskExecutor.execute(() -> {
//                    //int rfidCount = bytes[10];
//                    String rfidCountHex = String.format("%02X", bytes[10]);
//                    int rfidCountDec = Integer.parseInt(rfidCountHex, 16);
//                    if (rfidCountDec > 0) {
//                        String host = packet.getAddress().getHostAddress();
//                        String[] hexStrArr = toHexStrArr(bytes);
//                        //String bytesLen = hexStrArr[4] + hexStrArr[5];
//                        for (int i = 0; i < rfidCountDec; i++) {
//                            String rfidCardNumHexStr = "" +
//                                    hexStrArr[11 + 5 * i] +
//                                    hexStrArr[11 + 5 * i + 1] +
//                                    hexStrArr[11 + 5 * i + 2] +
//                                    hexStrArr[11 + 5 * i + 3] +
//                                    hexStrArr[11 + 5 * i + 4];
//                            int rfidCardNumDec = Integer.parseInt(rfidCardNumHexStr, 16); //523598
//                            //填充rfidInfo
//                            UdpRfidHandleFirst udpRfidHandleFirst = fullRfidInfo(host, rfidCardNumDec);
//                            log.debug("数据包:{}", udpRfidHandleFirst);
//                            //一顿分析确认实际所在地(排除多个设备干扰,主要看是否经过楼道这种标志性地点)
//                            UdpRfidHandleFirst target = this.confirmActualLocation(udpRfidHandleFirst);
//                            //发送到消息队列
//                            rabbitTemplate.convertAndSend(
//                                    DirectRabbitConfig.getExchangeName(),
//                                    DirectRabbitConfig.getRoutingKey(),
//                                    JSONUtil.toJsonStr(target));
//                        }
//                    }
//                });
//            }
        }
    }

    @Test
    public void t1() throws Exception {
        test((packet) -> {
//            LOG.info("begin:{},{},{}", packet.getAddress().getHostAddress(), packet.getData().length, packet.getLength());
            ThreadUtil.sleep(600_000);
            LOG.info("end:{},{},{}", packet.getAddress().getHostAddress(), packet.getData().length, packet.getLength());
        });
    }

    public void test(Consumer<DatagramPacket> consumer) throws UnknownHostException, SocketException {
        DatagramSocket ds = null; // 监听指定端口
        int udpPort = 17888;
        String udpHost = "127.0.0.1";
        ds = new DatagramSocket(udpPort, InetAddress.getByName(udpHost));
        int index = 0;

//        DatagramPacket[] list = new DatagramPacket[10];
//        MyThread1[] myThreadList = new MyThread1[list.length];

//        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        for (; ; ) { // 无限循环
            // 数据缓冲区:
//            for (int i1 = 0; i1 < list.length; i1++) {
//                list[i1] = new DatagramPacket(buffer, buffer.length);
//                myThreadList[i1] = new MyThread1(list[i1], consumer);
//            }
//            for (int i1 = 0; i1 < list.length; i1++) {
            try {
//                    ds.receive(list[i1]);
                byte[] buffer = new byte[1500];
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                MyThread1 thread1 = new MyThread1(dp, consumer);
                ds.receive(dp);
                LOG.info("{}", index++);
//                    myThreadList[i1].start();
                thread1.start();
            }
            catch (IOException e) {
                LOG.error("接受消息错误", e);
            }
//            }

//            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//            try {
//                MyThread1 myThread1 = new MyThread1(packet, consumer);
//                ds.receive(packet); // 收取一个UDP数据包
//                myThread1.start();
//            }
//            catch (IOException e) {
//                LOG.error("接受消息错误", e);
//            }
        }
    }

    static class MyThread1 extends Thread {

        private final DatagramPacket packet;
        private final Consumer<DatagramPacket> consumer;

        public MyThread1(DatagramPacket packet, Consumer<DatagramPacket> consumer) {
            this.packet = packet;
            this.consumer = consumer;
        }

        public void run() {
            consumer.accept(packet);
        }
    }
}