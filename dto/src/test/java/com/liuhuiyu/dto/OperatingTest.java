package com.liuhuiyu.dto;


/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-02 8:06
 */
class OperatingTest {
    public void create() {
        Operating operating = new Operating();
        operating.setRowStatus(Operating.RowStatus.A);
        System.out.println(operating);
    }
}