package com.liuhuiyu.okhttp;

import com.liuhuiyu.okhttp.functional_interface.OnFailure;
import com.liuhuiyu.okhttp.functional_interface.OnResponse;
import com.liuhuiyu.okhttp.utils.ArrangeTransformUtil;
import okhttp3.*;
import okhttp3.internal.http.HttpMethod;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * OkHttpUtil升级版
 * 尽量完整封装各个类
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-06 10:05
 */
public class OkHttpUtil2 {
    private final OkHttpClient.Builder client;
    private final Request.Builder request;
    private final HttpUrl.Builder httpUrl;
    private final FormBody.Builder bodyBuilder;
    private MethodModel methodModel;
    private RequestBody requestBody;

    /**
     * 提供已经包装的OkHttp3原型
     *
     * @return com.liuhuiyu.okhttp.OkHttpUtil2.OkHttpPrimeval
     * @author LiuHuiYu
     * Created DateTime 2021-09-18 16:31
     */
    public OkHttpPrimeval getOkHttpPrimeval() {
        return new OkHttpPrimeval();
    }

    class OkHttpPrimeval {
        public OkHttpClient.Builder getOkHttpClientBuilder() {
            return client;
        }

        public Request.Builder getRequestBuilder() {
            return request;
        }

        public HttpUrl.Builder getHttpUrlBuilder() {
            return httpUrl;
        }

        public FormBody.Builder getBodyBuilderBuilder() {
            return bodyBuilder;
        }

        public MethodModel getMethodModel() {
            return methodModel;
        }

        public RequestBody getRequestBody() {
            return requestBody;
        }
    }

    public static OkHttpUtil2 create(String url) {
        return new OkHttpUtil2(url);
    }

    private OkHttpUtil2(String url) {
        this.httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        this.request = new Request.Builder();
        this.client = new OkHttpClient.Builder();
        this.bodyBuilder = new FormBody.Builder();
        this.methodModel = MethodModel.GET;
    }

    /**
     * 添加地址参数
     *
     * @param name  键值
     * @param value 值
     * @return com.liuhuiyu.okhttp.OkHttpUtil2
     * @author LiuHuiYu
     * Created DateTime 2021-07-06 10:20
     */
    public OkHttpUtil2 addQueryParameter(String name, String value) {
        this.httpUrl.addQueryParameter(name, value);
        return this;
    }

    /**
     * 添加body参数
     *
     * @param name  参数名称
     * @param value 参数值
     * @return com.liuhuiyu.okhttp.OkHttpUtil2
     * @author LiuHuiYu
     * Created DateTime 2021-07-06 11:06
     */
    public OkHttpUtil2 addBody(String name, String value) {
        this.bodyBuilder.add(name, value);
        this.post();
        return this;
    }

    /**
     * 字符串设置Body
     *
     * @param bodyString body字符串
     * @param method     MediaType
     * @return com.liuhuiyu.okhttp.OkHttpUtil2
     * @throws IllegalArgumentException 参数不能设置成空
     * @author LiuHuiYu
     * Created DateTime 2021-09-18 16:25
     */
    public OkHttpUtil2 setBody(String bodyString, String method) {
        if (bodyString == null || method == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        else {
            this.requestBody = RequestBody.create(bodyString, MediaType.parse(method));
            this.post();
        }
        return this;
    }

    /**
     * 模式
     *
     * @author LiuHuiYu
     * Created DateTime 2021-07-06 10:33
     */
    public enum MethodModel {
        /**
         * get方法
         * Created DateTime 2021-07-06 10:40
         */
        GET,
        /**
         * post方法
         * Created DateTime 2021-07-06 10:40
         */
        POST,
        /**
         * delete方法
         * Created DateTime 2021-07-06 10:40
         */
        DELETE,
        /**
         * put方法
         * Created DateTime 2021-07-06 10:40
         */
        PUT,
        /**
         * patch方法
         * Created DateTime 2021-07-06 10:40
         */
        PATCH,
        /**
         * head方法
         * Created DateTime 2021-07-06 10:40
         */
        HEAD,
        ;

        public String getName() {
            return this.name();
        }

        void builder(Request.Builder builder, RequestBody requestBody) {
            if (HttpMethod.permitsRequestBody(this.name())) {
                builder.method(this.getName(), requestBody);
            }
            else {
                builder.method(this.name(), null);
            }
        }
    }

    //region 执行模式设定

    /**
     * 设定执行模式
     *
     * @param methodModel GET, POST, DELETE, PUT, PATCH, HEAD
     * @return com.liuhuiyu.okhttp.OkHttpUtil2
     * @author LiuHuiYu
     * Created DateTime 2021-09-18 10:52
     */
    public OkHttpUtil2 setMethodModel(OkHttpUtil2.MethodModel methodModel) {
        this.methodModel = methodModel;
        return this;
    }

    /**
     * get模式
     *
     * @return com.liuhuiyu.okhttp.OkHttpUtil2
     * @author LiuHuiYu
     * Created DateTime 2021-09-18 10:54
     */
    public OkHttpUtil2 get() {
        this.methodModel = MethodModel.GET;
        return this;
    }

    /**
     * post模式
     *
     * @return com.liuhuiyu.okhttp.OkHttpUtil2
     * @author LiuHuiYu
     * Created DateTime 2021-09-18 10:54
     */
    public OkHttpUtil2 post() {
        this.methodModel = MethodModel.POST;
        return this;
    }
    //endregion

    //region 执行查询

    /**
     * 执行查询
     *
     * @return okhttp3.Response
     * @author LiuHuiYu
     * Created DateTime 2021-09-18 16:56
     */
    public Response execute() {
        this.request.url(httpUrl.build());
        OkHttpClient client = this.getOkHttpClient();
        Request request = this.request.build();
        try {
            return client.newCall(request).execute();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String executeToString() {
        try {
            return Objects.requireNonNull(this.execute().body()).string();
        }
        catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public Map<String, Object> executeToMap() {
        return ArrangeTransformUtil.executeMap(this::executeToString);
    }

    //endregion

    //region 异步执行

    /**
     * 异步执行提交
     *
     * @param onResponse 返回信息
     * @param onFailure  异常
     * @author LiuHuiYu
     * Created DateTime 2021-09-18 16:21
     */
    public void asynchronousExecute(OnResponse onResponse, OnFailure onFailure) {
        this.request.url(httpUrl.build());
        OkHttpClient client = getOkHttpClient();
        Request request = this.request.build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (onFailure != null) {
                    onFailure.onFailure(call, e);
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (onResponse != null) {
                    onResponse.onResponse(call, response);
                }
            }
        };
        client.newCall(request).enqueue(callback);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient client = this.client.build();
        if (this.requestBody == null) {
            this.requestBody = this.bodyBuilder.build();
        }
        this.methodModel.builder(this.request, this.requestBody);
        return client;
    }

    /**
     * 异步执行返回 String
     *
     * @param consumer  String消费
     * @param onFailure 异常
     * @author LiuHuiYu
     * Created DateTime 2021-09-18 16:21
     */
    public void asynchronousExecuteToString(Consumer<String> consumer, OnFailure onFailure) {
        this.asynchronousExecute((call, response) -> {
            String str = Objects.requireNonNull(response.body()).string();
            consumer.accept(str);
        }, onFailure);
    }

    /**
     * 异步执行返回 Map
     *
     * @param consumer  Map消费
     * @param onFailure 异常
     * @author LiuHuiYu
     * Created DateTime 2021-09-18 16:21
     */
    public void asynchronousExecuteToMap(Consumer<Map<String, Object>> consumer, OnFailure onFailure) {
        asynchronousExecuteToString((str) -> consumer.accept(ArrangeTransformUtil.executeMap(() -> str)), onFailure);
    }
    //endregion

}
