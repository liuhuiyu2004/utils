package com.liuhuiyu.core.data.collection;

import java.util.function.Consumer;

/**
 * 基础采集数据消费者（无序）
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-30 21:04
 */
public class CollectionConsumerBaseImpl<T> implements ICollectionConsumer<T> {
    private final String key;
    private final Consumer<Data<T>> consumer;
    public CollectionConsumerBaseImpl(String key, Consumer<Data<T>> consumer) {
        this.key = key;
        this.consumer = consumer;
    }
    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void collectionNotice(Object sender, CollectionData<T> changeData) {
        if (this.consumer != null) {
            this.consumer.accept(new Data<>(sender, changeData));
        }
    }

    public static class Data<T> {
        Object sender;
        CollectionData<T> collectionData;

        public Data(Object sender, CollectionData<T> collectionData) {
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
}
