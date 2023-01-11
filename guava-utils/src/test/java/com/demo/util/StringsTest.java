package com.demo.util;

import com.google.common.base.Strings;
import org.junit.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-09-06 8:07
 */
public class StringsTest extends TestBase {
//    public static Logger LOG;// = LoggerFactory.getLogger(StringsTest.class);

//    @BeforeClass
//    public static void setLogger() throws MalformedURLException {
//        System.setProperty("log4j.configurationFile", "log4j2.xml");
//        LOG = LoggerFactory.getLogger(StringsTest.class);
//    }

    @Test
    public void commonPrefix() {
        String a = "aaabbbccc";
        String b = "abc";
        final String s = Strings.commonPrefix(a, b);
        LOG.info("公共前缀{};{}->{}", a, b, s);
    }

    @Test
    public void commonSuffix() {
        String a = "aaabbbccc";
        String b = "abc";
        final String s = Strings.commonSuffix(a, b);
        LOG.info("公共后缀{};{}->{}", a, b, s);
    }

    @Override
    protected String getConfigFileName() {
        return "log4j2.xml";
    }
}
