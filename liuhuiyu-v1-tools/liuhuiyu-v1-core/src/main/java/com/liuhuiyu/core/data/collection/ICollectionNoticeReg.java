package com.liuhuiyu.core.data.collection;

/**
 * 数据采集通知注册
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-30 20:44
 */
public interface ICollectionNoticeReg<T> {
    /**
     * 消费者注册
     *
     * @param collectionConsumer 消费者注册
     * Created DateTime 2023-03-31 8:24
     */
    void collectionNoticeReg(ICollectionConsumer<T> collectionConsumer);

    /**
     * 消费者注销
     *
     * @param collectionConsumerKey 消费者Key
     * Created DateTime 2023-03-31 8:24
     */
    void collectionNoticeUnReg(String collectionConsumerKey);
}
