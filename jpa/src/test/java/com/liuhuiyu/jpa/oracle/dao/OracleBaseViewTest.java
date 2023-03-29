package com.liuhuiyu.jpa.oracle.dao;

import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.dto.Paging;
import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-11-20 8:44
 */
public class OracleBaseViewTest extends TestBase {
    @BeforeEach
    public void setup() {
        throw new RuntimeException("此实例不能运行，需要数据库支持，仅作为演示使用。");
    }

    @Test
    public void test() {
        IPaging paging = new IPaging() {
            @Override
            public Paging getPaging() {
                return null;
            }

            @Override
            public void setPaging(Paging paging) {

            }
        };
        final OracleBaseViewDemo instance = OracleBaseViewDemo.getInstance();
        final Long count = instance.findCount(paging);
        final Long count2 = instance.findCount2(paging);
        final Long count3 = instance.findCount3(paging);
        final List<Object> list = instance.findList(paging);
        final Object one = instance.findOne(paging);
        final PageImpl<Object> page = instance.findPage(paging);
        final PageImpl<Object> page2 = instance.findPage2(paging);
        final PageImpl<Object> page3 = instance.findPage3(paging);

        LOG.info(String.join("", Collections.nCopies(8, "{};\n")), count, count2, count3, list,one, page, page2, page3);
    }

}
