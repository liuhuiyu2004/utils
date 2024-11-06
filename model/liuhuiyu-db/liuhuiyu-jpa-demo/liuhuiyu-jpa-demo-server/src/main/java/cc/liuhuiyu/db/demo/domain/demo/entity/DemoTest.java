package cc.liuhuiyu.db.demo.domain.demo.entity;

import cc.liuhuiyu.db.demo.domain.BaseAccountOperate;
import com.liuhuiyu.jpa.comment.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/12/16 9:26
 */
@Entity
@Table(name = "DemoTest")
@GenericGenerator(name = "DemoTest-uuid", strategy = "uuid")
@Comment("备注")
public class DemoTest extends BaseAccountOperate {
    /*
     * Timestamp
     * String
     * int
     * boolean
     * byte[]
     *
     */

    @Id
    @GenericGenerator(name = "DemoTest-uuid", strategy = "uuid")
    @GeneratedValue(generator = "DemoTest-uuid")
    @Column(columnDefinition = CHAR + "(" + COLUMN_ID_MAX_LENGTH + ") default " + SYS_GUID)
    @Comment("id")
    private String id;
    @Column(columnDefinition = NVARCHAR2 + "(" + COLUMN_CNAME_MAX_LENGTH + ")")
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

    @Comment("金额值")
    private BigDecimal moneyValue;

    public DemoTest() {
    }

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
}