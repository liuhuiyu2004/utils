package com.liuhuiyu.dto;

/**
 * 操作接口
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-02 7:49
 */
public interface IOperating {

    /**
     * 获取操作信息
     *
     * @return java.lang.String
     * @author LiuHuiYu
     * Created DateTime 2022-01-20 15:29
     */
    Operating getOperating();

    /**
     * 设置操作信息
     *
     * @param operating 操作信息
     * @author LiuHuiYu
     * Created DateTime 2022-01-20 15:29
     */
    void setOperating(Operating operating);

    /**
     * 是否是添加信息
     * @author LiuHuiYu
     * Created DateTime 2022-05-10 16:22
     * @return boolean
     */
    boolean isAdd();
    /**
     * 是否是修改信息
     * @author LiuHuiYu
     * Created DateTime 2022-05-10 16:22
     * @return boolean
     */
    boolean isUpdate();
    /**
     * 是否是删除信息
     * @author LiuHuiYu
     * Created DateTime 2022-05-10 16:22
     * @return boolean
     */
    boolean isDelete();
}
