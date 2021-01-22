package com.liuhuiyui.test;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

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
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mvc;

    @Before()
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    protected void printHtml(@NotNull ResultActions resultActions ) throws UnsupportedEncodingException {
        String json = resultActions.andReturn().getResponse().getContentAsString();
        int size = json.getBytes().length;
        this.printInfo("访问地址");
        log.info("{}{}",resultActions.andReturn().getRequest().getLocalAddr(),resultActions.andReturn().getRequest().getRequestURI());
        this.printInfo("参数信息");
        for (String key : resultActions.andReturn().getRequest().getParameterMap().keySet()) {
            log.info("{}:{}",key,resultActions.andReturn().getRequest().getParameterMap().get(key));
        }
        this.printInfo("html信息");
        log.info(resultActions.andReturn().getResponse().getContentAsString());
        this.printInfo("字节Byte：" + size);
        this.printInfo("流量bit：" + (size * 8));
        this.printInfo("html end");
    }

    protected void printJsonResult(@NotNull ResultActions resultActions)throws UnsupportedEncodingException {
        String json = resultActions.andReturn().getResponse().getContentAsString();
        int size = json.getBytes().length;
        this.printInfo("访问地址");
        log.info("{}{}",resultActions.andReturn().getRequest().getLocalAddr(),resultActions.andReturn().getRequest().getRequestURI());
        this.printInfo("参数信息");
        for (String key : resultActions.andReturn().getRequest().getParameterMap().keySet()) {
            log.info("{}:{}",key,resultActions.andReturn().getRequest().getParameterMap().get(key));
        }
        this.printInfo("json信息");
        log.info(resultActions.andReturn().getResponse().getContentAsString());
        this.printInfo("字节Byte：" + size);
        this.printInfo("流量bit：" + (size * 8));
        this.printInfo("json end");
    }
    protected void printInfo(String info) {
        log.info("/******************************* " + info + " *******************************/");
    }

    @SneakyThrows
    protected MockHttpSession adminLoginSession(){
//        CO_User admin=new CO_User(1,"admin","","",true);
        MockHttpSession session=new MockHttpSession();
//        session.setAttribute(SessionUtil.USER_INFO,admin);//对session进行登陆设置
        return session;
    }

    @SneakyThrows
    protected MockHttpSession userLoginSession(){
//        CO_User admin=new CO_User(1,"admin","","",false);
        MockHttpSession session=new MockHttpSession();
//        session.setAttribute(SessionUtil.USER_INFO,admin);//对session进行登陆设置
        return session;
    }
}