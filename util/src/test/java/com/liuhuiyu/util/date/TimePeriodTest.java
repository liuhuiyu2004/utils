package com.liuhuiyu.util.date;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-28 16:14
 */
class TimePeriodTest extends TestBase {

    @Test
    public void testNull() {
        TimePeriod timePeriod = new TimePeriod("", "");
        LOG.info("{}:{}-{}", timePeriod.isValidTime(), timePeriod.getBeginTime(), timePeriod.getEndTime());
    }

    @Test
    public void testNull2() {
        TimePeriod timePeriod = new TimePeriod("", "2012-03-07");
        LOG.info("{}-{}", timePeriod.getBeginTime(), timePeriod.getEndTime());
    }

    @Test
    public void testTime() {
        TimePeriod timePeriod = new TimePeriod("2022-11-30", "2022-11-30");
        LOG.info("{} - {}", timePeriod.getBeginTime(), timePeriod.getEndTime());
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        LOG.info("{} in {}", nowTime, timePeriod.isInTimePeriod(nowTime));
    }

    @Test
    public void testTime2() {
        TimePeriod timePeriod = new TimePeriod(null, Timestamp.valueOf(LocalDateTime.now()));
        LOG.info("{}-{}", timePeriod.getBeginTime(), timePeriod.getEndTime());
    }

    @Test
    public void testTime3() {
        TimePeriod timePeriod = new TimePeriod(Timestamp.valueOf(LocalDateTime.now().plusHours(1)), Timestamp.valueOf(LocalDateTime.now()));
        LOG.info("{}-{}", timePeriod.getBeginTime(), timePeriod.getEndTime());
    }
}