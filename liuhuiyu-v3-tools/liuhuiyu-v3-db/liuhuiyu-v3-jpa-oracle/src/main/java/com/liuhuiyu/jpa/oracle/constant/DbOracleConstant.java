package com.liuhuiyu.jpa.oracle.constant;

import com.liuhuiyu.jpa.comment.DbConstant;

/**
 * 数据库常量
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-20 19:16
 */
public interface DbOracleConstant extends DbConstant {

    /**
     * 长文本型
     * Created DateTime 2024-03-27 5:27
     *
     * @deprecated 尽量使用 CLOB + @LOB 替换
     */
    @Deprecated
    String LONG = "LONG";
    String CHAR = "CHAR";
    String VARCHAR = "VARCHAR";
    String CLOB = "CLOB";
    String BLOB = "BLOB";
    String DATE = "DATE";
    String TIMESTAMP = "TIMESTAMP";
    String NUMBER = "NUMBER";
    String NVARCHAR2 = "NVARCHAR2";
    String VARCHAR2 = "VARCHAR2";
    String BOOLEAN = "CHAR(1)";

    String SYSDATE = "sysdate";
    String BOOLEAN_VALUE_FALSE = "'0'";
    String SYS_GUID = "sys_guid()";
}
