package com.liuhuiyu.db.demo.domain.demo.out;

import com.google.gson.Gson;
import com.liuhuiyu.dto.FromPrototype;
import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.dto.ISerializationJson;
import com.liuhuiyu.json.map.MapUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/11/6 16:46
 */
@ApiModel(value = "DemoTestDto", description = "DemoTestDto数据")
public class DemoTestDto implements FromPrototype, ISerializationJson, IComputedValueFilling {
    public static String getBaseSql() {
        return "select t.id," +
                "       t.create_time createTime," +
                "       t.creator_id creatorId," +
                "       t.creator_name creatorName," +
                "       t.creator_type_id creatorTypeId," +
                "       t.delete_mark deleteMark," +
                "       t.delete_time deleteTime," +
                "       t.deleter_id deleterId," +
                "       t.deleter_name deleterName," +
                "       t.deleter_type_id deleterTypeId," +
                "       t.update_time updateTime," +
                "       t.updater_id updaterId," +
                "       t.updater_name updaterName," +
                "       t.updater_type_id updaterTypeId," +
                "       t.annex annex," +
                "       t.cname cname," +
                "       t.money_value moneyValue," +
                "       t.remark remark" +
                " from demo_test t";
    }

    public static String getCountSql() {
        return "select count(1) recordNum" +
                " from demo_test t";
    }

    @ApiModelProperty("id")
    private String id;
    private Timestamp createTime;
    private String creatorId;
    private String creatorName;
    private int creatorTypeId;
    private String deleteMark;
    private Timestamp deleteTime;
    private String deleterId;
    private String deleterName;
    private int deleterTypeId;
    private Timestamp updateTime;
    private String updaterId;
    private String updaterName;
    private int updaterTypeId;
    private String annex;
    private String cname;
    private BigDecimal moneyValue;
    private String remark;

    public DemoTestDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getCreatorTypeId() {
        return creatorTypeId;
    }

    public void setCreatorTypeId(int creatorTypeId) {
        this.creatorTypeId = creatorTypeId;
    }

    public String getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(String deleteMark) {
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

    public String getDeleterName() {
        return deleterName;
    }

    public void setDeleterName(String deleterName) {
        this.deleterName = deleterName;
    }

    public int getDeleterTypeId() {
        return deleterTypeId;
    }

    public void setDeleterTypeId(int deleterTypeId) {
        this.deleterTypeId = deleterTypeId;
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

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public int getUpdaterTypeId() {
        return updaterTypeId;
    }

    public void setUpdaterTypeId(int updaterTypeId) {
        this.updaterTypeId = updaterTypeId;
    }

    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public BigDecimal getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(BigDecimal moneyValue) {
        this.moneyValue = moneyValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * 根据 map 还原 DemoTestDto
     *
     * @param map 映射map
     * @return DemoTestDto
     * @author LiuHuiYu     * Created DateTime 2024/11/6 16:46
     */
    public static DemoTestDto ofSelfMap(Map<String, Object> map) {
        return MapUtil.fromMap(map, DemoTestDto.class);
    }

    @Override
    public <T> T fromPrototype(Class<T> classOfT) {
        return FromPrototype.fromPrototype(classOfT, this);
    }

    @Override
    public void computedValueFilling() {
    }
}
