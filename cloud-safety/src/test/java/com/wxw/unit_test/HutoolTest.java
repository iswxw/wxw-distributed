package com.wxw.unit_test;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import org.junit.Test;

import java.security.KeyPair;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/8
 * @description： https://www.hutool.cn/docs/#/crypto/%E6%A6%82%E8%BF%B0
 * @version: 1.0.0
 */

public class HutoolTest {

    /**
     * 自助生成秘钥对
     */
    @Test
    public void TestGenKeyPair(){
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        System.out.println("private = " + pair.getPrivate());
        System.out.println("public = " + pair.getPublic());
    }

    /**
     * 签名和验证签名
     */
    @Test
    public void TestSign() {
        byte[] data = "我是一段测试字符串".getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        //签名
        byte[] signed = sign.sign(data);
        //验证签名
        boolean verify = sign.verify(data, signed);
        System.out.println(verify);
    }
}
