package com.liuhuiyu.core.util.model;

/**
 * 基础树结构
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-11-03 13:51
 */
public class TreeStructure<T> {
    public static <T> TreeStructure<T> getNullValue() {
        return new TreeStructure<>();
    }
    /**
     * id
     */
    T id;
    /**
     * 上级id
     */
    T parentId;
    /**
     * 名称
     */
    String name;

    public TreeStructure() {
        this.id = null;
        this.parentId = null;
        this.name = null;
    }

    public TreeStructure(T id, T parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public T getParentId() {
        return parentId;
    }

    public void setParentId(T parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
