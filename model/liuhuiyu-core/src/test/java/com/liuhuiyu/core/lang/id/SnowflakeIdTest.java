package com.liuhuiyu.core.lang.id;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-01-16 16:11
 */
class SnowflakeIdTest extends TestBase {
    @Test
    public void create() {
        SnowflakeId idWorker = new SnowflakeId(0, 0);
        for (int i = 0; i < 100; i++) {
            long id = idWorker.nextId();
            LOG.info("{};{}", Long.toBinaryString(id), id);
        }
    }
}