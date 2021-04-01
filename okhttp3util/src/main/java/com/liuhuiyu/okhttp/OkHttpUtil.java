package com.liuhuiyu.okhttp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-09 15:06
 */
@Log4j2
public class OkHttpUtil {
    private final OkHttpClient client;
//    private final Request.Builder builder;
    /**
     * 保存body中的form-data、x-www-form-urlencoded 信息
     * Created DateTime 2021-03-29 22:00
     */
    private final Map<String, String> bodyMap;
    /**
     * 保存params信息
     * Created DateTime 2021-03-29 21:58
     */
    private final Map<String, String> paramMap;
    private final List<String[]> headerList;
    private String method;
    private Object tag;
    public static final String MEDIA_TYPE_APPLICATION_JSON_UTF_8 = "application/json;charset=utf-8";
    public static int CONNECT_TIMEOUT = 15;
    public static int READ_TIMEOUT = 15;
    public static int WRITE_TIMEOUT = 15;

    private OkHttpUtil(int connectTimeout, int readTimeout, int writeTimeout) {
        this.client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .build();
        this.paramMap = new HashMap<>(0);
        this.bodyMap = new HashMap<>(0);
        this.headerList = new ArrayList<>();
        this.method = "";
    }

    /**
     * 创建实例
     *
     * @return 实例
     */
    public static OkHttpUtil create() {
        return create(CONNECT_TIMEOUT, READ_TIMEOUT, WRITE_TIMEOUT);
    }

    /**
     * 创建实例
     *
     * @param connectTimeout 连接超时
     * @param readTimeout    读取超时
     * @param writeTimeout   写入超时
     * @return com.liuhuiyu.okhttp.OkHttpUtil
     * @author LiuHuiYu
     * Created DateTime 2021-03-30 9:39
     */
    public static OkHttpUtil create(int connectTimeout, int readTimeout, int writeTimeout) {
        return new OkHttpUtil(connectTimeout, readTimeout, writeTimeout);
    }

    public void setMethod(String value) {
        this.method = value == null ? "" : value.trim();
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    /**
     * 添加 body
     *
     * @param key   键值
     * @param value 数值
     * @return OkHttpUtil
     */
    public OkHttpUtil addBody(String key, String value) {
        this.bodyMap.put(key, value);
        return this;
    }

    /**
     * 添加uri参数
     *
     * @param key   键值
     * @param value 数值
     * @return OkHttpUtil
     */
    public OkHttpUtil addQueryParameter(String key, String value) {
        this.paramMap.put(key, value);
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
        this.headerList.add(new String[]{key, value});
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

    //region 生成基本数据
    private HttpUrl getHttpUrl(String url) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        this.paramMap.forEach(urlBuilder::addQueryParameter);
        return urlBuilder.build();
    }

    private RequestBody getRequestBody() {
        RequestBody body;
        if (this.bodyMap.size() > 0) {
            if ("".equals(this.method)) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                this.bodyMap.forEach(bodyBuilder::add);
                body = bodyBuilder.build();
            }
            else {
                Gson gson = new Gson();
                String jsonData = gson.toJson(bodyMap);
                body = RequestBody.create(jsonData, MediaType.parse(this.method));
            }
        }
        else {
            body = new FormBody.Builder().build();
        }
        return body;
    }

    private void addHeader(Request.Builder builder) {
        this.headerList.forEach(keyValue -> builder.addHeader(keyValue[0], keyValue[1]));
    }

    private void addTag(Request.Builder builder) {
        if (this.tag != null) {
            builder.tag(this.tag);
        }
    }
    //endregion
    //region post申请

    /**
     * 执行 post
     *
     * @param url 地址
     * @return Response
     */
    public Response executePost(String url) {
        try {
            HttpUrl httpUrl = this.getHttpUrl(url);
            RequestBody body = this.getRequestBody();

            Request.Builder builder = new Request.Builder();
            this.addHeader(builder);
            this.addTag(builder);
            builder.post(body);
            builder.url(httpUrl);
            /* builder.method(this.method,body); */
            Request request = builder.build();
            return this.client.newCall(request).execute();
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
        return getStringObjectMap(strJson);
    }
    //endregion

    //region get申请

    /**
     * @param url 地址
     * @return Response
     */
    public Response executeGet(String url) {
        try {
            HttpUrl httpUrl = this.getHttpUrl(url);
            Request.Builder builder = new Request.Builder();
            this.addHeader(builder);
            this.addTag(builder);
            builder.get();
            builder.url(httpUrl);
            Request request = builder.build();
            return this.client.newCall(request).execute();
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
        return getStringObjectMap(strJson);
    }
    //endregion

    //region put申请

    /**
     * @param url 地址
     * @return Response
     */
    public Response executePut(String url) {
        try {
            HttpUrl httpUrl = this.getHttpUrl(url);
            RequestBody body = this.getRequestBody();

            Request.Builder builder = new Request.Builder();
            this.addHeader(builder);
            this.addTag(builder);
            builder.put(body);
            builder.url(httpUrl);
            Request request = builder.build();
            return this.client.newCall(request).execute();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }

    }

    public String executePutToString(String url) {
        try {
            return Objects.requireNonNull(this.executePut(url).body()).string();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public Map<String, Object> executePutToMap(String url) {
        String strJson = this.executePutToString(url);
        return getStringObjectMap(strJson);
    }

    //endregion
    //region webSocket
    public static WebSocket webSocket(String url, WebSocketListener webSocketListener) {
        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();
        Request request = new Request.Builder().url(url).build();
        client.dispatcher().cancelAll();//清理一次

        return client.newWebSocket(request, webSocketListener);
    }
    //endregion

    /**
     * 将json字符串转换成Map
     *
     * @param strJson json字符串
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuHuiYu
     * Created DateTime 2021-03-30 9:04
     */
    @NotNull
    private Map<String, Object> getStringObjectMap(String strJson) {
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
