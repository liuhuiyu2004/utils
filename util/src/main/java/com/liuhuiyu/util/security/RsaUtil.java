package com.liuhuiyu.util.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-02-18 9:00
 */
public class RsaUtil {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    public static final String MAP_KEY_PUBLIC = "publicKey";
    public static final String MAP_KEY_PRIVATE = "privateKey";

    public static Map<String, String> createKeys(int keySize) {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<>(2);
        keyPairMap.put(MAP_KEY_PUBLIC, publicKeyStr);
        keyPairMap.put(MAP_KEY_PRIVATE, privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws NoSuchAlgorithmException 字符串错误
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws NoSuchAlgorithmException 字符串错误
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 公钥加密
     *
     * @param data      要加密数据
     * @param publicKey 公钥
     * @return 加密后的数据
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        return encrypt(data, publicKey);
    }

    private static String encrypt(String data, Key key) {
        try {
            RSAKey rsaKey = (RSAKey) key;
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), rsaKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data       要解密数据
     * @param privateKey 私钥
     * @return 解密后的数据
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        return decrypt(data, privateKey);
    }

    /**
     * 私钥加密
     *
     * @param data       要加密的数据
     * @param privateKey 私钥
     * @return 加密后的数据
     */
    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        return encrypt(data, privateKey);
    }

    /**
     * 公钥解密
     *
     * @param data      要解密的数据
     * @param publicKey 公钥
     * @return 解密后的数据
     */
    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        return decrypt(data, publicKey);
    }

    private static String decrypt(String data, Key key) {
        try {
            RSAKey rsaKey = (RSAKey) key;
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), rsaKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opMode, byte[] dataBytes, int keySize) throws IOException {
        int maxBlock;
        if (opMode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        }
        else {
            maxBlock = keySize / 8 - 11;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] buff;
            int i = 0;
            try {
                while (dataBytes.length > offSet) {
                    if (dataBytes.length - offSet > maxBlock) {
                        buff = cipher.doFinal(dataBytes, offSet, maxBlock);
                    }
                    else {
                        buff = cipher.doFinal(dataBytes, offSet, dataBytes.length - offSet);
                    }
                    out.write(buff, 0, buff.length);
                    i++;
                    offSet = i * maxBlock;
                }
            } catch (Exception e) {
                throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
            }
            return out.toByteArray();
        }
    }

}
