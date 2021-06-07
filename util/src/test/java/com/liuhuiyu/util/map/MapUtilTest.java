package com.liuhuiyu.util.map;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-06-07 9:02
 */
@Log4j2
public class MapUtilTest {
    @Test
    public void test() {
        String json = "{a:1,b:1.5,c:[{j:1,k:1.6},{j:2,k:1.7}],d:8088.0,16}";
        Map<String, Object> map = MapUtil.mapOfJsonString(json);
        log.info(map);
    }

}