package com.liuhuiyu.json.util;

import com.liuhuiyu.json.util.deserializer.ByteArrayDeserializer;
import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Map;

/**
 * 功能<p>
 * Created on 2025/3/17 21:01
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
class MapUtilTest extends AbstractTest {
    @Test
    public void fromMap() {
        Map<String, Object> userMap = Map.of("name", "liuhuiyu",
                "age", 18,
                "sex", true,
                "birthday", new Timestamp(System.currentTimeMillis()),
                "money", new BigDecimal("123.456"),
                "imageByte", Base64.getEncoder().encodeToString("大家好".getBytes()));
        final User user = MapUtil.fromMap(userMap, User.class);
        super.printObjectJson(user);
    }

    @Test
    public void fromMap2() {
        MapUtil.GsonAdapter[] typeAdapter = new MapUtil.GsonAdapter[]{
                new MapUtil.GsonAdapter(byte[].class, new ByteArrayDeserializer())
        };

        Map<String, Object> userMap = Map.of("name", "liuhuiyu",
                "age", 18,
                "sex", true,
                "birthday", new Timestamp(System.currentTimeMillis()),
                "money", new BigDecimal("123.456"),
                "imageByte", Base64.getEncoder().encodeToString("大家好".getBytes()));
        final User user = MapUtil.fromMap(userMap, User.class, typeAdapter);
        super.printObjectJson(user);
    }

    record User(String name,
                Integer age,
                Boolean sex,
                Timestamp birthday,
                BigDecimal money,
                byte[] imageByte) {
    }
}
