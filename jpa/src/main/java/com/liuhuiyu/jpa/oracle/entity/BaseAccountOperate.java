package com.liuhuiyu.jpa.oracle.entity;

import com.liuhuiyu.dto.IDataChange;
import com.liuhuiyu.jpa.entity.IUniversallyUniqueIdentifier;
import com.liuhuiyu.jpa.oracle.comment.Comment;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * 账号操作基础类
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-12-07 17:03
 */

@MappedSuperclass
public abstract class BaseAccountOperate implements IUniversallyUniqueIdentifier {

    //region 添加修改删除 标准字段
    /**
     * 创建时间
     */
    @Column(columnDefinition = " DATE default sysdate")
    @Comment("创建时间")
    private Timestamp createTime;
    /**
     * 创建人Id
     */
    @Column(columnDefinition = " CHAR(" + COLUMN_ID_MAX_LENGTH + ")")
    @Comment("创建人Id")
    private String creatorId;
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

    public void createOperate(String creatorId) {
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.creatorId = creatorId;
        this.updateTime = new Timestamp(System.currentTimeMillis());
        this.updaterId = creatorId;
        this.deleteMark = false;
        this.deleterId = null;
        this.deleteTime = null;
    }

    public void createOperate(IDataChange operator) {
        this.createOperate(operator.getOperating().getOperatorId());
    }

    public void updateOperate(String updaterId) {
        this.updateTime = new Timestamp(System.currentTimeMillis());
        this.updaterId = updaterId;
        this.deleteMark = false;
        this.deleterId = null;
        this.deleteTime = null;
    }

    public void updateOperate(IDataChange operator) {
        this.updateOperate(operator.getOperating().getOperatorId());
    }

    public void deleteOperate(String deleterId) {
        this.deleteMark = true;
        this.deleterId = deleterId;
        this.deleteTime = new Timestamp(System.currentTimeMillis());
    }

    public void deleteOperate(IDataChange operator) {
        this.deleteOperate(operator.getOperating().getOperatorId());
    }
}
