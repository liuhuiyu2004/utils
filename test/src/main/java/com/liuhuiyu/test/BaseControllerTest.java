package com.liuhuiyu.test;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.function.Consumer;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-07-21 9:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc//网站测试
@Log4j2
public class BaseControllerTest {
    private static final Logger log= LogManager.getLogger(BaseControllerTest.class);
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mvc;

    @Before()
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    protected void printHtml(@NotNull ResultActions resultActions) throws UnsupportedEncodingException {
        this.responseInfo(resultActions);
        this.printInfo("html信息");
        log.info(resultActions.andReturn().getResponse().getContentAsString());
        this.printInfo("html end");
    }

    String characterEncoding = "UTF-8";

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    protected void printJsonResult(@NotNull ResultActions resultActions) throws Exception {
        this.responseInfo(resultActions);
        this.printInfo("json信息");
//        log.info(resultActions.andReturn().getResponse().getContentAsString());
        resultActions.andReturn().getResponse().setCharacterEncoding(this.characterEncoding);
        ResultHandler handler = result -> {
            log.info(result.getResponse().getContentAsString());
        };
        resultActions.andDo(handler);


        this.printInfo("json end");
    }

    /**
     * 显示Response信息
     * Created DateTime 2021-02-23 9:10
     *
     * @param resultActions ResultActions
     * @author LiuHuiYu
     */
    private void responseInfo(@NotNull ResultActions resultActions) throws UnsupportedEncodingException {
        String json = resultActions.andReturn().getResponse().getContentAsString();
        int size = json.getBytes().length;
        this.printInfo("访问地址");
        log.info("{}{}", resultActions.andReturn().getRequest().getLocalAddr(), resultActions.andReturn().getRequest().getRequestURI());
        this.printInfo("参数信息");
        for (String key : resultActions.andReturn().getRequest().getParameterMap().keySet()) {
            log.info("{}:{}", key, resultActions.andReturn().getRequest().getParameterMap().get(key));
        }
        this.printInfo("字节Byte：" + size);
        this.printInfo("流量bit：" + (size * 8));
    }

    protected void printInfo(String info) {
        log.info("/******************************* " + info + " *******************************/");
    }

    @SneakyThrows
    protected MockHttpSession adminLoginSession(String adminAttribute, Object value) {
        return setSession((session) -> session.setAttribute(adminAttribute, value));
    }

    @SneakyThrows
    protected MockHttpSession userLoginSession(String userAttribute, Object value) {
        return setSession((session) -> session.setAttribute(userAttribute, value));
    }

    /**
     * session设定；
     * session.setAttribute(“admin”,admin);//对session进行登陆设置
     * Created DateTime 2021-02-23 9:19
     *
     * @param consumer session
     * @author LiuHuiYu
     */
    @SneakyThrows
    protected MockHttpSession setSession(Consumer<MockHttpSession> consumer) {
        MockHttpSession session = new MockHttpSession();
        consumer.accept(session);
        return session;
    }
}