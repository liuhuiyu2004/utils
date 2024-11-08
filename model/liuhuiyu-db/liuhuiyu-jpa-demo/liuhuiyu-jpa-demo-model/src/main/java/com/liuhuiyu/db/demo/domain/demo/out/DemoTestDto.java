package com.liuhuiyu.db.demo.domain.demo.out;

import com.google.gson.Gson;
import com.liuhuiyu.dto.FromPrototype;
import com.liuhuiyu.dto.IComputedValueFilling;
import com.liuhuiyu.dto.ISerializationJson;
import com.liuhuiyu.json.map.MapUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/11/6 16:46
 */
@ApiModel(value = "DemoTestDto", description = "DemoTestDto数据")
public class DemoTestDto implements FromPrototype, ISerializationJson, IComputedValueFilling {
    public static String getBaseSql() {
        return "select  from  t";
    }

    public static String getCountSql() {
        return "select count(1) recordNum from  t";
    }

    @ApiModelProperty("id")
    private String id;

    public DemoTestDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
