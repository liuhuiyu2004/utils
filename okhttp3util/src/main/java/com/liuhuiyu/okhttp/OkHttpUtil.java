package com.liuhuiyu.okhttp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-09 15:06
 */
public class OkHttpUtil {
    private final OkHttpClient client;
    private final Request.Builder builder;
    private final FormBody.Builder body;
    private final Map<String, String> queryParameterMap;
    public static int CONNECT_TIMEOUT = 15;
    public static int READ_TIMEOUT = 15;
    public static int WRITE_TIMEOUT = 15;

    private OkHttpUtil() {
        this.client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new CommonHeaderInterceptor())
//                .addInterceptor(new CacheInterceptor())
//                .addInterceptor(new HttpLoggerInterceptor())
//                .addNetworkInterceptor(new EncryptInterceptor())
                .build();
        this.builder = new Request.Builder();
        this.body = new FormBody.Builder();
        this.queryParameterMap = new HashMap<>(0);
    }

    /**
     * 创建实例
     *
     * @return 实例
     */
    public static OkHttpUtil create() {
        return new OkHttpUtil();
    }

    /**
     * 添加地址
     *
     * @param url 地址
     * @return OkHttpUtil
     */
    @Deprecated
    public OkHttpUtil addUrl(String url) {
        this.builder.url(url);
        return this;
    }

    /**
     * 添加 body
     *
     * @param key   键值
     * @param value 数值
     * @return OkHttpUtil
     */
    public OkHttpUtil addBody(String key, String value) {
        this.body.add(key, value);
        return this;
    }

    /**
     * 配合 get 使用
     *
     * @param key   键值
     * @param value 数值
     * @return OkHttpUtil
     */
    public OkHttpUtil addQueryParameter(String key, String value) {
        this.queryParameterMap.put(key, value);
        return this;
    }

    /**
     * 添加Header
     *
     * @param key   键值
     * @param value 数值
     * @return OkHttpUtil
     */
    public OkHttpUtil addHeader(String key, String value) {
        this.builder.addHeader(key, value);
        return this;
    }

    /**
     * 加入认证
     *
     * @param username 认证账号
     * @param password 认证密码
     * @return OkHttpUtil
     */
    public OkHttpUtil headerAuthorizationByBasicAuth(String username, String password) {
        String base64AppMsg;
        String appMsg = username + ":" + password;
        base64AppMsg = Base64.getEncoder().encodeToString(appMsg.getBytes(StandardCharsets.UTF_8));
        return this.addHeader("Authorization", "Basic " + base64AppMsg);
    }

    public OkHttpUtil headerAuthorizationByBearerToken(String token) {
        return this.addHeader("Authorization", "Bearer " + token);
    }

    //region post申请

    /**
     * 执行 post
     *
     * @param url 地址
     * @return Response
     */
    public Response executePost(String url) {
        try {
            this.builder.url(url);
            return this.client.newCall(this.builder.post(body.build()).build()).execute();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public String executePostToString(String url) {
        try {
            return Objects.requireNonNull(this.executePost(url).body()).string();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public Map<String, Object> executePostToMap(String url) {
        String strJson = this.executePostToString(url);
        try {
            Gson gson = new Gson();
            return gson.fromJson(strJson, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
        catch (JsonSyntaxException e) {
            throw new OkHttpException(e.getMessage());
        }
    }
    //endregion

    //region get申请

    /**
     * @param url 地址
     * @return Response
     */
    public Response executeGet(String url) {
        try {
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
            this.queryParameterMap.forEach(urlBuilder::addQueryParameter);
            return this.client.newCall(this.builder.url(urlBuilder.build()).build()).execute();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public String executeGetToString(String url) {
        try {
            return Objects.requireNonNull(this.executeGet(url).body()).string();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public Map<String, Object> executeGetToMap(String url) {
        String strJson = this.executeGetToString(url);
        try {
            Gson gson = new Gson();
            Map<String, Object> resultMap = gson.fromJson(strJson, new TypeToken<Map<String, Object>>() {
            }.getType());
            return mapDoubleToInt(resultMap);
        }
        catch (JsonSyntaxException e) {
            throw new OkHttpException(e.getMessage());
        }
    }
    //endregion

    public static Map<String, Object> mapDoubleToInt(Map<?, ?> resultMap) {
        Map<String, Object> res = new HashMap<>(resultMap.size());
        for (Object keyObj : resultMap.keySet()) {
            String key = keyObj.toString();
            if (resultMap.get(key) instanceof Double) {
                Double value = (Double) resultMap.get(key);
                if (value.intValue() == value) {
                    res.put(key, ((Double) resultMap.get(key)).intValue());
                }
                else {
                    res.put(key, resultMap.get(key));
                }
            }
            else if (resultMap.get(key) instanceof List<?>) {
                res.put(key, listDoubleToInt((List<?>) resultMap.get(key)));
            }
            else if (resultMap.get(key) instanceof Map<?, ?>) {
                res.put(key, mapDoubleToInt((Map<?, ?>) resultMap.get(key)));
            }
            else {
                res.put(key, resultMap.get(key));
            }
        }
        return res;
    }

    public static List<Object> listDoubleToInt(List<?> list) {
        List<Object> res = new ArrayList<>(list.size());
        for (Object o : list) {
            if (o instanceof Number) {
                Double value = (Double) o;
                if (value.intValue() == value) {
                    Object v = value.intValue();
                    res.add(v);
                }
                else {
                    res.add(value);
                }
            }
            else if (o instanceof Map<?, ?>) {
                res.add(mapDoubleToInt((Map<?, ?>) o));
            }
            else if (o instanceof List<?>) {
                res.add(listDoubleToInt((List<?>) o));
            }
            else {
                res.add(o);
            }
        }
        return res;
    }
}
