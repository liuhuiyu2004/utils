package com.liuhuiyu.jpa.dm.util;

import com.liuhuiyu.util.ObjectToFieldValue;
import dm.jdbc.driver.DmdbClob;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-11-07 14:35
 */
public class DmObjectToFieldValue implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        customConversionToString();
    }
    public void customConversionToString() {
        ObjectToFieldValue.setCustomConversionToString((v) -> {
            if (v instanceof DmdbClob) {
                DmdbClob clob = (DmdbClob) v;
                return clob.data;
            }
            else {
                return v.toString();
            }
        });
    }

}
