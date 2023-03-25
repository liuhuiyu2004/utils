package com.liuhuiyu.core.data.change;

/**
 * 数据变更通知注册
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-14 22:00
 */
public interface IChangeNoticeReg<T> {
    void changeNoticeReg(IChangeNotice<T> iChangeNotice);
}
