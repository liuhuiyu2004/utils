package com.liuhuiyu.jpa.oracle.dao;

import com.liuhuiyu.dto.IPaging;
import com.liuhuiyu.dto.Paging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.data.domain.PageImpl;

import java.util.List;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-11-20 8:44
 */
public class OracleBaseViewTest {
    private static final Logger LOG = LogManager.getLogger(OracleBaseViewDemo.class);

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
        final List<Object> list = instance.findList(paging);
        final PageImpl<Object> page = instance.findPage(paging);
        LOG.info("{};{};{}", count, list, page);
    }

}
