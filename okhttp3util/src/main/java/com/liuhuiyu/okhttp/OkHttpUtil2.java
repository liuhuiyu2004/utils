//package com.liuhuiyu.okhttp;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonSyntaxException;
//import com.google.gson.reflect.TypeToken;
//import okhttp3.*;
//import org.jetbrains.annotations.NotNull;
//
//import javax.net.ssl.*;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.SecureRandom;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//import java.util.*;
//import java.util.function.Supplier;
//
///**
// * OkHttpUtil升级版
// *
// * @author LiuHuiYu
// * @version v1.0.0.0
// * Created DateTime 2021-07-06 10:05
// */
//public class OkHttpUtil2 {
//    private final OkHttpClient.Builder client;
//    private final Request.Builder request;
//    private final HttpUrl.Builder httpUrl;
//    private final FormBody.Builder bodyBuilder;
//    private MethodModel methodModel;
//    private RequestBody requestBody;
//
//    public static OkHttpUtil2 create(String url) {
//        return new OkHttpUtil2(url);
//    }
//
//    private OkHttpUtil2(String url) {
//        this.httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
//        this.request = new Request.Builder();
//        this.client = new OkHttpClient.Builder();
//        this.bodyBuilder = new FormBody.Builder();
//        this.methodModel = MethodModel.GET;
//    }
//
//    /**
//     * 添加地址参数
//     *
//     * @param name  键值
//     * @param value 值
//     * @return com.liuhuiyu.okhttp.OkHttpUtil2
//     * @author LiuHuiYu
//     * Created DateTime 2021-07-06 10:20
//     */
//    public OkHttpUtil2 addQueryParameter(String name, String value) {
//        this.httpUrl.addQueryParameter(name, value);
//        return this;
//    }
//
//    /**
//     * 添加body参数
//     *
//     * @param name  参数名称
//     * @param value 参数值
//     * @return com.liuhuiyu.okhttp.OkHttpUtil2
//     * @author LiuHuiYu
//     * Created DateTime 2021-07-06 11:06
//     */
//    public OkHttpUtil2 addBody(String name, String value) {
//        this.bodyBuilder.add(name, value);
//        return this;
//    }
//
//    public OkHttpUtil2 setBody(String bodyString, String method) {
//        this.requestBody = RequestBody.create(bodyString, MediaType.parse(method));
//        return this;
//    }
//
//    /**
//     * 模式
//     *
//     * @author LiuHuiYu
//     * Created DateTime 2021-07-06 10:33
//     */
//    public enum MethodModel {
//        /**
//         * get方法
//         * Created DateTime 2021-07-06 10:40
//         */
//        GET,
//        /**
//         * post方法
//         * Created DateTime 2021-07-06 10:40
//         */
//        POST,
//        /**
//         * delete方法
//         * Created DateTime 2021-07-06 10:40
//         */
//        DELETE,
//        /**
//         * put方法
//         * Created DateTime 2021-07-06 10:40
//         */
//        PUT,
//        /**
//         * patch方法
//         * Created DateTime 2021-07-06 10:40
//         */
//        PATCH,
//        /**
//         * head方法
//         * Created DateTime 2021-07-06 10:40
//         */
//        HEAD,
//        ;
//
//        public String getName() {
//            return this.name();
//        }
//
//        protected void builder(Request.Builder builder, RequestBody requestBody) {
//            builder.method(this.name(), requestBody);
//        }
//    }
//
//    //region 执行模式设定
//
//    public OkHttpUtil2 setMethodModel(OkHttpUtil2.MethodModel methodModel) {
//        this.methodModel = methodModel;
//        return this;
//    }
//
//    public OkHttpUtil2 get() {
//        this.methodModel = MethodModel.GET;
//        return this;
//    }
//
//    public OkHttpUtil2 post() {
//        this.methodModel = MethodModel.POST;
//        return this;
//    }
//
//    public OkHttpUtil2 delete() {
//        this.methodModel = MethodModel.DELETE;
//        return this;
//    }
//
//    public OkHttpUtil2 put() {
//        this.methodModel = MethodModel.PUT;
//        return this;
//    }
//
//    //endregion
//
//    //region 执行查询
//    public Response execute() {
//        this.request.url(httpUrl.build());
//        OkHttpClient client = this.client.build();
//        Request request = this.request.build();
//        if (this.requestBody == null) {
//            this.requestBody = this.bodyBuilder.build();
//        }
//        this.methodModel.builder(this.request, this.requestBody);
//        try {
//            return client.newCall(request).execute();
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public String executeToString() {
//        try {
//            return Objects.requireNonNull(this.execute().body()).string();
//        }
//        catch (IOException e) {
//            throw new OkHttpException(e.getMessage());
//        }
//    }
//
//    public Map<String, Object> executeToMap() {
//        return executeMap(this::executeToString);
//    }
//    //endregion
//
//    //region 返回信息转换
//
//    /**
//     * 使用函数式将String转换成Map
//     *
//     * @param supplier 数据获取函数式
//     * @return java.util.Map<java.lang.String, java.lang.Object>
//     * @author LiuHuiYu
//     * Created DateTime 2021-04-16 9:08
//     */
//    private static Map<String, Object> executeMap(Supplier<String> supplier) {
//        String strJson = supplier.get();
//        return getStringObjectMap(strJson);
//    }
//
//    /**
//     * 将json字符串转换成Map
//     *
//     * @param strJson json字符串
//     * @return java.util.Map<java.lang.String, java.lang.Object>
//     * @author LiuHuiYu
//     * Created DateTime 2021-03-30 9:04
//     */
//    @NotNull
//    private static Map<String, Object> getStringObjectMap(String strJson) {
//        try {
//            Gson gson = new Gson();
//            Map<String, Object> resultMap = gson.fromJson(strJson, new TypeToken<Map<String, Object>>() {
//            }.getType());
//            return mapDoubleToInt(resultMap);
//        }
//        catch (JsonSyntaxException e) {
//            throw new OkHttpException(e.getMessage());
//        }
//    }
//
//    private static Map<String, Object> mapDoubleToInt(Map<?, ?> resultMap) {
//        Map<String, Object> res = new HashMap<>(resultMap.size());
//        for (Object keyObj : resultMap.keySet()) {
//            String key = keyObj.toString();
//            if (resultMap.get(key) instanceof Double) {
//                Double value = (Double) resultMap.get(key);
//                if (value.intValue() == value) {
//                    res.put(key, ((Double) resultMap.get(key)).intValue());
//                }
//                else {
//                    res.put(key, resultMap.get(key));
//                }
//            }
//            else if (resultMap.get(key) instanceof List<?>) {
//                res.put(key, listDoubleToInt((List<?>) resultMap.get(key)));
//            }
//            else if (resultMap.get(key) instanceof Map<?, ?>) {
//                res.put(key, mapDoubleToInt((Map<?, ?>) resultMap.get(key)));
//            }
//            else {
//                res.put(key, resultMap.get(key));
//            }
//        }
//        return res;
//    }
//
//    private static List<Object> listDoubleToInt(List<?> list) {
//        List<Object> res = new ArrayList<>(list.size());
//        for (Object o : list) {
//            if (o instanceof Number) {
//                Double value = (Double) o;
//                if (value.intValue() == value) {
//                    Object v = value.intValue();
//                    res.add(v);
//                }
//                else {
//                    res.add(value);
//                }
//            }
//            else if (o instanceof Map<?, ?>) {
//                res.add(mapDoubleToInt((Map<?, ?>) o));
//            }
//            else if (o instanceof List<?>) {
//                res.add(listDoubleToInt((List<?>) o));
//            }
//            else {
//                res.add(o);
//            }
//        }
//        return res;
//    }
//    //endregion
//}
