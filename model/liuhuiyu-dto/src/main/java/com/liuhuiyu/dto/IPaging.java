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
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-01-12 10:22
 */
public interface IPaging {
    /**
     * 获取分页信息<p>
     * author LiuHuiYu<p>
     * Created DateTime 2025/2/21 16:52
     * @return com.liuhuiyu.dto.Paging
     */
    Paging getPaging();
    /**
     * 设置分页信息
     * @author LiuHuiYu
     * Created DateTime 2022-01-20 15:29
     * @param paging 分页信息
     */
    void setPaging(Paging paging);
}
