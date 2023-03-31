package com.liuhuiyu.core.data.collection;

import com.liuhuiyu.core.thread.ThreadPoolExecutorBuilder;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * 采集数据消费者（有序）
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-30 21:10
 */
public class CollectionConsumerVectorImpl<T> implements ICollectionConsumer<T> {
    private final String key;
    private final Consumer<CollectionConsumerData<T>> consumer;
    Vector<CollectionConsumerData<T>> vector;
    private final ExecutorService executorService;

    public CollectionConsumerVectorImpl(String key, Consumer<CollectionConsumerData<T>> consumer) {
        this.vector = new Vector<>(0);
        this.key = key;
        this.consumer = consumer;
        this.executorService = ThreadPoolExecutorBuilder.create().builder();
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
        if (this.consumer != null) {
            this.executorService.execute(() -> {
                this.vector.add(new CollectionConsumerData<>(sender, changeData));
                this.run();
            });
        }
    }
}
