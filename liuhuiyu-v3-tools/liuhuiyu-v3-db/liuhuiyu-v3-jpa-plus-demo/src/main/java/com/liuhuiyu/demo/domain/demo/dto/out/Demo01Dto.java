package com.liuhuiyu.demo.domain.demo.dto.out;

import com.liuhuiyu.dto.FromPrototype;
import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.dto.ISerializationJson;

import java.math.BigDecimal;

/**
 * 功能<p>
 * Created on 2025/7/20 10:21
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class Demo01Dto implements IComputedValueFilling, FromPrototype {
    public static String getBaseSql() {
        return """
               select t.id id,
                      t.create_time createTime,
                      t.creator_id creatorId,
                      t.creator_name creatorName,
                      t.creator_type_id creatorTypeId,
                      t.delete_mark deleteMark,
                      t.delete_time deleteTime,
                      t.deleter_id deleterId,
                      t.deleter_name deleterName,
                      t.deleter_type_id deleterTypeId,
                      t.update_time updateTime,
                      t.updater_id updaterId,
                      t.updater_name updaterName,
                      t.updater_type_id updaterTypeId,
                      t.annex annex,
                      t.cname cname,
                      t.money_value moneyValue,
                      t.remark remark
               from demo_test1 t;
               """;
    }

    public static String getCountSql() {
        return """
               """;
    }

    private String id;
    private String cname;
    private String remark;
    private byte[] annex;
    private BigDecimal moneyValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public byte[] getAnnex() {
        return annex;
    }

    public void setAnnex(byte[] annex) {
        this.annex = annex;
    }

    public BigDecimal getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(BigDecimal moneyValue) {
        this.moneyValue = moneyValue;
    }
    //region

    @Override
    public void computedValueFilling() {
    }
    //endregion
}
