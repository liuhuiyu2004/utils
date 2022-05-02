package com.liuhuiyu.jpa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class NamedParameterStatementTest {
    private static final Logger LOG= LogManager.getLogger(NamedParameterStatementTest.class);
    @Test
    public void s() {
        Map<Integer, String> paramsMap = new HashMap<>();
        String sql = "select count(1)\n" +
                "from (SELECT t.ID,c.yzbh,t.RY_ZJHM,t.RY_XM,c.bmmc,t.RL_XSD,t.RL_ZPSJ,t.DZ_NAME,t.RL_IMG,t.RL_ZP_IMG_D,t.RL_ZP_IMG_X,\n" +
                "             row_number() over (partition by t.RYID order by RL_ZPSJ desc) rw\n" +
                "      FROM RL_YX_RLZP_LOG t\n" +
                "               left join RL_RY_KLOG c on t.ryk_id = c.id\n" +
                "      where (t.rl_zpsj between to_date('2021-09-07 00:00:00', 'yyyy-MM-dd HH24:mi:ss') and to_date('2021-09-08 23:59:59', 'yyyy-MM-dd HH24:mi:ss'))\n" +
                "        and (t.hk_8700_sjlx = '131668')\n" +
                "        and (t.RY_ZJHM LIKE :identificationNumber)) t\n" +
                "where (t.rw = 1)";
        String regex = "((=[\\s+]|=|(LIKE)):(\\w+))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sql);
//        paramsMap.clear();
        int idx = 1;
        while (m.find()) {
            //参数名称可能有重复，使用序号来做Key
            paramsMap.put(idx++, m.group(m.groupCount()));
            sql = sql.replace(m.group(), "?");
        }
        LOG.info(sql);
        LOG.info("{}",paramsMap.size());
//        sql = sql.replaceAll(regex, "=?");
//        log.info(sql);
    }

    @Test
    public void testParseSql2(){
        String sql="select count(1)\n" +
                "from (SELECT t.ID,c.yzbh,t.RY_ZJHM,t.RY_XM,c.bmmc,t.RL_XSD,t.RL_ZPSJ,t.DZ_NAME,t.RL_IMG,t.RL_ZP_IMG_D,t.RL_ZP_IMG_X,\n" +
                "             row_number() over (partition by t.RYID order by RL_ZPSJ desc) rw\n" +
                "      FROM RL_YX_RLZP_LOG t\n" +
                "               left join RL_RY_KLOG c on t.ryk_id = c.id\n" +
                "      where (t.rl_zpsj between to_date('2021-09-07 00:00:00', 'yyyy-MM-dd HH24:mi:ss') and to_date('2021-09-08 23:59:59', 'yyyy-MM-dd HH24:mi:ss'))\n" +
                "        and (t.hk_8700_sjlx = ':ffff')\n" +
                "        and (t.RY_ZJHM LIKE :identificationNumber)) t\n" +
                "where (t.rw = 1)";
        this.paramsMap=new HashMap<>();
        String outSql=parseSql2(sql);
        LOG.info("sql={}",sql);
        LOG.info("outSql={}",outSql);
        LOG.info("paramsMap={}",paramsMap);
    }
    private Map<Integer, String> paramsMap;

    private String parseSql2(String query) {
        int length = query.length();
        StringBuilder parsedQuery = new StringBuilder(length);
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        int index = 1;

        for (int i = 0; i < length; i++) {
            char c = query.charAt(i);
            if (inSingleQuote) {
                if (c == '\'') {
                    inSingleQuote = false;
                }
            }
            else if (inDoubleQuote) {
                if (c == '"') {
                    inDoubleQuote = false;
                }
            }
            else {
                if (c == '\'') {
                    inSingleQuote = true;
                }
                else if (c == '"') {
                    inDoubleQuote = true;
                }
                else if (c == ':' &&
                        i + 1 < length &&
                        Character.isJavaIdentifierStart(query.charAt(i + 1))) {
                    int j = i + 2;
                    while (j < length && Character.isJavaIdentifierPart(query.charAt(j))) {
                        j++;
                    }
                    String name = query.substring(i + 1, j);
                    c = '?'; // 用问号替换参数
                    i += name.length(); // 如果参数是跳过结尾
                    this.paramsMap.put(index++, name);
                }
            }
            parsedQuery.append(c);
        }

        return parsedQuery.toString();
    }
}