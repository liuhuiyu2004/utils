package com.liuhuiyu.dto;

/**
 * 更新类<p>
 * Created on 2025/8/6 20:55
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 8
 */
public abstract class AbstractChange<T> implements IDataChange, IOperating {
    T data;
    private Operating operating = new Operating();

    @Override
    public Operating getOperating() {
        return this.operating;
    }

    @Override
    public void setOperating(Operating operating) {
        this.operating = operating;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
