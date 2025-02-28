package com.liuhuiyu.core.data.collection;

/**
 * 采集数据消费者接口
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-30 20:53
 */
public interface ICollectionConsumer<T> {
    /**
     * 注册key
     *
     * @return java.lang.String
     * Created DateTime 2023-02-07 14:02
     */
    String getKey();

    /**
     * 采集通知
     *
     * @param sender     消息发送者
     * @param changeData 更新的数据信息
     *                   Created DateTime 2023-02-07 14:02
     */
    void collectionNotice(Object sender, CollectionData<T> changeData);
}
