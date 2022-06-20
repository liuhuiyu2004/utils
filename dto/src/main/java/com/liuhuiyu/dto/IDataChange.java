package com.liuhuiyu.dto;

/**
 * 数据更新接口
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-06-20 22:03
 */
public interface IDataChange extends IOperating{

    /**
     * 是否是添加信息
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2022-05-10 16:22
     */
    boolean isAdd();

    /**
     * 是否是修改信息
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2022-05-10 16:22
     */
    boolean isUpdate();

    /**
     * 是否是删除信息
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2022-05-10 16:22
     */
    boolean isDelete();
}
