package com.liuhuiyu.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-02-23 21:05
 */
public class IpUtil {
    public static final String UNKNOWN="unknown";
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (isNullIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isNullIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isNullIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isNullIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isNullIp(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public static boolean isNullIp(String ip){
        return ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip);
    }
}