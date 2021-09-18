package com.liuhuiyu.okhttp.functional_interface;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-18 15:42
 */

import okhttp3.Call;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * 网络相应
 *
 * @author LiuHuiYu
 * Created DateTime 2021-09-18 10:42
 */
@FunctionalInterface
public interface OnResponse {
    void onResponse(@NotNull Call call, @NotNull Response response) throws IOException;
}
