package com.liuhuiyu.core.data.collection;

import com.liuhuiyu.core.thread.ThreadPoolExecutorBuilder;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * 基础采集数据消费者（无序）
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-30 21:04
 */
public class CollectionConsumerBaseImpl<T> implements ICollectionConsumer<T> {
    private final String key;
    private final Consumer<CollectionConsumerData<T>> consumer;
    private ExecutorService executorService;
    public CollectionConsumerBaseImpl(String key, Consumer<CollectionConsumerData<T>> consumer) {
        this.key = key;
        this.consumer = consumer;
        this.executorService = ThreadPoolExecutorBuilder.create().threadName("ConsumerBase").builder();
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void collectionNotice(Object sender, CollectionData<T> changeData) {
        if (this.consumer != null) {
            this.executorService.execute(() -> this.consumer.accept(new CollectionConsumerData<>(sender, changeData)));
        }
    }
}
