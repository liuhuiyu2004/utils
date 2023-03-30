package com.liuhuiyu.core.data.collection;

import com.liuhuiyu.core.data.change.IChangeNotice;

/**
 * 数据采集通知注册
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-30 20:44
 */
public interface ICollectionNoticeReg<T> {
    void collectionNoticeReg(ICollectionConsumer<T> iCollectionConsumer);
}
