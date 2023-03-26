package com.liuhuiyu.jpa.oracle.dao;

import com.liuhuiyu.dto.Sort;
import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-26 21:41
 */
class OracleOrderTest extends TestBase {
    @DisplayName("排序测试")
    @ParameterizedTest
    @MethodSource("argumentsStream")
    public void orderTest(String defOrder, Stream<Arguments> map, List<Sort> sortList) {
        OracleOrder oracleOrder = new OracleOrder(defOrder);
        map.forEach(a -> {
            oracleOrder.addOrder((String) a.get()[0], (String) a.get()[1]);
        });
        final String order = oracleOrder.getOrder(sortList);
        LOG.info(order);
    }

    static Stream<Arguments> argumentsStream() {
        return Stream.of(
                Arguments.of("", Stream.of(
                                Arguments.of("a1", "t.a1"),
                                Arguments.of("a2", "t.a2"),
                                Arguments.of("a3", "t.a3")
                        ), Arrays.asList(
                                new Sort(10, "a1", Sort.Direction.ASC),
                                new Sort(2, "a3", Sort.Direction.DESC),
                                new Sort(3, "a7", Sort.Direction.ASC)
                        )
                ),Arguments.of("", Stream.of(
                                Arguments.of("a1", "t.a1"),
                                Arguments.of("a2", "t.a2"),
                                Arguments.of("a3", "t.a3")
                        ), Arrays.asList(
                                new Sort(10, "a11", Sort.Direction.ASC),
                                new Sort(2, "a13", Sort.Direction.DESC),
                                new Sort(3, "a7", Sort.Direction.ASC)
                        )
                ),Arguments.of("t.a8 DESC", Stream.of(
                                Arguments.of("a1", "t.a1"),
                                Arguments.of("a2", "t.a2"),
                                Arguments.of("a3", "t.a3")
                        ), Arrays.asList(
                                new Sort(10, "a11", Sort.Direction.ASC),
                                new Sort(2, "a13", Sort.Direction.DESC),
                                new Sort(3, "a7", Sort.Direction.ASC)
                        )
                )
        );
    }

}