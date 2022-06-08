package com.liuhuiyu.jpa.oracle.util;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间范围结构
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * @deprecated 不需要那么麻烦了，直接使用
 * Created DateTime 2021-09-08 9:44
 */
@Deprecated
public class DateBetween {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;

    public DateBetween(@NotNull LocalDateTime time1, @NotNull LocalDateTime time2) {
        this.startDateTime = time1.isBefore(time2) ? time1 : time2;
        this.endDateTime = time1.isAfter(time2) ? time1 : time2;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String oracleBetweenSql(String columnName) {
        return "(" + columnName + " between to_date('" +
                this.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "','yyyy-MM-dd hh24:mi:ss') and to_date('" +
                this.getEndDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "','yyyy-MM-dd hh24:mi:ss'))";
    }
}
