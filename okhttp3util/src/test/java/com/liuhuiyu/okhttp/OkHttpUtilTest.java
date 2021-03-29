package com.liuhuiyu.okhttp;

import jdk.nashorn.internal.runtime.logging.Logger;
import okhttp3.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-12 19:11
 */
public class OkHttpUtilTest {

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
        /*
        [
                { "isexpand": "false", "text": "特别防护期管理", "name": "特别防护期管理", "children":
                    [
                            {  "urls": "/DemoAIHtml/CSWH/TBFH_CSWH_List.html", "text": "措施管理", "name": "措施管理", "id": "2.0.1.1" },
                            {  "urls": "/DemoAIHtml/CSGL/TBFH_CSGL_List.html", "text": "防护期管理", "name": "防护期管理", "id": "2.0.1.2" },
                            {  "urls": "/DemoAIHtml/JCSC/TBFH_JCSC_List.html", "text": "进厂人员申请", "name": "进厂人员申请", "id": "2.0.1.3" },
                            {  "urls": "/DemoAIHtml/SCWZ/TBFH_SCWZ_List_Data.html", "text": "生产物资保障数据源", "name": "生产物资保障数据源", "id": "2.0.1.7" }
                    ]
                }
        ]
         */
    }

    @Test
    public void testPut() {
        String url = "http://192.168.2.7:8006/YJYAJX/QYSX";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiWkhfQWRtaW4iLCJleHAiOjE2MTY3NjE1NDYsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjIuNzo4MDA2IiwiYXVkIjoiaHR0cDovLzE5Mi4xNjguMi43OjgwMDYifQ.nmLKiJHeIf5niL1k6g4hVRA_RIa2BcYIR0jF9XNs4uo";
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
    public void testPost() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiWkhfQWRtaW4iLCJleHAiOjE2MTcwMjMyMzAsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjIuNzo4MDA2IiwiYXVkIjoiaHR0cDovLzE5Mi4xNjguMi43OjgwMDYifQ.8Y91ifTYi0-KH5PDWvFaJrN53Iz0_3Itqko8PzM82MM";
        String url="http://192.168.2.7:8006/YJYAJX/UpDate";
        Map<String,String>queryParameter=new HashMap<>(5);
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
        Map<String, Object> map = okHttpUtil.executePutToMap(url);
    }

}