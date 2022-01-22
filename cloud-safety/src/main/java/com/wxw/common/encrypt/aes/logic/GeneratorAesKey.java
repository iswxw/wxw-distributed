package com.wxw.common.encrypt.aes.logic;

import cn.hutool.core.codec.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/22
 * @description：
 * @version: 1.0.0
 */
public class GeneratorAesKey {

    public static void main(String[] args) throws Exception {
        String aesKey = "0123456789ABCDEF";
        System.out.println("aesKey = " + aesKey.length()); // 16
        byte[] generatorAesKey = generatorAesKey(aesKey);
        System.out.println("generatorAesKey = " + Base64.encodeUrlSafe(generatorAesKey));
    }

    private static byte[] generatorAesKey(String seed) throws Exception {
        //1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen=KeyGenerator.getInstance("AES");
        //2.根据ecnodeRules规则初始化密钥生成器
        //生成一个128位的随机源,根据传入的字节数组
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;
        secureRandom.setSeed(seed.getBytes());
        keygen.init(128, secureRandom);
        //3.产生原始对称密钥
        SecretKey original_key=keygen.generateKey();
        //4.获得原始对称密钥的字节数组
        return original_key.getEncoded();
    }
}
