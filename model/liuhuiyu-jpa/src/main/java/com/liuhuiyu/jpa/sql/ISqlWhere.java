package com.liuhuiyu.jpa.sql;

import java.util.List;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-19 15:56
 */
public interface ISqlWhere {
    /**
     * 获取sql条件语句
     *
     * @return java.lang.StringBuilder
     * Created DateTime 2023-09-19 17:34
     */
    StringBuilder getSqlWhere();

    /**
     * 设置sql条件语句
     *
     * @param sqlWhere 条件语句
     * Created DateTime 2023-09-19 17:37
     */
    void setSqlWhere(StringBuilder sqlWhere);

    /**
     * 获取条件参数值
     *
     * @return java.lang.StringBuilder
     * Created DateTime 2023-09-19 17:34
     */
    List<Object> getValues();

    /**
     * 设置条件参数值
     *
     * @param valueList 参数值列表
     * Created DateTime 2023-09-19 17:37
     */
    void setValues(List<Object> valueList);
}
