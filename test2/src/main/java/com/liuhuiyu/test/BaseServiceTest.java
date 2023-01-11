package com.liuhuiyu.test;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-09-08 15:44
 */
@SpringBootTest
public abstract class BaseServiceTest extends TestBase{

    @BeforeEach
    public void login() {

    }

    @AfterEach
    public void after() {

    }

    protected void printList(@NotNull List<?> list) {
        for (Object obj : list) {
            LOG.info(obj.toString());
        }
        LOG.info("size = " + list.size());
    }

    protected void printObject(Object obj) {
        if (obj == null) {
            LOG.info("Obj=null");
        }
        else {
            LOG.info(obj.toString());
        }
    }

    protected void printPageImpl(@NotNull Page<?> pageImpl) {
        LOG.info("总记录数：" + pageImpl.getTotalElements());
        LOG.info("当前页号索引：" + pageImpl.getNumber());

        LOG.info("每页数据：" + pageImpl.getSize());

        LOG.info("当前页记录数：" + pageImpl.getNumberOfElements());
        LOG.info("当前页号：" + (pageImpl.getNumber() + 1));

        LOG.info("总页数：" + pageImpl.getTotalPages());
        printList(pageImpl.getContent());
    }

    protected void printJson(Object obj) {
        if (obj == null) {
            LOG.info("Obj=null");
        }
        else {
            LOG.info(new Gson().toJson(obj));
        }
    }
}


