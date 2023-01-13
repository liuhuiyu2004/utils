package com.liuhuiyu.core.time;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-13 10:32
 */
class TimestampUtilTest extends TestBase {
    @Test
    public void test() {
        final boolean timestampString = TimestampUtil.isTimestampString("");
        LOG.info("{}", timestampString);
    }
}