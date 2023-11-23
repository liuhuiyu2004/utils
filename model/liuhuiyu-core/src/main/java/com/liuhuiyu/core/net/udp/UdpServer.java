package com.liuhuiyu.core.net.udp;

import com.liuhuiyu.core.thread.ThreadPoolExecutorBuilder;
import com.liuhuiyu.core.util.Assert;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-11-22 19:47
 */
public class UdpServer {
    private final int port;
    private final InetAddress inetAddress;
    private final Consumer<DatagramPacket> consumer;
    private Consumer<IOException> ioException;
    private boolean stop = false;
    private ExecutorService executorService;

    public UdpServer(int port, String host, Consumer<DatagramPacket> operating) {
        try {
            inetAddress = InetAddress.getByName(host);
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.port = port;
        this.consumer = operating;
        this.executorService = ThreadPoolExecutorBuilder.create()
                .threadName("udp-server")
                .corePoolSize(50)
                .keepAliveSeconds(5)
                .maxPoolSize(Integer.MAX_VALUE).builder();
    }

    public void start() {
        try (DatagramSocket ds = new DatagramSocket(port, this.inetAddress)) {
            do {
                try {
                    UdpThread thread1 = new UdpThread(consumer);
                    ds.receive(thread1.getPacket());
                    if (!this.stop) {
                        this.executorService.execute(thread1);
                    }
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

    public void setIoException(Consumer<IOException> ioException) {
        Assert.assertFalse(stop, "服务启动中禁止修改异常接口。");
        this.ioException = ioException;
    }

    public void setExecutorService(ExecutorService executorService) {
        Assert.assertFalse(stop, "服务启动中禁止修改线程池。");
        this.executorService = executorService;
    }

    public void stop() {
        this.stop((ex) -> {
        });
    }

    public void stop(Consumer<IOException> ioException) {
        this.stop = true;
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = "over".getBytes();
            //将数据打包
            DatagramPacket packet = new DatagramPacket(buf, buf.length, inetAddress, port);
            socket.send(packet);
            socket.close();
        }
        catch (IOException ex) {
            ioException.accept(ex);
        }
    }
}
