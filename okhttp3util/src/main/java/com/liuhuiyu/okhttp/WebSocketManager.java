package com.liuhuiyu.okhttp;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-31 17:33
 */
public final class WebSocketManager {
    boolean err = false;
    boolean run = false;

    /**
     * 功能描述
     *
     * @param url               地址
     * @param webSocketListener 监听反馈
     * @return com.liuhuiyu.okhttp.WebSocketManager
     * @author LiuHuiYu
     * Created DateTime 2021-04-01 8:20
     */
    public static WebSocketManager create(String url, WebSocketListener webSocketListener) {
        return new WebSocketManager(url, webSocketListener);
    }

    String url;
    WebSocketListener webSocketListener;
    WebSocket webSocket;
    int restartMaxNum = 5;
    int restartNum = 0;

    private WebSocketManager(String url, WebSocketListener webSocketListener) {
        this.url = url;
        this.webSocketListener = webSocketListener;
    }

    /**
     * 设定最多重新启动的次数
     * @author LiuHuiYu
     * Created DateTime 2021-04-01 9:36
     * @param num 次数
     */
    public void setRestartMaxNum(int num) {
        this.restartMaxNum = Math.max(num, 0);
    }

    public boolean start() {
        if (!run) {
            this.restartNum = 0;
            this.run(this.url);
            return true;
        }
        else {
            return false;
        }
    }
    public boolean stop() {
        if (run) {
            this.webSocket.close(0, "主动关闭");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 消息发送
     *
     * @param message 消息
     * @author LiuHuiYu
     * Created DateTime 2021-04-01 9:30
     */
    public void send(String message) {
        if (this.run) {
            this.webSocket.send(message);
        }
    }

    /**
     * 对象发送
     *
     * @param jsonObject json对象
     * @author LiuHuiYu
     * Created DateTime 2021-04-01 9:32
     */
    public void sendJson(Object jsonObject) {
        Gson gson = new Gson();
        String json = gson.toJson(jsonObject);
        this.send(json);
    }

    private void restart(){
        if(this.restartMaxNum==0 || this.restartNum<this.restartMaxNum){
            this.run(this.url);
        }
    }
    private void run(String url) {
        WebSocketListener selfWebSocketListener = new WebSocketListener() {
            /**
             * 关闭监听
             * @author LiuHuiYu
             * Created DateTime 2021-04-01 8:26
             * @param webSocket webSocket
             * @param code      code
             * @param reason    reason
             */
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                run = false;
                webSocketListener.onClosed(webSocket, code, reason);
            }

            /**
             * 当远程对等端指示不再传输传入消息时调用。
             * @author LiuHuiYu
             * Created DateTime 2021-04-01 8:27 
             * @param webSocket webSocket
             * @param code      code
             * @param reason    reason
             */
            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                run = false;
                webSocketListener.onClosing(webSocket, code, reason);
            }

            /**
             * 异常调用
             * @author LiuHuiYu
             * Created DateTime 2021-04-01 8:28 
             * @param webSocket webSocket
             * @param t         错误
             * @param response  response
             */
            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                run = false;
                err = true;
                webSocketListener.onFailure(webSocket, t, response);
                restart();//重新启动
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                webSocketListener.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                webSocketListener.onMessage(webSocket, bytes);
            }

            /**
             * 开启调用
             * @author LiuHuiYu
             * Created DateTime 2021-04-01 8:29
             * @param webSocket webSocket
             * @param response  response
             */
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                run = true;
                webSocketListener.onOpen(webSocket, response);
            }
        };
        this.webSocket = OkHttpUtil.webSocket(url, selfWebSocketListener);
    }

}
