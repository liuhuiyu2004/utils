package com.liuhuiyu.jpa_plus.constant;

/**
 * 功能<p>
 * Created on 2025/4/23 20:42
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */

public interface DbConstant {

    /**
     * Id长度
     */
    int COLUMN_ID_MAX_LENGTH = 32;
    /**
     * 类型状态名称长度
     */
    int TYPE_NAME_MAX_LENGTH = 10;
    /**
     * 类型状态名称长度
     */
    int STATE_NAME_MAX_LENGTH = 8;
    /**
     * 姓名长度
     */
    int COLUMN_CNAME_MAX_LENGTH = 16;
    /**
     * email长度
     */
    int COLUMN_EMAIL_MAX_LENGTH = 40;
    /**
     * 通用默认文本字段长度
     */
    int COLUMN_DEFAULT_FIELD_MAX_LENGTH = 100;
    /**
     * 电话长度
     */
    int COLUMN_TEL_MAX_LENGTH = 15;
    /**
     * 身份证长度
     */
    int COLUMN_ID_CARD_MAX_LENGTH = 18;
    /**
     * 车牌号长度
     */
    int COLUMN_NUMBER_PLATE_MAX_LENGTH = 10;
    /**
     * 长文本
     */
    int COLUMN_LONG_TEXT_MAX_LENGTH = 512;
    /**
     * IPv4
     */
    int COLUMN_IP4_MAX_LENGTH = 15;
    /**
     * IPv6
     */
    int COLUMN_IP6_MAX_LENGTH = 46;
    /**
     * mac地址
     */
    int COLUMN_MAC_MAX_LENGTH = 48;
    /**
     * 2D坐标位置
     */
    int COLUMN_2D_COORD_MAX_LENGTH = 33;
    /**
     * 3D坐标位置
     */
    int COLUMN_3D_COORD_MAX_LENGTH = 50;

    //region 数据类型
    //字符
    String CHAR = "CHAR";
    //变长中文字符
    String NVARCHAR = "NVARCHAR2";
    //长文本
    String CLOB = "CLOB";
    //二进制
    String BLOB = "BLOB";
    //日期
    String DATE = "DATE";
    //时间戳
    String TIMESTAMP = "TIMESTAMP";
    //整形
    String NUMBER = "NUMBER";
    //小数
    String DECIMAL = "DECIMAL";
    //布尔
    String BOOLEAN = "BIT";
    //endregion
    String SYSDATE = "sysdate";
    String BOOLEAN_VALUE_FALSE = "'0'";
    String SYS_GUID = "sys_guid()";
}