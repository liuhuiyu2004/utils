package com.liuhuiyu.demo.domain.demo.entity;

import com.liuhuiyu.demo.domain.BaseAccountOperate;
import com.liuhuiyu.jpa_plus.comment.Comment;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

/**
 * 功能<p>
 * Created on 2025/7/17 11:06
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
@Entity
@Table(name = "DEMO_TEST1")
@Comment("备注")
public class Demo01 extends BaseAccountOperate {
    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.liuhuiyu.jpa_plus.util.CustomUUIDGenerator")
    @Column(columnDefinition = CHAR + "(" + COLUMN_ID_MAX_LENGTH + ") default " + SYS_GUID)
    @Comment("id")
    private String id;
    @Column(columnDefinition = NVARCHAR + "(" + COLUMN_CNAME_MAX_LENGTH + ")")
    @Comment("名称")
    private String cname;
    @Column(columnDefinition = CLOB)
    @Lob
    @Comment("长文本备注")
    private String remark;
    @Column(columnDefinition = BLOB)
    @Lob
    @Comment("字节码附件")
    private byte[] annex;
//
    @Comment("金额值")
    private BigDecimal moneyValue;
//
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
//
//    public String getCname() {
//        return cname;
//    }
//
//    public void setCname(String cname) {
//        this.cname = cname;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public byte[] getAnnex() {
//        return annex;
//    }
//
//    public void setAnnex(byte[] annex) {
//        this.annex = annex;
//    }
//
//    public BigDecimal getMoneyValue() {
//        return moneyValue;
//    }
//
//    public void setMoneyValue(BigDecimal moneyValue) {
//        this.moneyValue = moneyValue;
//    }
}
