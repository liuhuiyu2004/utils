package com.liuhuiyu.dto;

/**
 * 功能<p>
 * Created on 2025/8/10 19:57
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 8
 */
public class AbstractFindImpl implements IFind {
    private Operating operating = new Operating();
    private Paging paging = new Paging();


    @Override
    public Operating getOperating() {
        return this.operating;
    }

    @Override
    public void setOperating(Operating operating) {
        this.operating = operating;
    }

    @Override
    public Paging getPaging() {
        return this.paging;
    }

    @Override
    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
