package com.liuhuiyu.demo.domain.demo.dto.in;

import com.liuhuiyu.demo.domain.demo.dto.out.Demo01Dto;
import com.liuhuiyu.dto.IOperating;
import com.liuhuiyu.dto.ISerializationJson;
import com.liuhuiyu.dto.Operating;

/**
 * 功能<p>
 * Created on 2025/7/20 11:04
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class Demo01ChangeDto extends Demo01Dto implements IOperating, ISerializationJson {
    private Operating operating = new Operating();

    /**
     * 获取操作信息
     */
    @Override
    public Operating getOperating() {
        return this.operating;
    }

    /**
     * 设置操作信息
     */
    @Override
    public void setOperating(Operating operating) {
        this.operating = operating;
    }

}
