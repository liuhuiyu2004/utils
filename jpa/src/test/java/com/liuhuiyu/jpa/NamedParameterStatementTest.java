package com.liuhuiyu.jpa;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-09-08 11:48
 */
@Log4j2
public class NamedParameterStatementTest {
    @Test
    public void s(){
        Map<Integer, String> paramsMap = new HashMap<>();
        String sql="to_date(':abc','yyyy-MM-dd HH24:mi:ss'))AND(b=:b)AND(c = :c)AND(d= :d)";
        String regex = "((=[\\s+]|=):(\\w+))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sql);
//        paramsMap.clear();
        int idx = 1;
        while (m.find()) {
            //参数名称可能有重复，使用序号来做Key
            paramsMap.put(idx++, m.group(2));
        }
        log.info(sql);
        sql = sql.replaceAll(regex, "=?");
        log.info(sql);
    }
}