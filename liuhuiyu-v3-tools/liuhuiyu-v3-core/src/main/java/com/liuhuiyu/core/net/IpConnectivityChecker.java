package com.liuhuiyu.core.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查指定IP地址的连通性
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/6/29 9:59
 */
public class IpConnectivityChecker {

    /**
     * 检查指定IP地址的连通性。
     *
     * @param ipAddress 要检查的IP地址
     * @param port      尝试连接的端口号，默认为80
     * @return 如果连接成功返回true，否则返回false
     */
    public static boolean isHostAvailable(String ipAddress, int port) {
        IpConnectivityChecker instance = new IpConnectivityChecker(ipAddress);
        instance.setPort(port);
        return instance.isHostAvailable();
    }

    /**
     * ping 指定地址
     *
     * @param ipAddress 地址
     * @return boolean
     */
    public static boolean ping(String ipAddress) {
        IpConnectivityChecker instance = new IpConnectivityChecker(ipAddress);
        return instance.ping();
    }

    public IpConnectivityChecker(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    //region 参数
    private String ipAddress;
    private int port = 80;
    int timeOut = 3000;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    //endregion
    private boolean isHostAvailable() {
        try (Socket socket = new Socket()) {
            // 设置超时时间，避免长时间等待
            socket.setSoTimeout(this.timeOut); // 3秒超时
            InetSocketAddress address = new InetSocketAddress(this.ipAddress, this.port);
            // 尝试连接
            socket.connect(address, this.timeOut);
            return true;
        }
        catch (IOException e) {
            // 连接失败或超时会抛出异常
            return false;
        }
    }

    /**
     * ping 指定地址
     *
     * @return boolean
     */
    public boolean ping() {
        try {
            return InetAddress.getByName(this.ipAddress).isReachable(this.timeOut);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用windows命令 ping 指定地址
     *
     * @param ipAddress 地址
     * @param pingTimes ping次数
     * @param timeOut   超时时间
     * @return boolean
     */
    public static boolean windowsPing(String ipAddress, int pingTimes, int timeOut) {
        IpConnectivityChecker instance = new IpConnectivityChecker(ipAddress);
        return instance.windowsPing(pingTimes);
    }

    /**
     * 使用windows命令 ping 指定地址
     *
     * @param pingTimes ping次数
     * @return boolean
     */
    public boolean windowsPing(int pingTimes) {
        // 将要执行的ping命令,此命令是windows格式的命令
        Runtime r = Runtime.getRuntime();
        String pingCommand = "ping " + this.ipAddress + " -n " + pingTimes + " -w " + this.timeOut;
        // 执行命令并获取输出
        Process p;
        try {
            p = r.exec(pingCommand);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (p == null) {
            return false;
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            int connectedCount = 0;
            String line;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        }
        catch (Exception ex) {
            return false;
        }
    }


    /**
     * 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
     * Created DateTime 2023-01-10 16:51
     */
    private static final String REGEX = "(\\d+ms)(\\s+)(TTL=\\d+)";

    /**
     * 返回信息检测
     *
     * @param line 返回信息
     * @return int

     * Created DateTime 2023-01-10 16:53
     */
    private static int getCheckResult(String line) {
        Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? 1 : 0;
    }

}
