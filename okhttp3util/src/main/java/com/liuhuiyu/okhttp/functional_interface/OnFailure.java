package com.liuhuiyu.okhttp.functional_interface;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-18 15:41
 */

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * 网络错误
 *
 * @author LiuHuiYu
 * Created DateTime 2021-09-18 10:42
 */
@FunctionalInterface
public interface OnFailure {
    void onFailure(@NotNull Call call, @NotNull IOException e);
}
