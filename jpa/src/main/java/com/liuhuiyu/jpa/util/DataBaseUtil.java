package com.liuhuiyu.jpa.util;

import java.io.BufferedReader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-08-11 9:59
 */
public class DataBaseUtil {
    public static String clobToString(Clob clob) throws SQLException {
        // 得到流
        java.io.Reader is = clob.getCharacterStream();
        BufferedReader br = new BufferedReader(is);
        StringBuilder sb = new StringBuilder();
        try {
            String s = br.readLine();
            // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成String
            while (s != null) {
                sb.append(s);
                s = br.readLine();
            }
        }
        catch (Exception e) {
            throw new DataBaseException(e);
        }
        return sb.toString();
    }
}
