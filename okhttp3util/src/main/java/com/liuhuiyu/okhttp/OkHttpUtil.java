package com.liuhuiyu.okhttp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.*;
import java.util.concurrent.TimeUnit;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-09 15:06
 */
public class OkHttpUtil {
    private final OkHttpClient.Builder client;
//    private final Request.Builder builder;
    /**
     * 保存body中的form-data、x-www-form-urlencoded 信息
     * Created DateTime 2021-03-29 22:00
     */
//    private final Map<String, String> bodyMap;
    private final List<String[]> bodyMap;
    /**
     * 保存params信息
     * Created DateTime 2021-03-29 21:58
     */
    private final Map<String, String> paramMap;
    private final List<String[]> headerList;
    private String method;
    private String bodyString = "";
    private Object tag;
    public static final String MEDIA_TYPE_APPLICATION_JSON_UTF_8 = "application/json;charset=utf-8";
    public static final int CONNECT_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;

    private OkHttpUtil(int connectTimeout, int readTimeout, int writeTimeout) {
        this.client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS));
        //.build();
        this.paramMap = new HashMap<>(0);
//        this.bodyMap = new HashMap<>(0);
        this.bodyMap = new ArrayList<>(0);
        this.headerList = new ArrayList<>();
        this.method = "";
    }
//    public void https(){
//        this.client.hostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        }).sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
//    }

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

    public void setBodyString(String bodyString) {
        this.bodyString = bodyString;
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
        this.bodyMap.add(new String[]{key, value});
//        this.bodyMap.put(key, value);
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
//            if ("".equals(this.method)) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
//                this.bodyMap.forEach(bodyBuilder::add);
            this.bodyMap.forEach((infos) -> bodyBuilder.add(infos[0], infos[1]));
            body = bodyBuilder.build();
//                bodyBuilder.add("a", "1");
//            }
//            else {
//                Gson gson = new Gson();
//                String jsonData = gson.toJson(bodyMap);
//                body = RequestBody.create(jsonData, MediaType.parse(this.method));
//            }
        }
        else if (StringUtils.isNotBlank(this.bodyString)) {
            MediaType mediaType = MediaType.parse(this.method);
//            body = RequestBody.create(MediaType.parse(this.method),this.bodyString);
            body = RequestBody.create(mediaType, this.bodyString);
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
            return this.client.build().newCall(request).execute();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public String executePostToString(String url) {
        try (Response response = this.executePost(url)) {
            return Objects.requireNonNull(response.body()).string();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public Map<String, Object> executePostToMap(String url) {
        String strJson = this.executePostToString(url);
        return getStringObjectMap(strJson);
    }

    public List<Object> executePostToList(String url) {
        return executeList(() -> this.executePostToString(url));
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
            return this.client.build().newCall(request).execute();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public String executeGetToString(String url) {
        String resData;
        try (Response response = this.executeGet(url)) {
            resData = Objects.requireNonNull(response.body()).string();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
        return resData;
    }

    public Map<String, Object> executeGetToMap(String url) {
        return executeMap(() -> this.executeGetToString(url));
    }

    public List<Object> executeGetList(String url) {
        return executeList(() -> this.executeGetToString(url));
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
            return this.client.build().newCall(request).execute();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }

    }

    public String executePutToString(String url) {
        try (Response response = this.executePut(url)) {
            return Objects.requireNonNull(response.body()).string();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public Map<String, Object> executePutToMap(String url) {
        String strJson = this.executePutToString(url);
        return getStringObjectMap(strJson);
    }

    public List<Object> executePutList(String url) {
        return executeList(() -> this.executePutToString(url));
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
     * 使用函数式将String转换成Map
     *
     * @param supplier 数据获取函数式
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuHuiYu
     * Created DateTime 2021-04-16 9:08
     */
    private static Map<String, Object> executeMap(Supplier<String> supplier) {
        String strJson = supplier.get();
        return getStringObjectMap(strJson);
    }

    /**
     * 使用函数式将String转换成List
     *
     * @param supplier 数据获取函数式
     * @return java.util.List<java.lang.Object>
     * @author LiuHuiYu
     * Created DateTime 2021-04-16 9:09
     */
    private static List<Object> executeList(Supplier<String> supplier) {
        String strJson = supplier.get();
        return getStringObjectList(strJson);
    }

    /**
     * 将json字符串转换成Map
     *
     * @param strJson json字符串
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author LiuHuiYu
     * Created DateTime 2021-03-30 9:04
     */
    @NotNull
    public static Map<String, Object> getStringObjectMap(String strJson) {
        try {
            if (strJson == null) {
                return new HashMap<>(0);
            }
            Gson gson = new Gson();
            Map<String, Object> resultMap = gson.fromJson(strJson, new TypeToken<Map<String, Object>>() {
            }.getType());
            if(resultMap==null){
                return new HashMap<>(0);
            }
            return  mapDoubleToInt(resultMap);
        }
        catch (JsonSyntaxException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public static List<Object> getStringObjectList(String strJson) {
        try {
            Gson gson = new Gson();
            List<Object> resultList = gson.fromJson(strJson, new TypeToken<List<Object>>() {
            }.getType());
            return listDoubleToInt(resultList);
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

    //region https支持
    private MyTrustManager mMyTrustManager;

    public OkHttpUtil https() {
        this.client.sslSocketFactory(createSSLSocketFactory(), mMyTrustManager)
                .hostnameVerifier(new TrustAllHostnameVerifier());
        return this;
    }

    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            mMyTrustManager = new MyTrustManager();
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{mMyTrustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        }
        catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }

    //实现X509TrustManager接口
    public static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    //实现HostnameVerifier接口
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * 对外提供的获取支持自签名的okhttp客户端
     *
     * @param certificate 自签名证书的输入流
     * @return 支持自签名的客户端
     */
    public OkHttpClient getTrusClient(InputStream certificate) {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(certificate);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            //使用构建出的trustManger初始化SSLContext对象
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            //获得sslSocketFactory对象
            sslSocketFactory = sslContext.getSocketFactory();
        }
        catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build();
    }

    /**
     * 获去信任自签证书的trustManager
     *
     * @param in 自签证书输入流
     * @return 信任自签证书的trustManager
     * @throws GeneralSecurityException
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        //通过证书工厂得到自签证书对象集合
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }
        //为证书设置一个keyStore
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        //将证书放入keystore中
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }
        // Use it to build an X509 trust manager.
        //使用包含自签证书信息的keyStore去构建一个X509TrustManager
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(null, password);
            return keyStore;
        }
        catch (IOException e) {
            throw new AssertionError(e);
        }
    }
    //endregion

}
