package com.liuhuiyu.dto;

import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询信息
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-12-20 16:13
 */
public class Paging {
    /**
     * 当前页面索引（0开始）
     */
    private int pageIndex;
    /**
     * 页面大小(0，表示不会有列表信息)
     */
    private int pageSize;
    /**
     * 全部数据放入一个页面不分页
     */
    private boolean allInOne;
    /**
     * 排序
     */
    private List<Sort> sort;

    public static final int FIRST_PAGE_INDEX = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MIN_PAGE_SIZE = 0;

    public Paging() {
        pageIndex = FIRST_PAGE_INDEX;
        pageSize = DEFAULT_PAGE_SIZE;
        this.sort = new ArrayList<>();
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, FIRST_PAGE_INDEX);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize < MIN_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public boolean isAllInOne() {
        return allInOne;
    }

    public void setAllInOne(boolean allInOne) {
        this.allInOne = allInOne;
    }

    /**
     * Pageable 实现 bean
     *
     * @return org.springframework.data.domain.PageRequest
     * @author LiuHuiYu
     * Created DateTime 2022-02-11 11:19
     */
    public PageRequest getPageRequest() {
        return PageRequest.of(this.pageIndex, this.pageSize == 0 ? 1 : this.pageSize);
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    /**
     * 获取起始数据NO（1开始）
     *
     * @return java.lang.Integer
     * @author LiuHuiYu
     * Created DateTime 2022-04-01 16:00
     */
    public Integer beginRowNo() {
        return this.pageIndex * pageSize + 1;
    }

    /**
     * 获取结束数据NO(包含此NO的数据)
     *
     * @return java.lang.Integer
     * @author LiuHuiYu
     * Created DateTime 2022-04-01 16:01
     */
    public Integer endRowNo() {
        return beginRowNo() + pageSize - 1;
    }
}