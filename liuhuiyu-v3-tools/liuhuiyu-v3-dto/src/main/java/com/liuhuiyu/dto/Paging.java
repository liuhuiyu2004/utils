package com.liuhuiyu.dto;

import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询信息<p>
 * Created on 2025/3/20 21:01
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
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
    /**
     * 第一页索引
     */
    public static final int FIRST_PAGE_INDEX = 0;
    /**
     * 默认页面大小
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * 页面最小值
     */
    public static final int MIN_PAGE_SIZE = 0;

    /**
     * 分页信息获取
     */
    public Paging() {
        pageIndex = FIRST_PAGE_INDEX;
        pageSize = DEFAULT_PAGE_SIZE;
        this.sort = new ArrayList<>();
    }

    /**
     * 获取页面索引
     *
     * @return int
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置获取第几页信息
     *
     * @param pageIndex 页面（0开始）
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, FIRST_PAGE_INDEX);
    }

    /**
     * 设置页面大小
     *
     * @return int
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页面大小
     *
     * @param pageSize 页面大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize < MIN_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * 是否获取全部数据
     *
     * @return boolean
     */
    public boolean isAllInOne() {
        return allInOne;
    }

    /**
     * 设置是否获取全部数据
     *
     * @param allInOne 设置是否获取全部数据
     */
    public void setAllInOne(boolean allInOne) {
        this.allInOne = allInOne;
    }

    /**
     * Pageable 实现 bean
     *
     * @return org.springframework.data.domain.PageRequest
     */
    public PageRequest getPageRequest() {
        return PageRequest.of(this.pageIndex, this.pageSize == 0 ? 1 : this.pageSize);
    }

    /**
     * 获取排序规则
     *
     * @return java.util.List&lt;com.liuhuiyu.dto.Sort>
     */
    public List<Sort> getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    /**
     * 获取起始数据NO（1开始）
     *
     * @return java.lang.Integer
     */
    public Integer beginRowNo() {
        return this.pageIndex * pageSize + 1;
    }

    /**
     * 获取结束数据NO(包含此NO的数据)
     *
     * @return java.lang.Integer
     */
    public Integer endRowNo() {
        return beginRowNo() + pageSize - 1;
    }
}
