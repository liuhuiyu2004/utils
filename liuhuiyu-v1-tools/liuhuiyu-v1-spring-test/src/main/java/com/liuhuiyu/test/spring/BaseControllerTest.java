package com.liuhuiyu.test.spring;

import com.google.gson.*;
import com.liuhuiyu.json.map.MapUtil;
import com.liuhuiyu.spring.util.ResultUtil;
import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/2/14 10:46
 */
@SpringBootTest
public abstract class BaseControllerTest extends AbstractTest {
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    protected void printHtml(ResultActions resultActions) throws UnsupportedEncodingException {
        this.responseInfo(resultActions);
        this.printInfo("html信息");
        LOG.info(resultActions.andReturn().getResponse().getContentAsString());
        this.printInfo("html end");
    }

    String characterEncoding = "UTF-8";

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    protected void printJsonResult(ResultActions resultActions) throws Exception {
        this.responseInfo(resultActions);
        this.printInfo("json信息");
        resultActions.andReturn().getResponse().setCharacterEncoding(this.characterEncoding);
        ResultHandler handler = result -> {
            LOG.info(result.getResponse().getContentAsString());
        };
        resultActions.andDo(handler);


        this.printInfo("json end");
    }

    /**
     * 显示Response信息
     * Created DateTime 2021-02-23 9:10
     *
     * @param resultActions ResultActions

     */
    private void responseInfo(ResultActions resultActions) throws UnsupportedEncodingException {
        String json = resultActions.andReturn().getResponse().getContentAsString();
        int size = json.getBytes().length;
        this.printInfo("访问地址");
        LOG.info("{}{}", resultActions.andReturn().getRequest().getLocalAddr(), resultActions.andReturn().getRequest().getRequestURI());
        this.printInfo("参数信息");
        for (String key : resultActions.andReturn().getRequest().getParameterMap().keySet()) {
            LOG.info("{}:{}", key, resultActions.andReturn().getRequest().getParameterMap().get(key));
        }
        this.printInfo("字节Byte：" + size);
        this.printInfo("流量bit：" + (size * 8));
    }

    protected void printInfo(String info) {
        LOG.info("/******************************* " + info + " *******************************/");
    }

    protected MockHttpSession adminLoginSession(String adminAttribute, Object value) {
        return setSession((session) -> session.setAttribute(adminAttribute, value));
    }

    protected MockHttpSession userLoginSession(String userAttribute, Object value) {
        return setSession((session) -> session.setAttribute(userAttribute, value));
    }

    /**
     * session设定；
     * session.setAttribute(“admin”,admin);//对session进行登陆设置
     * Created DateTime 2021-02-23 9:19
     *
     * @param consumer session

     */
    protected MockHttpSession setSession(Consumer<MockHttpSession> consumer) {
        MockHttpSession session = new MockHttpSession();
        consumer.accept(session);
        return session;
    }

    protected String getResultJson(ResultActions resultActions) throws Exception {
        AtomicReference<String> s = new AtomicReference<>();
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        ResultHandler handler = (result) -> {
            s.set(result.getResponse().getContentAsString());
        };
        resultActions.andDo(handler);
        return s.get();
    }

    protected <T> T getData(ResultActions resultActions, Class<T> clazz) throws Exception {
        final String resultJson = this.getResultJson(resultActions);
        final Map<String, Object> stringObjectMap = MapUtil.mapOfJsonString(resultJson);
        final T t = (T) MapUtil.fromMap(stringObjectMap, clazz);
        return ResultUtil.getResultData(MapUtil.mapOfJsonString(resultJson), (map) -> MapUtil.fromMap(map, clazz));
    }
}