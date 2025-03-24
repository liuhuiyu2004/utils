package com.liuhuiyu.web.help;

import com.liuhuiyu.dto.FromPrototype;
import com.liuhuiyu.dto.ISerializationJson;

import java.util.List;

/**
 * 主从结构<p>
 * Created on 2025/3/24 21:04
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class MasterDetailDto <M, D> implements FromPrototype, ISerializationJson {
    /**
     * 主表
     * Created DateTime 2022-12-01 9:50
     */
    private M master;
    /**
     * 从表
     * Created DateTime 2022-12-01 9:50
     */
    private List<D> detail;

    public MasterDetailDto() {
    }

    public M getMaster() {
        return master;
    }

    public void setMaster(M master) {
        this.master = master;
    }

    public List<D> getDetail() {
        return detail;
    }

    public void setDetail(List<D> detail) {
        this.detail = detail;
    }

    @Override
    public <T> T fromPrototype(Class<T> classOfT) {
        return FromPrototype.fromPrototype(classOfT, this);
    }
}
