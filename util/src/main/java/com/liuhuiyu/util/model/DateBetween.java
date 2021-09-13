package com.liuhuiyu.util.model;

import com.liuhuiyu.util.date.LocalDateUtil;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 时间范围结构
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-08 9:44
 */
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
}
