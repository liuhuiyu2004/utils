package com.liuhuiyu.jpa_plus.constant;

/**
 * 功能<p>
 * Created on 2025/7/17 11:21
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public interface DbConstantByMySql extends DbConstant {
    String SYS_GUID = "(replace(uuid(),_utf8mb4'-',_utf8mb4''))";
    String NVARCHAR = "NVARCHAR";
    //长文本
    String CLOB = "LONGTEXT";

    String SYSDATE = "CURRENT_TIMESTAMP";
}
