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
    /**
     * 第一页索引
     * Created DateTime 2022-05-28 14:55
     */
    public static final int FIRST_PAGE_INDEX = 0;
    /**
     * 默认页面大小
     * Created DateTime 2022-05-28 14:55
     */
    public static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * 页面最小值
     * Created DateTime 2022-05-28 14:55
     */
    public static final int MIN_PAGE_SIZE = 0;

    /**
     * 分页信息获取
     *
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:55
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
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:55
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置获取第几页信息
     *
     * @param pageIndex 页面（0开始）
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:54
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, FIRST_PAGE_INDEX);
    }

    /**
     * 设置页面大小
     *
     * @return int
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:54
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页面大小
     *
     * @param pageSize 页面大小
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:54
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize < MIN_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * 是否获取全部数据
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:54
     */
    public boolean isAllInOne() {
        return allInOne;
    }

    /**
     * 设置是否获取全部数据
     *
     * @param allInOne 设置是否获取全部数据
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:53
     */
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

    /**
     * 获取排序规则
     *
     * @return java.util.List<com.liuhuiyu.dto.Sort>
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:53
     */
    public List<Sort> getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     * @author LiuHuiYu
     * Created DateTime 2022-05-28 14:53
     */
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