package com.liuhuiyu.jpa.sql;

import java.util.List;

/**
 * 条件过滤接口<p>
 * Created on 2025/4/8 21:44
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public interface ConditionalFiltering {
    /**
     * 获取当前条件<p>
     * author liuhuiyu<p>
     *
     * @return java.lang.StringBuffer
     */
    StringBuffer getConditional();

    /**
     * 获取条件参数<p>
     * author liuhuiyu<p>
     *
     * @return java.util.List<java.lang.Object>
     */
    List<Object> getParameterList();
}
