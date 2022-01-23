package com.wxw.common.encrypt.aes.go;

import cn.hutool.core.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/22
 * @description：
 * @version: 1.0.0
 */
public class Demo01_PKCS5_16_iv {

    public static void main(String[] args) {
        String signKey = "123456";
        String aesKey = "0123456789ABCDEF";
        String encryptBody = encrypt("weixiaowei@qoogle.com",aesKey);

        // 加密后 = xBtQod-SPFDn0WVgbxa1lAwoUqffgf5nB_O4e9RO3PY
        System.out.println("加密后 = " + encryptBody);

        String decryptBase64 = decrypt("xBtQod-SPFDn0WVgbxa1lAwoUqffgf5nB_O4e9RO3PY",aesKey);
        System.out.println("解密后 = " + decryptBase64);
    }

    public static String encrypt(String origData,String key) {
        try {
            byte[] byteBuf = getByteBuf(key);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(byteBuf, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] doFinal = cipher.doFinal(origData.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeUrlSafe(doFinal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // 取前16位
    public static byte[] getByteBuf(String key) {
        byte[] keyBytes = key.getBytes();
        byte[] buf = new byte[16];
        try {
            for (int i = 0; i < keyBytes.length && i < buf.length; i++) {
                buf[i] = keyBytes[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf;
    }

    public static String decrypt(String crypted,String key) {

        byte[] decode = Base64.decode(crypted);
        byte[] byteBuf = getByteBuf(key);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(byteBuf, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] doFinal = cipher.doFinal(decode);
            return new String(doFinal,StandardCharsets.UTF_8);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
