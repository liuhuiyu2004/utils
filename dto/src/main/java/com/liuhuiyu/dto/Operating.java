package com.liuhuiyu.dto;

import org.springframework.util.ObjectUtils;

import java.util.Locale;

/**
 * 操作
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-02 7:49
 */
public class Operating {
    public static final String NONE = "";
    /**
     * 操作人id
     */
    private String operatorId;
    /**
     * 操作模式名称（用户自定义）
     */
    private String modelName;
    /**
     * 记录行状态
     */
    private RowStatus rowStatus;

    public Operating() {
        this.operatorId = NONE;
        this.modelName = NONE;
        this.rowStatus = RowStatus.U;
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
     * @author LiuHuiYu
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

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }

    /**
     * 记录行状态
     *
     * @author LiuHuiYu
     * Created DateTime 2022-05-02 7:45
     */
    public enum RowStatus {
        /**
         * 添加
         */
        A,
        /**
         * 修改
         */
        E,
        /**
         * 删除
         */
        D,
        /**
         * 未知
         */
        U;

        RowStatus() {
        }

        public boolean isAdd() {
            return this.equals(A);
        }

        public boolean isUpdate() {
            return this.equals(E);
        }

        public boolean isDelete() {
            return this.equals(D);
        }

        public boolean isUnknown() {
            return this.equals(U);
        }

        public static RowStatus fromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            }
            catch (Exception var2) {
                throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'A' 'E' 'D' 'U' (case insensitive).", value), var2);
            }
        }
    }

}
