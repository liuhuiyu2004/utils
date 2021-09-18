package com.liuhuiyu.okhttp;

import com.liuhuiyu.okhttp.functional_interface.OnFailure;
import com.liuhuiyu.okhttp.functional_interface.OnResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.apache.commons.lang3.ThreadUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-18 10:32
 */
//@Log4j2
public class OkHttpTest {
    @SneakyThrows
    @Test
    public void getAsynchronousInteraction() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder().url("https://blog.csdn.net/QasimCyrus").build();
//        val request: Request = Request.Builder().url("https://blog.csdn.net/QasimCyrus").build()
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
