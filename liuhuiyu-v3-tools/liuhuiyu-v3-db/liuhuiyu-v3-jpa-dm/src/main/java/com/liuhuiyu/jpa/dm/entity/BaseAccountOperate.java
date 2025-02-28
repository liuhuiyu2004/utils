package com.liuhuiyu.jpa.dm.entity;

import com.liuhuiyu.dto.IDataChange;
import com.liuhuiyu.jpa.comment.Comment;
import com.liuhuiyu.jpa.comment.DbConstant;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.sql.Timestamp;

/**
 * 账号操作基础类
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-12-07 17:03
 */

@MappedSuperclass
public abstract class BaseAccountOperate implements DbConstant {

    //region 添加修改删除 标准字段
    /**
     * 创建时间
     */
    @Column(columnDefinition = TIMESTAMP + " default " + SYSDATE)
    @Comment("创建时间")
    private Timestamp createTime;
    /**
     * 创建人Id
     */
    @Column(columnDefinition = CHAR + " (" + COLUMN_ID_MAX_LENGTH + ")")
    @Comment("创建人Id")
    private String creatorId;
    @Comment("创建人员类型（1.系统；2.用户；3.三方）")
    private int creatorTypeId;
    @Column(columnDefinition = NVARCHAR2 + " (" + COLUMN_DEFAULT_FIELD_MAX_LENGTH + ")")
    @Comment("创建人名称")
    private String creatorName;

    /**
     * 更新时间
     */
    @Column(columnDefinition = " DATE default sysdate")
    @Comment("更新时间")
    private Timestamp updateTime;
    /**
     * 更新人Id
     */
    @Column(columnDefinition = " CHAR(" + COLUMN_ID_MAX_LENGTH + ")")
    @Comment("更新人Id")
    private String updaterId;
    @Comment("更新人员类型（1.系统；2.用户；3.三方）")
    private int updaterTypeId;
    @Column(columnDefinition = NVARCHAR2 + " (" + COLUMN_DEFAULT_FIELD_MAX_LENGTH + ")")
    @Comment("更新人名称")
    private String updaterName;

    /**
     * 删除标记
     */
    @Column(columnDefinition = " CHAR(1) default '0'")
    @Comment("删除标记（0:未删除；1:删除）")
    private boolean deleteMark;
    /**
     * 删除时间
     */
    @Comment("删除时间")
    private Timestamp deleteTime;
    /**
     * 删除人Id
     */
    @Column(columnDefinition = " CHAR(" + COLUMN_ID_MAX_LENGTH + ")")
    @Comment("删除人Id")
    private String deleterId;

    @Comment("删除人员类型（1.系统；2.用户；3.三方）")
    private Integer deleterTypeId;
    @Column(columnDefinition = NVARCHAR2 + " (" + COLUMN_DEFAULT_FIELD_MAX_LENGTH + ")")
    @Comment("删除人名称")
    private String deleterName;
    //endregion

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public boolean isDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(boolean deleteMark) {
        this.deleteMark = deleteMark;
    }

    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeleterId() {
        return deleterId;
    }

    public void setDeleterId(String deleterId) {
        this.deleterId = deleterId;
    }

    public void createOperate(String creatorId, String creatorName, int creatorTypeId) {
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.creatorTypeId = creatorTypeId;
        this.updateOperate(creatorId, creatorName, creatorTypeId);
    }

    public void createOperate(IDataChange operator) {
        this.createOperate(operator.getOperating().getOperatorId(), operator.getOperating().getOperatorName(), operator.getOperating().getOperatorTypeId());
    }

    public void updateOperate(String updaterId, String updaterName, int updaterTypeId) {
        this.updateTime = new Timestamp(System.currentTimeMillis());
        this.updaterId = updaterId;
        this.updaterName = updaterName;
        this.updaterTypeId = updaterTypeId;

        this.deleteMark = false;
        this.deleterId = null;
        this.deleterName = null;
        this.deleteTime = null;
        this.deleterTypeId = null;
    }

    public void updateOperate(IDataChange operator) {
        this.updateOperate(operator.getOperating().getOperatorId(), operator.getOperating().getOperatorName(), operator.getOperating().getOperatorTypeId());
    }

    public void deleteOperate(String deleterId, String deleterName, int deleterTypeId) {
        this.deleteTime = new Timestamp(System.currentTimeMillis());
        this.deleteMark = true;
        this.deleterId = deleterId;
        this.deleterName = deleterName;
        this.deleterTypeId = deleterTypeId;
    }

    public void deleteOperate(IDataChange operator) {
        this.deleteOperate(operator.getOperating().getOperatorId(), operator.getOperating().getOperatorName(), operator.getOperating().getOperatorTypeId());
    }

    //授权管理
    private void authorizationManagement() {
        //TODO
    }

    public int getCreatorTypeId() {
        return creatorTypeId;
    }

    public void setCreatorTypeId(int creatorTypeId) {
        this.creatorTypeId = creatorTypeId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getUpdaterTypeId() {
        return updaterTypeId;
    }

    public void setUpdaterTypeId(int updaterTypeId) {
        this.updaterTypeId = updaterTypeId;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public Integer getDeleterTypeId() {
        return deleterTypeId;
    }

    public void setDeleterTypeId(Integer deleterTypeId) {
        this.deleterTypeId = deleterTypeId;
    }

    public String getDeleterName() {
        return deleterName;
    }

    public void setDeleterName(String deleterName) {
        this.deleterName = deleterName;
    }
}
