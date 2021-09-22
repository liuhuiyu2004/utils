package com.liuhuiyu.okhttp;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-07-06 10:44
 */
@Log4j2
public class OkHttpUtil2Test {

    public void getOkHttpPrimeval() {
        String url = "http://127.0.0.1:9999/api/get/test";
        OkHttpUtil2 okHttpUtil2 = OkHttpUtil2.create(url);
        OkHttpUtil2.OkHttpPrimeval okHttpPrimeval = okHttpUtil2.getOkHttpPrimeval();
        log.info("OkHttpClient.Builder:{}", okHttpPrimeval.getOkHttpClientBuilder());
        log.info("BodyBuilder.Builder:{}", okHttpPrimeval.getBodyBuilderBuilder());
        log.info("HttpUrl.Builder:{}", okHttpPrimeval.getHttpUrlBuilder());
        log.info("Request.Builder:{}", okHttpPrimeval.getRequestBuilder());
        log.info("MethodModel:{}", okHttpPrimeval.getMethodModel());
        log.info("RequestBody:{}", okHttpPrimeval.getRequestBody());
    }

    @Test
    public void getNoParameters() {
        String url = "http://127.0.0.1:9999/api/get/test";
        String s = OkHttpUtil2.create(url).get().executeToString();
        log.info(s);
        String s2 = OkHttpUtil2.create(url)
                .setMethodModel(OkHttpUtil2.MethodModel.GET)
                .executeToString();
        log.info(s2);
    }

    @Test
    public void getParameters() {
        String url = "http://127.0.0.1:9999/api/get/test_p";
        String parameter = "轻微隐患";
        String parameterName = "p1";
        String s = OkHttpUtil2.create(url)
                .addQueryParameter(parameterName, parameter)
                .executeToString();
        Map<String, Object> map = OkHttpUtil2.create(url)
                .addQueryParameter(parameterName, parameter)
                .executeToMap();
        log.info(s);
        log.info("map:{}", map);
    }

    @Test
    public void asynchronousExecute() throws InterruptedException {
        String url = "http://127.0.0.1:9999/api/get/test";
        AtomicBoolean b = new AtomicBoolean(true);
        OkHttpUtil2.create(url).get().asynchronousExecute((call, response) -> {
            log.info("测试");
            b.set(false);
        }, (call, e) -> {
            log.error(e);
        });
        log.info("异步");
        while (b.get()) {
        }
    }

    @Test
    public void asynchronousExecuteToString() throws InterruptedException {
        String url = "http://127.0.0.1:9999/api/get/test";
        AtomicBoolean b = new AtomicBoolean(true);
        OkHttpUtil2.create(url).get().asynchronousExecuteToString((str) -> {
            log.info(str);
            b.set(false);
        }, (call, e) -> {
            log.error(e);
        });
        log.info("异步");
        while (b.get()) {
        }
    }

    @Test
    public void asynchronousExecuteToMap() throws InterruptedException {
        String url = "http://127.0.0.1:9999/api/get/test";
        AtomicBoolean b = new AtomicBoolean(true);
        OkHttpUtil2
                .create(url)
                .get()
                .asynchronousExecuteToMap((map) -> {
                    log.info("map:{}", map);
                    b.set(false);
                }, (call, e) -> {
                    log.error(e);
                });
        log.info("异步");
        while (b.get()) {
        }
    }

    @Test
    public void postNoParameters() {
        String url = "http://127.0.0.1:9999/api/post/test";
        String s = OkHttpUtil2.create(url)
                .post()
                .executeToString();
        log.info(s);
    }

    @Test
    public void postParameters() {
        String url = "http://127.0.0.1:9999/api/post/test_p";
        String parameter = "轻微隐患";
        String parameterName = "p1";
        String s = OkHttpUtil2.create(url)
                .addQueryParameter(parameterName, parameter)
                .addBody(parameterName, parameter + "body传入")
                .post()
                .executeToString();
        log.info(s);
        Map<String, Object> map = OkHttpUtil2.create(url)
                .post()
                .addQueryParameter(parameterName, parameter + "url传入")
                .addBody(parameterName, parameter)
                .executeToMap();
        log.info("map:{}", map);
    }

    @Test
    public void postStringBody() {
        final String GET_TOKEN_URL = "https://safety-vsmapi.geg.com.cn/WebService.asmx/GetToken";
        final String CONTRACTOR_PERSONNEL_INFORMATION_URL = "https://safety-vsmapi.geg.com.cn/WebService.asmx/GetDataRequest";
        String strJson = "{\"userName\":\"zhAdmin\",\"userPwd\":\"zh@2021\"}";
        String method = "application/json";
        Map<String, Object> map = OkHttpUtil2
                .create(GET_TOKEN_URL)
                .setBody(strJson, method)
                .post()
                .executeToMap();
        log.info(map);
        String token = map.get("token").toString();
        String cardNo = "440421198903088237";
        String findJson = getJson(token, cardNo);
        Map<String, Object> map2 = OkHttpUtil2
                .create(CONTRACTOR_PERSONNEL_INFORMATION_URL)
                .setBody(findJson, method)
                .post()
                .executeToMap();
        log.info("map2:{}", map2);
    }

    private String getJson(String token, String cardNo) {
        //region 结构编码
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("dataType", "C_VENUSER");
        map.put("company", "ZHP");
        map.put("pageSize", 1000);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("code", "");
        dataMap.put("name", "");
        dataMap.put("idcards", cardNo);
        dataMap.put("suCode", "");
        dataMap.put("mobile", "");
        dataMap.put("suName", "");
        map.put("data", dataMap);
        //endregion
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}