package com.liuhuiyu.core.net.udp;

import com.liuhuiyu.core.net.UdpUtil;

import java.net.DatagramPacket;
import java.util.function.Consumer;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-11-23 7:49
 */
public class UdpThread extends Thread {
    byte[] buffer = new byte[UdpUtil.MAXIMUM_TRANSMISSION_LENGTH_LOCAL_AREA_NETWORK];

    private final DatagramPacket packet;
    private final Consumer<DatagramPacket> consumer;

    public UdpThread(Consumer<DatagramPacket> consumer) {
        this.packet = new DatagramPacket(buffer, buffer.length);
        this.consumer = consumer;
    }

    @Override
    public void run() {
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        consumer.accept(packet);
    }

    public DatagramPacket getPacket() {
        return packet;
    }
}
