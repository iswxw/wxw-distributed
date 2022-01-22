package com.wxw.common.encrypt.aes.demo;

import cn.hutool.core.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/22
 * @description：
 * @version: 1.0.0
 */
public class Demo02_AesUtils_01_good {

    public static void main(String[] args) {
        String content = "weixiaowei@qoogle.com";
        String aesKey = "0123456789ABCDEF";

        /**
         * 1. 使用getByteKey01 测试 已经支持 go
         */
        String encryptBody = encrypt01("weixiaowei@qoogle.com",aesKey);

        // 加密后 = xBtQod-SPFDn0WVgbxa1lAwoUqffgf5nB_O4e9RO3PY
        System.out.println("加密后 = " + encryptBody);

        String decryptBase64 = decrypt01("xBtQod-SPFDn0WVgbxa1lAwoUqffgf5nB_O4e9RO3PY",aesKey);
        System.out.println("解密后 = " + decryptBase64);

        /**
         * 2. 使用 getRawKey02 测试
         */
        String encryptBody2 = encrypt02("weixiaowei@qoogle.com",aesKey);

        // 加密后 = w0xFzL8zZVcpZq_KHzTmISwibcKpzwh6FseTq2KP8Xg
        System.out.println("加密后 = " + encryptBody2);
        String decryptBase642 = decrypt02(encryptBody2,aesKey);
        System.out.println("解密后 = " + decryptBase642);

        /**
         * 3. 使用 getRawKey02 测试,添加 向量初始化
         */
        String encryptBody3 = encrypt03("weixiaowei@qoogle.com", aesKey);

        // 加密后 = ynyb9Qr6Rrjq8ppqZr-RKyuVnxgVzemcJmx3buk6ors
        System.out.println("加密后 = " + encryptBody3);
        String decryptBase643 = decrypt03(encryptBody3, aesKey);
        System.out.println("解密后 = " + decryptBase643);
    }

    /**
     * 方式一
     * @param origData
     * @param key
     * @return
     */

    public static String encrypt01(String origData,String key) {
        try {
            byte[] byteBuf = getByteKey01(key);
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
    public static String decrypt01(String crypted,String key) {

        byte[] decode = Base64.decode(crypted);
        byte[] byteBuf = getByteKey01(key);
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
    /**
     * 取前16位
     *  - 加密后 = xBtQod-SPFDn0WVgbxa1lAwoUqffgf5nB_O4e9RO3PY
     *  - 解密后 = weixiaowei@qoogle.com
     * @param key
     * @return
     */
    public static byte[] getByteKey01(String key) {
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

    /**
     * 方式二
     * @param origData
     * @param key
     * @return
     */

    public static String encrypt02(String origData,String key) {
        try {
            byte[] byteBuf = getRawKey02(key);
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(byteBuf, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] doFinal = cipher.doFinal(origData.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeUrlSafe(doFinal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String decrypt02(String crypted,String key) {
        byte[] decode = Base64.decode(crypted);
        byte[] byteBuf = getRawKey02(key);
        try {
            SecretKey keySpec = new SecretKeySpec(byteBuf, "AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] doFinal = cipher.doFinal(decode);
            return new String(doFinal,StandardCharsets.UTF_8);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // 生成Key

    /**
     * 生成Key
     *   - 加密后 = w0xFzL8zZVcpZq_KHzTmISwibcKpzwh6FseTq2KP8Xg
     *   - 解密后 = null
     * @param seed
     * @return
     */
    private static byte[] getRawKey02(String seed) {
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed.getBytes(StandardCharsets.UTF_8));
            // 3. 生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, secureRandom); // 192 and 256 bits may not be available
            // 4. 产生原始对称密钥
            SecretKey skey = keygen.generateKey();
            // 5. 获得原始对称密钥的字节数组
            return skey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * 方式三
     * @param origData
     * @param key
     * @return
     */
    public static String encrypt03(String origData,String key) {
        try {
            byte[] byteBuf = getRawKey02(key);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(byteBuf, "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec,ivSpec);
            byte[] doFinal = cipher.doFinal(origData.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeUrlSafe(doFinal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String decrypt03(String crypted,String key) {
        byte[] decode = Base64.decode(crypted);
        byte[] byteBuf = getRawKey02(key);
        try {
            SecretKey keySpec = new SecretKeySpec(byteBuf, "AES");
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec,ivSpec);
            byte[] doFinal = cipher.doFinal(decode);
            return new String(doFinal,StandardCharsets.UTF_8);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
