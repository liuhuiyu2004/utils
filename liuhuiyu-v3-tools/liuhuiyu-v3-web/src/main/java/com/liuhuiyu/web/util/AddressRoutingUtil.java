package com.liuhuiyu.web.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 功能<p>
 * Created on 2025/3/31 21:01
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class AddressRoutingUtil {
    /**
     * 获取地址
     *
     * @param request request
     * @param root    根目录
     * @param path    网页路径
     * @return 可以访问的地址
     */
    public static String getFullAddress(HttpServletRequest request, String root, String path) {
        return getFullAddress(request, root, path, null);
    }

    /**
     * 获取地址
     *
     * @param request   request
     * @param root      根目录
     * @param path      网页路径
     * @param parameter 参数对
     * @return 可以访问的地址
     */
    public static String getFullAddress(HttpServletRequest request, String root, String path, Map<String, Object> parameter) {
        String foot = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String address;
        address = foot + root + path;

        if (null == parameter) {
            return address;
        } else {
            String linkSymbol = "?";
            for (String key : parameter.keySet()) {
                address = String.format("%1$s%2$s%3$s=%4$s", address, linkSymbol, key, parameter.get(key).toString());
                linkSymbol = "&";
            }
            return address;
        }
    }
}
