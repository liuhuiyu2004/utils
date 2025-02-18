package com.liuhuiyu.core.data.change;

import java.util.function.Consumer;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-23 14:16
 */
public class ChangeNoticeBaseImpl<T> implements IChangeNotice<T> {
    private final String key;
    private final Consumer<Data<T>> consumer;

    public ChangeNoticeBaseImpl(String key, Consumer<Data<T>> consumer) {
        this.key = key;
        this.consumer = consumer;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void changeNotice(Object sender, ChangeData<T> changeData) {
        if (this.consumer != null) {
            this.consumer.accept(new Data<>(sender, changeData));
        }
    }

    public static class Data<T> {
        Object sender;
        ChangeData<T> changeData;

        public Data(Object sender, ChangeData<T> changeData) {
            this.sender = sender;
            this.changeData = changeData;
        }

        public Object getSender() {
            return sender;
        }

        public ChangeData<T> getChangeData() {
            return changeData;
        }
    }
}
