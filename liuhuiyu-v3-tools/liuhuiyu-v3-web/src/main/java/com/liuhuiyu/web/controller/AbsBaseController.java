package com.liuhuiyu.web.controller;

/**
 * 基础页面p>
 * Created on 2025/3/24 21:02
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class AbsBaseController {
    /**
     * 首页
     */
    protected static final String PATH_INDEX = "/index";
    /**
     * 当前目录默认地址
     */
    protected static final String PATH_DEFAULT = "/";
    /**
     * 页号属性
     */
    public static final String ATTRIBUTE_PAGE_INDEX = "page_index";
    /**
     * 页面大小
     */
    public static final String ATTRIBUTE_PAGE_SIZE = "page_size";
    /**
     * id
     */
    public static final String ATTRIBUTE_ID = "id";
    /**
     * 默认页面大小
     */
    protected static final int DEF_PAGE_SIZE = 20;
}
