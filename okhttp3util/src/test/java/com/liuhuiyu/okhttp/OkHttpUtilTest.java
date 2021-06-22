package com.liuhuiyu.okhttp;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-12 19:11
 */
public class OkHttpUtilTest {
    //region get测试
    private String getUrl(String url) {
        return "http://192.168.2.7:8006" + url;
    }

    @Test
    public void testGetForLogin() {
        String url = getUrl("/Authentication/Login");
        OkHttpUtil okHttpUtil = OkHttpUtil.create();
        okHttpUtil.addQueryParameter("userid", "ZH_Admin");
        okHttpUtil.addQueryParameter("password", "UmVob21lLnpocHNAMjAyMA==");
        Response res = okHttpUtil.executeGet(url);
        String resString = okHttpUtil.executeGetToString(url);
        Map<String, Object> resMap = okHttpUtil.executeGetToMap(url);
    }

    @Test
    public void testTime() {
        for (int i = 0; i < 100; i++) {
            testGetForLogin();
        }
    }

    private String getToken() {
        String url = getUrl("/Authentication/Login");
        OkHttpUtil okHttpUtil = OkHttpUtil.create();
        okHttpUtil.addQueryParameter("userid", "ZH_Admin");
        okHttpUtil.addQueryParameter("password", "UmVob21lLnpocHNAMjAyMA==");
        Map<String, Object> resMap = okHttpUtil.executeGetToMap(url);
        return resMap.get("token").toString();
    }

    @Test
    public void testGet02() {
        String url = getUrl("/U_RS_BASICINFO/GetUserInfoByManID");
        String token = getToken();
        OkHttpUtil okHttpUtil = OkHttpUtil.create();
        okHttpUtil.headerAuthorizationByBearerToken(token);
        okHttpUtil.headerAuthorizationByBearerToken(token);
        Response res = okHttpUtil.executeGet(url);
        String resString = okHttpUtil.executeGetToString(url);
        Map<String, Object> resMap = okHttpUtil.executeGetToMap(url);
    }

    @Test
    public void testGet03() {
        String url = getUrl("/U_RS_BASICINFO/GetTreeUser");
        String token = getToken();
        OkHttpUtil okHttpUtil = OkHttpUtil.create();
        okHttpUtil.addQueryParameter("dc", "ZH");
        okHttpUtil.headerAuthorizationByBearerToken(token);
        okHttpUtil.headerAuthorizationByBearerToken(token);
        Response res = okHttpUtil.executeGet(url);
        String resString = okHttpUtil.executeGetToString(url);
        Map<String, Object> resMap = okHttpUtil.executeGetToMap(url);
    }

    //endregion
    @Test
    public void test() {
        Map<String, Object> m1 = new HashMap<>();
        Map<String, Object> m2 = new HashMap<>();
        Map<String, Object> m3 = new HashMap<>();
        List<Number> list1 = new ArrayList<>();
        list1.add(1.0);
        list1.add(2.0);
        list1.add(3.0);
        list1.add(4.1);
        list1.add(5.0);
        m3.put("m3_1", list1);
        m3.put("m3_2", 2.0);
        m3.put("m3_3", 3.0);
        m2.put("m2_1", m3);
        m2.put("m2_2", 5.0);
        m2.put("m2_3", 6.1);
        m1.put("m1_1", m2);
        m1.put("m1_2", 4.0);
        m1.put("m1_3", 1.1);
        Map<String, Object> map = OkHttpUtil.mapDoubleToInt(m1);

    }

    @Test
    public void testHeadGet() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiWkhfQWRtaW4iLCJleHAiOjE2MTI2MzM4MzAsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjIuNzo4MDA2IiwiYXVkIjoiaHR0cDovLzE5Mi4xNjguMi43OjgwMDYifQ.E6R3ei7UyUK3UnLAKbbLj9vIO9frdUtekT1UD4hHB6A";
        String url = "http://192.168.2.7:8006/U_SYSTREE/GetUserTreeStr";
        Map<String, Object> map = OkHttpUtil.create().headerAuthorizationByBearerToken(token).addQueryParameter("dc", "ZH").executeGetToMap(url);
        if (Integer.parseInt(map.get("code").toString()) == 200) {
            System.out.println(map.get("data"));
        }
        else {
            System.out.println(map);
        }
    }

    @Test
    public void testPut01() {
        String url = "http://192.168.2.7:8006/YJYAJX/QYSX";
        String token = this.getToken();
        String id = "b7af1ab0-0719-4e3e-93f0-c81dba07e9cb";
        OkHttpUtil okHttpUtil = OkHttpUtil.create();
        okHttpUtil.addQueryParameter("id", id);
        okHttpUtil.headerAuthorizationByBearerToken(token);
        Response response = okHttpUtil.executePut(url);
        Map<String, Object> map = okHttpUtil.executePutToMap(url);
        /*
         {
         "code": 200,
         "msg": "",
         "id": "b7af1ab0-0719-4e3e-93f0-c81dba07e9cb",
         "JSR": "管理员",
         "JSSJ": "2021-03-26 10:35:25"
         }
         */
    }

    @Test
    public void testPut02() {
        String token = this.getToken();
        String url = "http://192.168.2.7:8006/YJYAJX/UpDate";
        Map<String, String> queryParameter = new HashMap<>(5);
        queryParameter.put("id", "aa30fe55-746c-45a1-8bc1-ef91cd7566c9");
        queryParameter.put("yjyaid", "96f3065d-8006-4758-a784-b12f147594b6");
        queryParameter.put("title", "2021年122号台风天鸽应急预案");
        queryParameter.put("type", "防风");
        queryParameter.put("dz", "200002");
        OkHttpUtil okHttpUtil = OkHttpUtil.create();
        okHttpUtil.headerAuthorizationByBearerToken(token);
        for (String key : queryParameter.keySet()) {
            okHttpUtil.addBody(key, queryParameter.get(key));
        }
        okHttpUtil.setMethod(OkHttpUtil.MEDIA_TYPE_APPLICATION_JSON_UTF_8);
        Response response = okHttpUtil.executePut(url);
        Map<String, Object> map = okHttpUtil.executePutToMap(url);
    }

    static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    public void testWebSocket() throws InterruptedException {
        String url = "ws://localhost:8111/ws1";
        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
            }
        };
        OkHttpUtil.webSocket(url, webSocketListener);
        countDownLatch.await();
    }

    @Test
    public void testHttps() {
        String url = "http://10.0.21.227/WebService.asmx/GetToken";
        OkHttpUtil okHttpUtil = OkHttpUtil.create();
        okHttpUtil.setMethod("application/json");
        okHttpUtil.addBody("userName", "zhAdmin");
        okHttpUtil.addBody("userPwd", "zh@2021");
        okHttpUtil.https();
        Map<String, Object> map = okHttpUtil.executePostToMap(url);
        String token = map.get("token").toString();
        String url2 = "https://safety-vsmapi.geg.com.cn/WebService.asmx/GetDataRequest";
        OkHttpUtil okHttpUtil2 = OkHttpUtil.create();
        okHttpUtil2.setMethod("application/json");
        String[] names = {"", "巫中建"};//"巫中建";
        String name = names[0];//"巫中建";
        String[] ids = {"", "51162119900401647X", "510231197208103576", "511621"};
        String idCards = ids[3];//""510231197208103576";
        String pageSize = "5";
        okHttpUtil2.setBodyString("{\n" +
                "    \"token\":\"" + token + "\",\n" +
                "    \"dataType\":\"C_VENUSER\",\n" +
                "    \"company\":\"ZHP\",\n" +
                "    \"pageSize\":" + pageSize + ",\n" +
                "    \"data\":{" +
                "        \"code\":\"\"," +
                "        \"name\":\"" + name + "\"," +
                "        \"idcards\":\"" + idCards + "\"," +
                "        \"suCode\":\"\"," +
                "        \"mobile\":\"\"," +
                "        \"suName\":\"\"" +
                "    }\n" +
                "}\n");
//        String info=okHttpUtil.executePostToString(url);
        Map<String, Object> map2 = okHttpUtil2.executePostToMap(url2);

    }

    @Test
    public void testHttpsGet() {
        String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=13614600169";
        OkHttpUtil okHttpUtil = OkHttpUtil.create();
//        okHttpUtil.https();
        String info = okHttpUtil.executeGetToString(url);
    }

    @Test
    public void testHttpsGet2(){
        String url="https://127.0.0.1:8125/api/v1.0/system/authentication/login_do";
        Map<String, String> queryParameter = new HashMap<>(2);
        String account="ZH_AF_Admin";
        String password="UmVob21lLnpoYWZAMjEwNg==";
        queryParameter.put("account", account);
        queryParameter.put("password", password);
        OkHttpUtil okHttpUtil = OkHttpUtil.create();
        for (String key : queryParameter.keySet()) {
            okHttpUtil.addQueryParameter(key, queryParameter.get(key));
        }
        okHttpUtil.https();
        Map<String, Object> map = okHttpUtil.executeGetToMap(url);
    }
    @Test
    public void testFor() {
        int num = 100_000;
        int n = 0;
        long m = 0;
        for (int k = 0; k <= 50; k++) {
            for (int j = 0; j <= 10_000; j++) {
                n = 0;
                for (int i = 0; i <= num; i++) {
                    n += i * 2 - 1;
                    m++;
                }
            }
        }
        System.out.println(m + "," + n);
    }

    @Test
    public void testVoid() {
        String id = "f592b1eac6fb4d1091041bf1dab89aa2";
        String url = "http://10.19.0.114:8354/partnerRequest/hik8700/queryHls.shtml";
        String str = OkHttpUtil.create().addQueryParameter("indexCode", id).executeGetToString(url);
    }

}