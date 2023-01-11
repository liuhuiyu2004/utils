package com.demo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-09-06 8:38
 */
public abstract class TestBase {
    protected static Logger LOG;
//    @BeforeClass
    @Before
    public  void setLogger() throws MalformedURLException {
        System.setProperty("log4j.configurationFile", getConfigFileName());
        LOG = LogManager.getLogger();
    }
    protected abstract String getConfigFileName();
}
