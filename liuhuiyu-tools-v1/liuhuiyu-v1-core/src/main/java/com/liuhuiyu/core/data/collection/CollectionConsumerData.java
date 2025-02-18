package com.liuhuiyu.core.data.collection;

/**
 * 采集消费数据
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-31 8:01
 */
public class CollectionConsumerData<T> {
    Object sender;
    CollectionData<T> collectionData;

    public CollectionConsumerData(Object sender, CollectionData<T> collectionData) {
        this.sender = sender;
        this.collectionData = collectionData;
    }

    public Object getSender() {
        return sender;
    }

    public CollectionData<T> getCollectionData() {
        return collectionData;
    }
}
