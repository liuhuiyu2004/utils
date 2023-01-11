package com.liuhuiyu.core.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-10 16:36
 */
public class Ping {
    /**
     * ping 指定地址
     *
     * @param ipAddress 地址
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-01-10 17:08
     */
    public static boolean ping(String ipAddress) {
        //超时应该在3钞以上
        int timeOut = 3000;
        try {
            return InetAddress.getByName(ipAddress).isReachable(timeOut);
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
     * @author LiuHuiYu
     * Created DateTime 2023-01-10 17:09
     */
    public static boolean windowsPing(String ipAddress, int pingTimes, int timeOut) {
        // 将要执行的ping命令,此命令是windows格式的命令
        Runtime r = Runtime.getRuntime();
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
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
            ex.printStackTrace();   // 出现异常则返回假
            return false;
        }
    }


    /**
     * 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
     * Created DateTime 2023-01-10 16:51
     */
    public static final String REGEX = "(\\d+ms)(\\s+)(TTL=\\d+)";

    /**
     * 返回信息检测
     *
     * @param line 返回信息
     * @return int
     * @author LiuHuiYu
     * Created DateTime 2023-01-10 16:53
     */
    private static int getCheckResult(String line) {
        Pattern pattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? 1 : 0;
    }
}
