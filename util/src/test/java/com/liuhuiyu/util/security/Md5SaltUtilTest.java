package com.liuhuiyu.util.security;


import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-11-15 14:59
 */
public class Md5SaltUtilTest {

    @Test
    public void test01() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String password = "大家好";
        final String encryptedPwd = Md5SaltUtil.getEncryptedPwd(password);
        //log.info("原始密码：{}", password);
        //log.info("加密密码：{}", encryptedPwd);
        final boolean b = Md5SaltUtil.validPassword(password, encryptedPwd);
        //log.info("密码校验：{}", b);
    }
}