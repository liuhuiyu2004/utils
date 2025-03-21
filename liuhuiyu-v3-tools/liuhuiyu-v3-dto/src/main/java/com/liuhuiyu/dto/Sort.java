package com.liuhuiyu.dto;

import java.util.Locale;

/**
 * 功能<p>
 * Created on 2025/3/22 00:30
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class Sort {
    private int index;
    private String name;
    private Direction direction;

    /**
     * 初始化排序
     * <p>
     * Created DateTime 2022-05-28 15:04
     */
    public Sort() {
    }

    /**
     * 初始化排序
     *
     * @param index     索引
     * @param name      字段名称
     * @param direction 排序方向
     *                  Created DateTime 2022-05-28 15:05
     */
    public Sort(int index, String name, Direction direction) {
        this.index = index;
        this.name = name;
        this.direction = direction;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * 排序方向
     *
     * @author LiuHuiYu
     */
    public enum Direction {
        /**
         * 顺序
         */
        ASC,
        /**
         * 倒序
         */
        DESC;

        Direction() {
        }

        public boolean isAscending() {
            return this.equals(ASC);
        }

        public boolean isDescending() {
            return this.equals(DESC);
        }

        public static Direction fromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            }
            catch (Exception var2) {
                return ASC;
            }
        }
    }
}
