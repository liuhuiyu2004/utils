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
        String sql="select count(1)\n" +
                "from (SELECT t.ID,c.yzbh,t.RY_ZJHM,t.RY_XM,c.bmmc,t.RL_XSD,t.RL_ZPSJ,t.DZ_NAME,t.RL_IMG,t.RL_ZP_IMG_D,t.RL_ZP_IMG_X,\n" +
                "             row_number() over (partition by t.RYID order by RL_ZPSJ desc) rw\n" +
                "      FROM RL_YX_RLZP_LOG t\n" +
                "               left join RL_RY_KLOG c on t.ryk_id = c.id\n" +
                "      where (t.rl_zpsj between to_date('2021-09-07 00:00:00', 'yyyy-MM-dd HH24:mi:ss') and to_date('2021-09-08 23:59:59', 'yyyy-MM-dd HH24:mi:ss'))\n" +
                "        and (t.hk_8700_sjlx = '131668')\n" +
                "        and (t.RY_ZJHM = :identificationNumber)) t\n" +
                "where (t.rw = 1)";
        String regex = "((=[\\s+]|=):(\\w+))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sql);
//        paramsMap.clear();
        int idx = 1;
        while (m.find()) {
            //参数名称可能有重复，使用序号来做Key
            paramsMap.put(idx++, m.group(m.groupCount()));
        }
        log.info(sql);
        sql = sql.replaceAll(regex, "=?");
        log.info(sql);
    }
}