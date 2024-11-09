package com.liuhuiyu.db.demo.domain.demo.in;

import com.google.gson.Gson;
import com.liuhuiyu.dto.*;
import com.liuhuiyu.json.map.MapUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Locale;
import java.util.Map;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/11/6 16:46
 */
@ApiModel(value = "DemoTestFindDto", description = "DemoTestFindDto查找")
public class DemoTestFindDto implements IFind, ISerializationJson {
    public static final String FIND_BY_ID_MODEL = "findById";

    public static DemoTestFindDto findById(String id) {
        DemoTestFindDto resData = new DemoTestFindDto();
        resData.getOperating().setModelName(FIND_BY_ID_MODEL);
        resData.setId(id);
        return resData;
    }

    public DemoTestFindDto() {
    }

    private String id;

    @ApiModelProperty("操作人")
    Operating operating = new Operating();

    @ApiModelProperty("分页信息")
    private Paging paging = new Paging();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Paging getPaging() {
        return paging;
    }

    @Override
    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public Operating getOperating() {
        return this.operating;
    }

    @Override
    public void setOperating(Operating operating) {
        this.operating = operating;
    }

    public boolean isModel(String modelName) {
        return this.operating.isModel(modelName);
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
     * @return DemoTestFindDto
     * @author LiuHuiYu     * Created DateTime 2024/11/6 16:46
     */
    public static DemoTestFindDto ofSelfMap(Map<String, Object> map) {
        return MapUtil.fromMap(map, DemoTestFindDto.class);
    }
}
