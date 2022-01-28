package com.liuhuiyu.jpa.oracle.constant;

/**
 * 字段常量接口
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-01-28 15:16
 */
public interface IColumnConstant {
    /**
     * UUID长度
     */
    int COLUMN_ID_MAX_LENGTH = 32;
    /**
     * NVARCHAR2最大长度
     */
    int COLUMN_NVARCHAR2_MAX_LENGTH = 2000;
    /**
     * VARCHAR2最大长度
     */
    int COLUMN_VARCHAR2_MAX_LENGTH = 4000;
}
