package com.wxw.test.dd220109;

import com.wxw.common.utils.ApiEncryptUtil;
import sun.misc.BASE64Decoder;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/9
 * @description：
 * @version: 1.0.0
 */
public class TestDecryptAndVerify {

    public static void main(String[] args) throws Exception {

        // 测试私钥解密
        testDecrypt();
    }

    // 测试私钥解密
    private static void testDecrypt() throws Exception {
        String enString_from_go="jIYRVYv3kDiSXN6fWMuZdJj/ljLUv51TtNbbZi1m8h2jc/UzYxVnuYUp66kd8dEeROQ0gAqwXdovBFauzoDfPUtCBWZ0OJZrnyOC1jlE0/FRTIq+goA7B97k2WdYueEtkydNsahNzsABO+mA/hZE+P+oiZojoZISpAVyWyVD7XA=";
        // 加密后的字符串
        String privateKeyStr =ApiEncryptUtil.getKey(ApiEncryptUtil.PRI_KEY);
        byte[] enBytes=(new BASE64Decoder()).decodeBuffer(enString_from_go);
        String decryptData = ApiEncryptUtil.decrypt(
                ApiEncryptUtil.createPrivateKey(privateKeyStr), enBytes);
        System.out.println("解密后：" + decryptData);
    }
}
