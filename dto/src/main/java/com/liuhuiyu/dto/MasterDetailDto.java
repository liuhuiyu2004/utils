package com.liuhuiyu.dto;


import com.google.gson.Gson;

import java.util.List;

/**
 * 主从结构
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-12-01 4:53
 */
public class MasterDetailDto<M, D> implements FromPrototype, ISerializationJson {
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
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public <T> T fromPrototype(Class<T> classOfT) {
        return FromPrototype.fromPrototype(classOfT, this);
    }
}
