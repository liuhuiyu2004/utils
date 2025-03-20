package com.liuhuiyu.dto;

/**
 * 分页接口
 * <pre>
 *     &#x40;ApiModelProperty("分页信息")
 *     private Paging paging = new Paging();
 *
 *     &#x40;Override
 *     public Paging getPaging() {
 *         return paging;
 *     }
 *
 *     &#x40;Override
 *     public void setPaging(Paging paging) {
 *         this.paging = paging;
 *     }
 * </pre>
 * Created on 2025/3/20 21:03
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public interface IPaging {
    /**
     * 获取分页信息<p>
     * author LiuHuiYu<p>
     *
     * @return com.liuhuiyu.dto.Paging
     */
    Paging getPaging();

    /**
     * 设置分页信息
     *
     * @param paging 分页信息
     */
    void setPaging(Paging paging);
}
