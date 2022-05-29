package com.liuhuiyu.util.date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-28 16:14
 */
class TimePeriodTest {
    private static final Logger LOG = LogManager.getLogger(TimePeriodTest.class);

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
        TimePeriod timePeriod = new TimePeriod("2012-05-06", "2012-03-07");
        LOG.info("{}-{}", timePeriod.getBeginTime(), timePeriod.getEndTime());
    }
}