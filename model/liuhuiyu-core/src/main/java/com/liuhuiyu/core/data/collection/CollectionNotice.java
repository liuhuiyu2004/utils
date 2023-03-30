package com.liuhuiyu.core.data.collection;

import com.liuhuiyu.core.thread.ThreadPoolExecutorBuilder;
import com.liuhuiyu.core.util.IgnoredException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 数据采集通知
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-30 20:50
 */
public class CollectionNotice<T> implements ICollectionNoticeReg<T> {
    private final Object sender;
    private final ExecutorService executorService;
    private final Map<String, ICollectionConsumer<T>> noticeMap = new HashMap<>(0);

    public CollectionNotice(Object sender) {
        this.sender = sender;
        this.executorService = ThreadPoolExecutorBuilder.create().builder();
    }
    /**
     * 注册
     *
     * @param iCollectionConsumer 注册的对象
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:05
     */
    @Override
    public void collectionNoticeReg(ICollectionConsumer <T> iCollectionConsumer) {
        this.noticeMap.put(iCollectionConsumer.getKey(), iCollectionConsumer);
    }

    /**
     * 注销通知
     *
     * @param iCollectionConsumer 注销的对象
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:05
     */
    public void unReg(ICollectionConsumer<T> iCollectionConsumer) {
        this.noticeMap.remove(iCollectionConsumer.getKey());
    }

    /**
     * 数据采集通知
     *
     * @param data 采集的数据
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:03
     */
    public void collectionNotice(T data) {
        this.collectionNotice(data, "");
    }

    /**
     * 数据采集通知
     *
     * @param data 采集的数据
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:03
     */
    public void collectionNotice(T data, String modelName) {
        synchronized (this) {
            CollectionData<T> collectionData = new CollectionData<>(data, modelName);
            this.noticeMap.forEach((key, item) -> IgnoredException.run(() -> this.executorService.execute(() -> item.collectionNotice(sender, collectionData))));
        }
    }


}
