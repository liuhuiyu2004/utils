package com.liuhuiyu.jpa.oracle.util;


import com.liuhuiyu.util.ObjectToFieldValue;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.sql.Clob;
import java.sql.SQLException;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-11-07 14:35
 */
public class OracleObjectToFieldValue implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        customConversionToString();
    }

    /**
     * 自定义转换
     */
    public void customConversionToString() {
        ObjectToFieldValue.setCustomConversionToString((v) -> {
            if (v instanceof Clob) {
                Clob clob = (Clob) v;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    final long length = clob.length();
                    int len = 1024;
                    for (long pos = 1; pos <= length; pos += len) {
                        len = (length - (pos - 1)) >= len ? len : (int) (length - (pos - 1));
                        if (len > 0) {
                            stringBuilder.append(clob.getSubString(pos, len));
                        }
                        else {
                            break;
                        }
                    }
                }
                catch (SQLException ignored) {

                }
                return stringBuilder.toString();
            }
            else {
                return v.toString();
            }
        });
    }

}
