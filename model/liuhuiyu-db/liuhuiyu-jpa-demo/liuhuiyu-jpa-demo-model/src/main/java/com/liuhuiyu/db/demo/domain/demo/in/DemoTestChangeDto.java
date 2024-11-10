package com.liuhuiyu.db.demo.domain.demo.in;

import com.google.gson.Gson;
import com.liuhuiyu.db.demo.domain.demo.out.DemoTestDto;
import com.liuhuiyu.dto.IOperating;
import com.liuhuiyu.dto.ISerializationJson;
import com.liuhuiyu.dto.Operating;
import com.liuhuiyu.json.map.MapUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/11/6 16:46
 */
@ApiModel(value = "DemoTestChangeDto", description = "DemoTest更新数据")
public class DemoTestChangeDto extends DemoTestDto implements IOperating, ISerializationJson {
    public static final String ADD_MODEL = "add";
    public static final String UPDATE_MODEL = "update";
    public static final String DELETE_MODEL = "delete";
    @ApiModelProperty("数据（增删改）操作")
    private Operating operating = new Operating();

    /**
     * 获取操作信息
     *
     * @return com.liuhuiyu.dto.Operating
     * @author LiuHuiYu     * Created DateTime 2024/11/6 16:46
     */
    @Override
    public Operating getOperating() {
        return this.operating;
    }

    /**
     * 设置操作信息
     *
     * @param operating 操作信息
     * @author LiuHuiYu     * Created DateTime 2024/11/6 16:46
     */
    @Override
    public void setOperating(Operating operating) {
        this.operating = operating;
    }

    public boolean isModel(String modelName) {
        return this.operating.isModel(modelName);
    }

    public DemoTestChangeDto() {
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
     * @return DemoTestChangeDto
     * @author LiuHuiYu     * Created DateTime 2024/11/6 16:46
     */
    public static DemoTestChangeDto ofSelfMap(Map<String, Object> map) {
        return MapUtil.fromMap(map, DemoTestChangeDto.class);
    }
}