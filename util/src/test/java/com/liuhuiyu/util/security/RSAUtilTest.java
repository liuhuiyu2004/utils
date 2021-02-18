package com.liuhuiyu.util.security;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-02-18 9:22
 */
@Log4j2
public class RSAUtilTest {

    @Test
    public void createKeys() {
        int keySize = 512;
        Map<String, String> map = RsaUtil.createKeys(keySize);
        log.info(map);
    }

    @Test
    public void getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        int keySize = 512;
        Map<String, String> map = RsaUtil.createKeys(keySize);
        RSAPublicKey key = RsaUtil.getPublicKey(map.get(RsaUtil.MAP_KEY_PUBLIC));
        log.info(key);
    }

    @Test
    public void getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        int keySize = 512;
        Map<String, String> map = RsaUtil.createKeys(keySize);
        RSAPrivateKey key = RsaUtil.getPrivateKey(map.get(RsaUtil.MAP_KEY_PRIVATE));
        log.info(key);
    }

    @Test
    public void publicEncrypt() throws InvalidKeySpecException, NoSuchAlgorithmException {
        int keySize = 512;
        String info = "abcd";
        Map<String, String> map = RsaUtil.createKeys(keySize);
        String encryptInfo = RsaUtil.publicEncrypt(info, RsaUtil.getPublicKey(map.get(RsaUtil.MAP_KEY_PUBLIC)));
        log.info(encryptInfo);
    }

    @Test
    public void privateDecrypt() throws InvalidKeySpecException, NoSuchAlgorithmException {
        int keySize = 512;
        String info = "abcd";
        Map<String, String> map = RsaUtil.createKeys(keySize);
        String encryptInfo = RsaUtil.publicEncrypt(info, RsaUtil.getPublicKey(map.get(RsaUtil.MAP_KEY_PUBLIC)));
        log.info(encryptInfo);
        String decryptInfo=RsaUtil.privateDecrypt(encryptInfo,RsaUtil.getPrivateKey(map.get(RsaUtil.MAP_KEY_PRIVATE)));
        log.info(decryptInfo);
    }

    @Test
    public void privateEncrypt() throws InvalidKeySpecException, NoSuchAlgorithmException {
        int keySize = 512;
        String info = "abcd";
        Map<String, String> map = RsaUtil.createKeys(keySize);
        String encryptInfo = RsaUtil.privateEncrypt(info, RsaUtil.getPrivateKey(map.get(RsaUtil.MAP_KEY_PRIVATE)));
        log.info(encryptInfo);
    }

    @Test
    public void publicDecrypt() throws InvalidKeySpecException, NoSuchAlgorithmException {
        int keySize = 512;
        String info = "abcd";
        Map<String, String> map = RsaUtil.createKeys(keySize);
        String encryptInfo = RsaUtil.privateEncrypt(info, RsaUtil.getPrivateKey(map.get(RsaUtil.MAP_KEY_PRIVATE)));
        log.info(encryptInfo);
        String decryptInfo=RsaUtil.publicDecrypt(encryptInfo,RsaUtil.getPublicKey(map.get(RsaUtil.MAP_KEY_PUBLIC)));
        log.info(decryptInfo);
    }
}