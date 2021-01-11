package com.liuhuiyu.okhttp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-09 15:06
 */
public class OkHttpUtil {
    private final OkHttpClient client = new OkHttpClient();
    private final Request.Builder builder;
    private final FormBody.Builder body;

    private OkHttpUtil() {
        this.builder = new Request.Builder();
        this.body = new FormBody.Builder();
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

    //region post申请
    public Response executePost() {
        try {
            return client.newCall(this.builder.post(body.build()).build()).execute();
        } catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public String executePostToString() {
        try {
            return Objects.requireNonNull(this.executePost().body()).string();
        } catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public Map<String, Object> executePostToMap() {
        String strJson = this.executePostToString();
        try {
            Gson gson = new Gson();
            return gson.fromJson(strJson, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            throw new OkHttpException(e.getMessage());
        }
    }
    //endregion

    //region get申请
    public Response executeGet() {
        try {
            return client.newCall(this.builder.get().build()).execute();
        } catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public String executeGetToString() {
        try {
            return Objects.requireNonNull(this.executeGet().body()).string();
        } catch (IOException e) {
            throw new OkHttpException(e.getMessage());
        }
    }

    public Map<String, Object> executeGetToMap() {
        String strJson = this.executeGetToString();
        try {
            Gson gson = new Gson();
            return gson.fromJson(strJson, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            throw new OkHttpException(e.getMessage());
        }
    }
    //endregion

}
