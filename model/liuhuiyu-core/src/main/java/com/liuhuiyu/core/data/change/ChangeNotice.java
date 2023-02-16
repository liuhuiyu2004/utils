package com.liuhuiyu.core.data.change;

import com.liuhuiyu.core.util.IgnoredException;

import java.util.HashMap;
import java.util.Map;

/**
 * 更新通知
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-07 14:13
 */
public class ChangeNotice<T> {
    private final Object sender;

    private final Map<String, IChangeNotice<T>> noticeMap = new HashMap<>(0);

    public ChangeNotice(Object sender) {
        this.sender = sender;
    }

    /**
     * 注册
     *
     * @param iChangeNotice 注册的对象
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:05
     */
    public void reg(IChangeNotice<T> iChangeNotice) {
        this.noticeMap.put(iChangeNotice.getKey(), iChangeNotice);
    }

    /**
     * 注销通知
     *
     * @param iChangeNotice 注销的对象
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:05
     */
    public void unReg(IChangeNotice<T> iChangeNotice) {
        this.noticeMap.remove(iChangeNotice.getKey());
    }

    /**
     * 数据更新通知
     *
     * @param data        变更的数据
     * @param dataStatus  变更数据的状态
     * @param changeModel 更新模式
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:04
     */
    public void changeNotice(T data, ChangeData.DataStatus dataStatus, String changeModel) {
        this.noticeMap.forEach((key, item) -> IgnoredException.run(() -> item.changeNotice(sender, new ChangeData<>(data, dataStatus, changeModel))));
    }
    /**
     * 数据更新通知
     *
     * @param data        变更的数据
     * @param dataStatus  变更数据的状态
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:04
     */
    public void changeNotice(T data, ChangeData.DataStatus dataStatus) {
        this.changeNotice(data, dataStatus, null);
    }
    /**
     * 数据更新通知
     *
     * @param data 变更的数据
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:03
     */
    public void changeNotice(T data) {
        this.changeNotice(data, null, null);
    }
}
