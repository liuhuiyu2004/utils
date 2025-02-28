package com.liuhuiyu.jpa.util;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-12-31 13:52
 */
class SqlUtilTest extends AbstractTest {
    public static final String SQL = "SELECT col1 AS a, col3 AS c, col2 AS b,col4 d,(select col5_1 f from table2) e FROM table T WHERE col1 = 10 AND col2 = 20 AND col3 = 30";

}