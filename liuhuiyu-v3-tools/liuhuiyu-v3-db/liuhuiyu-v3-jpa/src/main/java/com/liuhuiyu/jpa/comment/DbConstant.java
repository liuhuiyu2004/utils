package com.liuhuiyu.jpa.comment;

/**
 * 数据库常量
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-20 19:16
 */
public interface DbConstant {

    /**
     * Id长度
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_ID_MAX_LENGTH = 32;
    /**
     * 类型状态名称长度
     * Created DateTime 2023-10-20 19:25
     */
    int TYPE_NAME_MAX_LENGTH = 10;
    /**
     * 类型状态名称长度
     * Created DateTime 2023-10-20 19:25
     */
    int STATE_NAME_MAX_LENGTH = 8;
    /**
     * 姓名长度
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_CNAME_MAX_LENGTH = 16;
    /**
     * email长度
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_EMAIL_MAX_LENGTH = 40;
    /**
     * 通用默认文本字段长度
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_DEFAULT_FIELD_MAX_LENGTH = 100;
    /**
     * 电话长度
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_TEL_MAX_LENGTH = 15;
    /**
     * 身份证长度
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_ID_CARD_MAX_LENGTH = 18;
    /**
     * 车牌号长度
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_NUMBER_PLATE_MAX_LENGTH = 10;
    /**
     * 长文本
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_LONG_TEXT_MAX_LENGTH = 512;
    /**
     * IPv4
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_IP4_MAX_LENGTH = 15;
    /**
     * IPv6
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_IP6_MAX_LENGTH = 46;
    /**
     * mac地址
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_MAC_MAX_LENGTH = 48;
    /**
     * 2D坐标位置
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_2D_COORD_MAX_LENGTH = 33;
    /**
     * 3D坐标位置
     * Created DateTime 2023-10-20 19:25
     */
    int COLUMN_3D_COORD_MAX_LENGTH = 50;


    String CHAR = "CHAR";
    String VARCHAR = "VARCHAR";
    String CLOB = "CLOB";
    /**
     * 长文本型
     * Created DateTime 2024-03-27 5:27
     *
     * @deprecated 尽量使用 CLOB + @LOB 替换
     */
    @Deprecated
    String LONG = "LONG";
    String BLOB = "BLOB";
    String DATE = "DATE";
    String TIMESTAMP = "TIMESTAMP";
    String NUMBER = "NUMBER";

    String NVARCHAR2 = "NVARCHAR2";
    String BOOLEAN = "CHAR(1)";

    String SYSDATE = "sysdate";
    String BOOLEAN_VALUE_FALSE = "'0'";
    String SYS_GUID = "sys_guid()";
}
