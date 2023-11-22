package com.liuhuiyu.core.net;

import java.io.IOException;
import java.net.*;
import java.util.function.Consumer;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-11-22 19:47
 */
public class UdpServer {

    final int port;
    final InetAddress inetAddress;
    final Consumer<DatagramPacket> consumer;
    Consumer<IOException> ioException;
    boolean stop = false;

    public UdpServer(int port, String host, Consumer<DatagramPacket> operating) {
        try {
            inetAddress = InetAddress.getByName(host);
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.port = port;
        this.consumer = operating;
    }

    public void start() {
        try (DatagramSocket ds = new DatagramSocket(port, this.inetAddress)) {
            do {
                try {
                    byte[] buffer = new byte[UdpUtil.MAXIMUM_TRANSMISSION_LENGTH_LOCAL_AREA_NETWORK];
                    DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                    MyThread1 thread1 = new MyThread1(dp, consumer);
                    ds.receive(dp);
                    thread1.start();
                }
                catch (IOException e) {
                    if (ioException != null) {
                        ioException.accept(e);
                    }
                }
            } while (!this.stop);
        }
        catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public Consumer<IOException> getIoException() {
        return ioException;
    }

    public void setIoException(Consumer<IOException> ioException) {
        this.ioException = ioException;
    }

    public void stop() {
        this.stop = true;
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
