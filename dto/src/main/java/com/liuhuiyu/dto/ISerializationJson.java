package com.liuhuiyu.dto;

import java.io.Serializable;

/**
 * 将当前类序列化成json字符串
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-28 10:15
 */
public interface ISerializationJson extends Serializable {
    /**
     * 将当前类序列化成json字符串
     * @author LiuHuiYu
     * Created DateTime 2022-01-20 15:30
     * @return java.lang.String
     */
    String toJson();
}