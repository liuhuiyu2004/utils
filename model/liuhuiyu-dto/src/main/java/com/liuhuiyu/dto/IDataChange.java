package com.liuhuiyu.dto;

/**
 * 数据更新接口
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-06-20 22:03
 */
public interface IDataChange extends IOperating {
    String NONE_MODEL = Operating.NONE_MODEL;
    String ADD_MODEL = Operating.ADD_MODEL;
    String UPDATE_MODEL = Operating.UPDATE_MODEL;
    String DELETE_MODEL = Operating.DELETE_MODEL;
}
