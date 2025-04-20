package com.liuhuiyu.jpa.mysql.constant;

import com.liuhuiyu.jpa.constant.DbConstant;

/**
 * 功能<p>
 * Created on 2025/4/13 10:11
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public interface DbMySqlConstant extends DbConstant {
    //region 数据类型
    //字符
    String CHAR = "VARCHAR";
    //变长中文字符
    String NVARCHAR = "VARCHAR";
    //长文本
    String CLOB = "LONGTEXT";
    //二进制
    String BLOB = "LONGBLOB";
    //日期
    String DATE = "DATE";
    //时间戳
    String TIMESTAMP = "TIMESTAMP";
    //整形
    String NUMBER = "INT";
    //小数
    String DECIMAL = "DECIMAL";
    //布尔
    String BOOLEAN = "BIT";
    //endregion

}
