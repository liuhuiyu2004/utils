package com.liuhuiyu.jpa;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-13 10:31
 */
public class BaseViewTest extends BaseView {
    public BaseViewTest(DataSource dataSource) {
        super(dataSource);
    }

    //region 封装测试
    public void test() {
        Object inputObj = null;
        String sql = "";
        Map<String, Object> parameterMap = new HashMap<>();
        final Long aLong = new SelectBuilder<>(sql).buildCount();
        final Optional<Object> o = new SelectBuilder<>((obj) -> obj, sql).buildFirst();
        final List<Object> objects = new SelectBuilder<>((obj) -> obj, sql)
                .whereFull(inputObj, (inputO, sqlBuilder, map) -> {
                })
                .buildList();
        final List<Object> objects1 = new SelectBuilder<>((obj) -> obj, sql)
                .parameterMap(parameterMap)
                .buildList();

    }
    //endregion
}