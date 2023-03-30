package com.liuhuiyu.core.data.collection;

import java.util.Vector;
import java.util.function.Consumer;

/**
 * 采集数据消费者（有序）
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-30 21:10
 */
public class CollectionConsumerVectorImpl<T> implements ICollectionConsumer<T> {
    private final String key;
    private final Consumer<Data<T>> consumer;
    Vector<Data<T>> vector;

    public CollectionConsumerVectorImpl(String key, Consumer<Data<T>> consumer) {
        this.vector = new Vector<>(0);
        this.key = key;
        this.consumer = consumer;
    }

    private void run() {
        synchronized (this) {
            while (this.vector.size() > 0) {
                this.consumer.accept(this.vector.get(0));
                this.vector.remove(0);
            }
        }
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void collectionNotice(Object sender, CollectionData<T> changeData) {
        this.vector.add(new Data<>(sender, changeData));
        this.run();
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
