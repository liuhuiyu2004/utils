package com.liuhuiyu.core.data.change;

/**
 * 更新数据管理
 * 不建议使用
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-07 12:45
 */
@Deprecated
public class ChangeNoticeManage<T> {

    private final ChangeNotice<T> changeNotice;

    public ChangeNoticeManage(ChangeNotice<T> changeNotice) {
        this.changeNotice = changeNotice;
    }

    /**
     * 注册
     *
     * @param iChangeNotice 注册的对象
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:05
     */
    public void reg(IChangeNotice<T> iChangeNotice) {
        this.changeNotice.reg(iChangeNotice);
    }

    /**
     * 注销通知
     *
     * @param iChangeNotice 注销的对象
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:05
     */
    public void unReg(IChangeNotice<T> iChangeNotice) {
        this.changeNotice.unReg(iChangeNotice);
    }
}
