package com.wxw.common.encrypt.aes.demo;

import cn.hutool.core.codec.Base64;
import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.google.common.hash.Hashing;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/22
 * @description：
 * @version: 1.0.0
 */
public class Demo01_AesUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo01_AesUtils.class);
    private final static String CHARSET = "utf-8";

    // 加密后 = w0xFzL8zZVcpZq_KHzTmISwibcKpzwh6FseTq2KP8Xg
    public static void main(String[] args) {
        String content = "weixiaowei@qoogle.com";
        String aesKey = "0123456789ABCDEF";
        String encryptBody = encryptBase64(aesKey,content);
        // 加密后 = w0xFzL8zZVcpZq_KHzTmISwibcKpzwh6FseTq2KP8Xg
        System.out.println("加密后 = " + encryptBody);

        String decryptBase64 = decryptBase64(aesKey, encryptBody);
        System.out.println("解密后 = " + decryptBase64);
    }


    /**
     *入参：加密的body，请求头Head中的四个字段
     *返回：sign签名
     */
    private String getSign(String encryptReqBody, String partnerNo,String requestId,String version,String timestamp) {
        String signKey = "123456";
        List<String> values = new ArrayList<>();
        values.add(partnerNo);
        values.add(requestId);
        values.add(version);
        values.add(timestamp);
        values.add(signKey);
        return sign(values, encryptReqBody);
    }

    /**
     * 验签和解密
     *入参：加密的报文body，请求头Head中字段
     * 先验签，后解密报文体
     * 返回明文报文体
     */
    private String checkSignAndDecrypt(String encryptBody,String sign, String partnerNo,String requestId,String version,String timestamp) {
        String aesKey = "0123456789ABCDEF";
        String sign_new = getSign(encryptBody, partnerNo,requestId,version,timestamp);
        if (sign.equals(sign_new)) {
            //验签通过，对加密的body解密
            return decryptBase64(aesKey, encryptBody);
        }
        return null;
    }

    //加密算法
    public static String encryptBase64(String seed, String content) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes(CHARSET));
            byte[] result = encrypt(rawKey, content.getBytes(CHARSET));
            return Base64.encodeUrlSafe(result);
           // return Base64.encodeBase64URLSafeString(result);
        } catch (Exception e) {
            LOGGER.error("AES encrypt base64 error", e);
            throw new RuntimeException(e);
        }
    }
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        return encrypt(raw, null, clear);
    }
    private static byte[] encrypt(byte[] raw, byte[] iv, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher;
        if (iv != null) {
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
        } else {
            cipher = Cipher.getInstance("AES"); // AES/ECB/PKCS5Padding
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        }
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    //解密算法
    public static String decryptBase64(String aesKey, String encrypted) {
        try {
            byte[] rawKey = getRawKey(aesKey.getBytes(CHARSET));
            byte[] enc = Base64.decode(encrypted);
            byte[] result = decrypt(rawKey, enc);
            return new String(result, CHARSET);
        } catch (Exception e) {
            LOGGER.error("AES decrypt base64 error", e);
            throw new RuntimeException(e);
        }
    }
    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        return decrypt(raw, null, encrypted);
    }
    private static byte[] decrypt(byte[] raw, byte[] iv, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher;
        if (iv != null) {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
        } else {
            cipher = Cipher.getInstance("AES"); // AES/ECB/PKCS5Padding
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        }
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    //签名
    public String sign(List<String> values, String reqEncryptBody) {
        if (values == null) {
            throw new NullPointerException("签名参数为空");
        }
        values.removeAll(Collections.singleton(null));
        values.add(reqEncryptBody);
        Collections.sort(values);
        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }
        return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString();
    }
}


