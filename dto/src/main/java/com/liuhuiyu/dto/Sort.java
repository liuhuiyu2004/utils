package com.liuhuiyu.dto;

import java.util.Locale;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-02 7:42
 */
public class Sort {
    private int index;
    private String name;
    private Direction direction;

    public Sort() {
    }

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
     * @author LiuHuiYu
     * Created DateTime 2022-05-02 7:45
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
                throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), var2);
            }
        }
    }
}
