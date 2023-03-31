package com.liuhuiyu.core.data.collection;

/**
 * 采集到的数据
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-30 20:45
 */
public class CollectionData<T> {
    T data;
    String collectionModel;

    public CollectionData(T data, String collectionModel) {
        this.data = data;
        this.collectionModel = collectionModel;
    }

    public T getData() {
        return data;
    }

    public String getCollectionModel() {
        return collectionModel;
    }

    /**
     * 采集的模式
     *
     * @param modelName 更新模式名称
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:06
     */
    public boolean isCollectionModel(String modelName) {
        return this.collectionModel != null && this.collectionModel.equals(modelName);
    }
}
