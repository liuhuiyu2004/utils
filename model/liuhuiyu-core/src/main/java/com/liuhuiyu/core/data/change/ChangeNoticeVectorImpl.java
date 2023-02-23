package com.liuhuiyu.core.data.change;

import java.util.Vector;
import java.util.function.Consumer;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-23 14:29
 */
public class ChangeNoticeVectorImpl<T> implements IChangeNotice<T> {
    private final String key;
    private final Consumer<Data<T>> consumer;
    Vector<Data<T>> vector;

    public ChangeNoticeVectorImpl(String key, Consumer<Data<T>> consumer) {
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
    public void changeNotice(Object sender, ChangeData<T> changeData) {
        this.vector.add(new Data<>(sender, changeData));
        this.run();
    }

    static class Data<T> {
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
