package com.liuhuiyu.test;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-07-06 10:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public abstract class BaseServiceTest {
    private static final Logger log= LogManager.getLogger(BaseServiceTest.class);
    @Before
    public void login() {

    }

    @After
    public void after() {

    }

    protected void printList(@NotNull List<?> list) {
        for (Object obj : list) {
            log.info(obj.toString());
        }
        log.info("size = " + list.size());
    }

    protected void printObject(Object obj) {
        if (obj == null) {
            log.info("Obj=null");
        }
        else {
            log.info(obj.toString());
        }
    }

    protected void printPageImpl(@NotNull Page<?> pageImpl) {
        log.info("总记录数：" + pageImpl.getTotalElements());
        log.info("当前页号索引：" + pageImpl.getNumber());

        log.info("每页数据：" + pageImpl.getSize());

        log.info("当前页记录数：" + pageImpl.getNumberOfElements());
        log.info("当前页号：" + (pageImpl.getNumber() + 1));

        log.info("总页数：" + pageImpl.getTotalPages());
        printList(pageImpl.getContent());
    }

    protected void printJson(Object obj) {
        if (obj == null) {
            log.info("Obj=null");
        }
        else {
            log.info(new Gson().toJson(obj));
        }
    }
}

