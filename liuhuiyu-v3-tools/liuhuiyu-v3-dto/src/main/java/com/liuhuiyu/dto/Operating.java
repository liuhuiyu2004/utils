package com.liuhuiyu.dto;

import org.springframework.util.ObjectUtils;

/**
 * 操作
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-19 11:17
 */
public class Operating implements Cloneable {
    public static final String NONE_MODEL = "";
    public static final String ADD_MODEL = "add";
    public static final String UPDATE_MODEL = "update";
    public static final String DELETE_MODEL = "delete";
    /**
     * 操作人id
     */
    private String operatorId;
    /**
     * 操作人名称
     */
    private String operatorName;
    /**
     * 操作人类型id（1.系统；2.用户；3.三方）
     */
    private int operatorTypeId;

    /**
     * 操作模式名称（用户自定义）
     */
    private String modelName;

    public Operating() {
        this.operatorId = NONE_MODEL;
        this.modelName = NONE_MODEL;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * 判定是否是指定模式
     *
     * @param modelName 模式名称
     * @return boolean
     * Created DateTime 2022-06-13 8:55
     */
    public boolean isModel(String modelName) {
        return ObjectUtils.nullSafeEquals(this.modelName, modelName);
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public int getOperatorTypeId() {
        return operatorTypeId;
    }

    public void setOperatorTypeId(int operatorTypeId) {
        this.operatorTypeId = operatorTypeId;
    }

    @Override
    @SuppressWarnings("all")
    public Operating clone() {
        Operating operating = new Operating();
        operating.setModelName(this.modelName);
        operating.setOperatorId(this.operatorId);
        operating.setOperatorName(this.operatorName);
        operating.setOperatorTypeId(this.operatorTypeId);
        return operating;
    }
}
