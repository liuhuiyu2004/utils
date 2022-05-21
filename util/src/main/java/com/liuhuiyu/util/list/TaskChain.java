package com.liuhuiyu.util.list;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 任务链
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-20 10:11
 */
public class TaskChain<T> {
    public List<Consumer<T>> taskList = new ArrayList<>();

    public void addTask(Consumer<T> consumer) {
        taskList.add(consumer);
    }

    public void execution(T t) {
        taskList.forEach(consumer -> consumer.accept(t));
    }
}
