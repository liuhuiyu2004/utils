package com.liuhuiyu.core.data.change;

/**
 * 数据变更通知接口
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-07 12:36
 */
public interface IChangeNotice<T> {
    /**
     * 注册key
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:02
     * @return java.lang.String
     */
    String getKey();
    /**
     * 功能描述
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:02
     * @param sender     消息发送者
     * @param changeData 更新的数据信息
     */
    void changeNotice(Object sender,ChangeData<T> changeData);
}
