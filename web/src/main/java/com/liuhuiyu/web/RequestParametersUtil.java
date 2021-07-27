package com.liuhuiyu.web;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-20 13:53
 */
public class RequestParametersUtil {
    public static final String GET = "GET";
    public static final String POST = "POST";

    /**
     * 获取request中参数
     *
     * @param request 页面请求
     */
    public static Map<String, Object> getRequestParameters(HttpServletRequest request) {
        //请求参数
        String parameters = "";
        //GET请求时的参数
        if (GET.equals(request.getMethod())) {
            //网址中的参数
            String urlParameter = request.getQueryString();
            if (urlParameter != null && !"".equals(urlParameter)) {
                try {
                    urlParameter = URLDecoder.decode(urlParameter, "UTF-8");
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            else {
                urlParameter = "";
            }
            parameters = urlParameter;
        }
        //POST请求时的参数
        else if (POST.equals(request.getMethod())) {
            //表单及网址中全部参数
            StringBuilder totalParameter = new StringBuilder();
            Map<String, String[]> params = request.getParameterMap();
            //参数个数
            int parametersNum = request.getParameterMap().size();
            int flag = 1;
            for (String key : params.keySet()) {

                String[] values = params.get(key);
                for (String value : values) {
                    totalParameter.append(key).append("=").append(value);
                }
                if (flag < parametersNum) {
                    totalParameter.append("&");
                }
                flag += 1;
            }
            parameters = totalParameter.toString();
        }
        Map<String, Object> map = new HashMap<>(0);
        String[] arr = parameters.split("&");
        for (String s : arr) {
            int index = s.indexOf("=");
            //=不能是首位或者是末尾
            if (index < 1 || index >= (s.length() - 1)) {
                break;
            }
            String key = s.substring(0, index);
            String value = s.substring(index + 1);
            map.put(key, value);
        }
        return map;
    }
}
