package com.wxw.common.encrypt.aes.go;

import cn.hutool.core.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/24
 * @description：
 * @version: 1.0.0
 */
public class Demo02_go_ecb_keysha1random_base64 {

    // w0xFzL8zZVcpZq_KHzTmISwibcKpzwh6FseTq2KP8Xg
    public static void main(String[] args) {

        String content = "weixiaowei@qoogle.com";
        String aesKey = "0123456789ABCDEF";

        String encryptBody2 = encrypt02(content,aesKey);
        // 加密后 = w0xFzL8zZVcpZq_KHzTmISwibcKpzwh6FseTq2KP8Xg
        System.out.println("加密后 = " + encryptBody2);
        String decryptBase642 = decrypt02(encryptBody2,aesKey);
        System.out.println("解密后 = " + decryptBase642);
    }

    /**
     * 方式二 EBC 模式 go demo02_java_ecb_base64_sha1.go
     * @param origData
     * @param key
     * @return
     */

    public static String encrypt02(String origData,String key) {
        try {
            byte[] byteBuf = getRawKey02(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(byteBuf, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec); // EBC
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
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] doFinal = cipher.doFinal(decode);
            return new String(doFinal,StandardCharsets.UTF_8);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
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

}
