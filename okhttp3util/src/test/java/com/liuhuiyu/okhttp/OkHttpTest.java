package com.liuhuiyu.okhttp;

import com.liuhuiyu.okhttp.functional_interface.OnFailure;
import com.liuhuiyu.okhttp.functional_interface.OnResponse;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-18 10:32
 */
//@Log4j2
public class OkHttpTest {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OkHttpTest.class);
    @Test
    public void getAsynchronousInteraction() throws InterruptedException {
        log.info("测试异步");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder().url("https://blog.csdn.net/QasimCyrus").build();
        OnFailure onFailure = (call, e) -> {
        };
        OnResponse onResponse = (call, response) -> {
        };
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                onFailure.onFailure(call, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                onResponse.onResponse(call, response);
            }
        };
        okHttpClient.newCall(build).enqueue(callback);
        Thread.sleep(100000);
    }
}
