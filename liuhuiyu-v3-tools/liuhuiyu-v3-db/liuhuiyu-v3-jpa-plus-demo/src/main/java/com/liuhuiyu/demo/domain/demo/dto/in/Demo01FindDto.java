package com.liuhuiyu.demo.domain.demo.dto.in;

import com.liuhuiyu.dto.IFind;
import com.liuhuiyu.dto.ISerializationJson;
import com.liuhuiyu.dto.Operating;
import com.liuhuiyu.dto.Paging;

/**
 * 功能<p>
 * Created on 2025/7/20 10:58
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class Demo01FindDto implements IFind, ISerializationJson {

    private static final String FIND_BY_ID_MODEL = "findById";
    Operating operating = new Operating();
    private Paging paging = new Paging();

    public static Demo01FindDto findById(String id) {
        Demo01FindDto resData = new Demo01FindDto();
        resData.getOperating().setModelName(Demo01FindDto.FIND_BY_ID_MODEL);
        resData.setId(id);
        return resData;
    }
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
