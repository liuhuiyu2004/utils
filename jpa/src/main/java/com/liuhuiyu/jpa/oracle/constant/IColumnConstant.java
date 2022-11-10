package com.liuhuiyu.jpa.oracle.constant;

import com.liuhuiyu.jpa.entity.IUniversallyUniqueIdentifier;

/**
 * 字段常量接口
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-01-28 15:16
 */
public interface IColumnConstant extends IUniversallyUniqueIdentifier {
    /**
     * bool型字符串变量长度
     */
    int COLUMN_BOOL_CHAR_MAX_LENGTH = 1;
    /**
     * NVARCHAR2最大长度
     */
    int COLUMN_NVARCHAR2_MAX_LENGTH = 2000;
    /**
     * VARCHAR2最大长度
     */
    int COLUMN_VARCHAR2_MAX_LENGTH = 4000;
}
