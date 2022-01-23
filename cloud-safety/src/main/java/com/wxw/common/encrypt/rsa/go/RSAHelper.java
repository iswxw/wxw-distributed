package com.wxw.common.encrypt.rsa.go;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @contract: 公众号：Java半颗糖
 * @desc:
 * @link: https://blog.csdn.net/igoqhb/article/details/19832259
 */
public class RSAHelper {
    /**
     *私有数据成员，主要是公钥和私钥
     *由程序自己产生，公钥需传递给通信另一方
     */
    private static String publicKey;
    private static String privateKey;

    // 产生公钥和私钥
    private static void genKeys() throws Exception{
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //密钥位数
        keyPairGen.initialize(1024);
        //密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        PublicKey pubKey =  keyPair.getPublic();
        // 私钥
        PrivateKey privKey = keyPair.getPrivate();
        publicKey= getKeyString(pubKey);
        privateKey= getKeyString(privKey);
    }

    /**
     * 得到公钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 得到密钥字符串（经过base64编码）
     * @return
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encode(keyBytes);
        return s;
    }
    /**
     * 利用Go语言产生的公钥加密
     * @param pubkey_from_go 从服务器（go语言实现）获取的公钥
     * @param plainText 需要加密的字符串
     */
    public static String encByGoPubKey(String pubkey_from_go,String plainText) throws Exception {
        //加解密类
        Cipher cipher = Cipher.getInstance("RSA"); //Cipher.getInstance("RSA/ECB/PKCS1Padding");
        byte[] plainTextBytes = plainText.getBytes();
        //用Go语言产生的公钥加密
        PublicKey pubkey_go = getPublicKey(pubkey_from_go);
        cipher.init(Cipher.ENCRYPT_MODE, pubkey_go);
        byte[] enBytes = cipher.doFinal(plainTextBytes);
        String encryptString = (new BASE64Encoder()).encode(enBytes);
        return encryptString;
    }
    /**
     *用私钥解密Go语言用java公钥产生的密文
     *
     */
    public static String decryptByJavaPrivKey(String privkey_from_java,String encryptedString) throws Exception{
        //加解密类
        Cipher cipher = Cipher.getInstance("RSA");//Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // G0语言通过java产生的公钥加密得到的加密后的字符串
        byte[] enBytes=(new BASE64Decoder()).decodeBuffer(encryptedString);
        //通过密钥字符串得到密钥
        PrivateKey privateKey = getPrivateKey(privkey_from_java);
        //解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[]deBytes = cipher.doFinal(enBytes);
        String decryptString=new String(deBytes);
        return decryptString;

    }

    public static void main(String[] args) throws Exception {
        genKeys();
        System.out.println("public key:\n" + publicKey);
        System.out.println("private key:\n" + privateKey);

        //java生成的公钥和私钥
        String pubKey_from_java="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDmb2bXhcfeiosnxs0bD17isalelyS2/0xKQdJU"+
                "VUyMdt+/5Inm/S5upDFrliMs3i9zj3PtJWO7yzRfiBnoDNlOfTqPNY6DI9FXnhDgjQMJhp1Zbhl7"+
                "d74E63CBVTU6Deocqfy/KCiPjQnpTzln89Mm7eE3WbvlmvX3mO7uD2/geQIDAQAB";
        String privKey_from_java="MIIBNwIBADANBgkqhkiG9w0BAQEFAASCASEwggEdAgEAAoGBAOZvZteFx96KiyfGz"+
                "RsPXuKxqV6XJLb/TEpB0lRVTIx237/kieb9Lm6kMWuWIyzeL3OPc+0lY7vLNF+IGe"+
                "gM2U59Oo81joMj0VeeEOCNAwmGnVluGXt3vgTrcIFVNToN6hyp/L8oKI+NCelPOWf"+
                "z0ybt4TdZu+Wa9feY7u4Pb+B5AgEAAoGBAL5mmBxGzwIDib2hF0JfrfA0ChU9X7nR"+
                "MrE8t9S08l4xrul4pbV1x1LmWmtiD8h4Ac9DXe858LFv0uOIqpdBXp9ZMyoqBC97L"+
                "wDboutzt6OcXQ3hMVTOszn9cFFIf6JXaLz8HgocqAHTLVM4LwmyZNbGAyX/vja9BX"+
                "jVtUQdVxt9AgEAAgEAAgEAAgEAAgEA";

        //Go生成的公钥和私钥
        String pubKey_from_go="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAywxbDd3ufuJQtDHjmxu5\n" +
                "16Muh6DOQKaXrU+OI9VVIRsnwfjTik4KDwiJz+YhlP6IaJMS6Ck7bCgAp9kjr4Uv\n" +
                "CLfZXKfVw9O+o0IBZl8mvBSBNerAgiQjGOJFoIz3Oa1OUCNNmSxTzuVeFB77zHm9\n" +
                "ZaZXKl1tzl1LAThBsZHmARN3eyf8SuyfzkUWuGvHu7bBsNPf2DAFRqBEiHKuljvM\n" +
                "TIFNe3BipaSDZjVOSsX24X8F6aZjtuQWl4Ha8My7Wo5OnqaoOXrADv8FXJor316C\n" +
                "+JfORgaT5f+9oBJ3mIG8urm6bWIPB99EhuHdEVk5fPxgkbwGReuEdCsROhpL3iRr\n" +
                "7QIDAQAB";

        String privKey_from_go="MIICXQIBAAKBgQDZsfv1qscqYdy4vY+P4e3cAtmvppXQcRvrF1cB4drkv0haU24Y"+
                "7m5qYtT52Kr539RdbKKdLAM6s20lWy7+5C0DgacdwYWd/7PeCELyEipZJL07Vro7"+
                "Ate8Bfjya+wltGK9+XNUIHiumUKULW4KDx21+1NLAUeJ6PeW+DAkmJWF6QIDAQAB"+
                "AoGBAJlNxenTQj6OfCl9FMR2jlMJjtMrtQT9InQEE7m3m7bLHeC+MCJOhmNVBjaM"+
                "ZpthDORdxIZ6oCuOf6Z2+Dl35lntGFh5J7S34UP2BWzF1IyyQfySCNexGNHKT1G1"+
                "XKQtHmtc2gWWthEg+S6ciIyw2IGrrP2Rke81vYHExPrexf0hAkEA9Izb0MiYsMCB"+
                "/jemLJB0Lb3Y/B8xjGjQFFBQT7bmwBVjvZWZVpnMnXi9sWGdgUpxsCuAIROXjZ40"+
                "IRZ2C9EouwJBAOPjPvV8Sgw4vaseOqlJvSq/C/pIFx6RVznDGlc8bRg7SgTPpjHG"+
                "4G+M3mVgpCX1a/EU1mB+fhiJ2LAZ/pTtY6sCQGaW9NwIWu3DRIVGCSMm0mYh/3X9"+
                "DAcwLSJoctiODQ1Fq9rreDE5QfpJnaJdJfsIJNtX1F+L3YceeBXtW0Ynz2MCQBI8"+
                "9KP274Is5FkWkUFNKnuKUK4WKOuEXEO+LpR+vIhs7k6WQ8nGDd4/mujoJBr5mkrw"+
                "DPwqA3N5TMNDQVGv8gMCQQCaKGJgWYgvo3/milFfImbp+m7/Y3vCptarldXrYQWO"+
                "AQjxwc71ZGBFDITYvdgJM1MTqc8xQek1FXn1vfpy2c6O";

        // 用Go语言产生的公钥加密
        String plainText = "Hello,I am  plaintext string!@sina.com";
        String encryptString=encByGoPubKey(publicKey,plainText);
        System.out.println("加密后的字符串：\n"+encryptString);


        // 解密G0语言通过java公钥加密得到的字符串
        String enString_from_go="jIYRVYv3kDiSXN6fWMuZdJj/ljLUv51TtNbbZi1m8h2jc/UzYxVnuYUp66kd8dEeROQ0gAqwXdovBFauzoDfPUtCBWZ0OJZrnyOC1jlE0/FRTIq+goA7B97k2WdYueEtkydNsahNzsABO+mA/hZE+P+oiZojoZISpAVyWyVD7XA=";
        String decryptString=decryptByJavaPrivKey(privKey_from_java,enString_from_go);
        System.out.println("解密后的字符串：\n"+decryptString);

    }


}
