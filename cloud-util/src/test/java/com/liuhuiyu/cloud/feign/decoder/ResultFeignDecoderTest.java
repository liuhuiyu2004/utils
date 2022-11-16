package com.liuhuiyu.cloud.feign.decoder;


import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-11-16 19:49
 */
class ResultFeignDecoderTest extends TestBase {
    @Test
    public void t1() {
        ResultFeignDecoder resultFeignDecoder = new ResultFeignDecoder();
        final String name = resultFeignDecoder.getClass().getName();
        LOG.info("{}", name);
    }
}