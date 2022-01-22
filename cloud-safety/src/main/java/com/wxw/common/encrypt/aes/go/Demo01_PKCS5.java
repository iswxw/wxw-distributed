package com.wxw.common.encrypt.aes.go;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/22
 * @description：和 GO 语言互通
 * @version: 1.0.0
 */
public class Demo01_PKCS5 {

    private IvParameterSpec ivSpec;
    private SecretKeySpec keySpec;

    public Demo01_PKCS5(String key) {
        try {
            byte[] keyBytes = key.getBytes();
            byte[] buf = new byte[16];
            for (int i = 0; i < keyBytes.length && i < buf.length; i++) {
                buf[i] = keyBytes[i];
            }
            this.keySpec = new SecretKeySpec(buf, "AES");
            this.ivSpec = new IvParameterSpec(keyBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // 构造方法实现
        Demo01_PKCS5 aes = new Demo01_PKCS5("0123456789ABCDEF");
        String data = "weixiaowei@qoogle.com";
        byte[] crypted = aes.encrypt(data.getBytes());

        // 加密后：xBtQod+SPFDn0WVgbxa1lAwoUqffgf5nB/O4e9RO3PY=
        System.out.println("加密后：" + base64Encode(crypted));
        System.out.println("解密后" + new String(aes.decrypt(crypted))); //weixiaowei@qoogle.com
    }

    public byte[] encrypt(byte[] origData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, this.keySpec, this.ivSpec);
            return cipher.doFinal(origData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] decrypt(byte[] crypted) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, this.keySpec, this.ivSpec);
            return cipher.doFinal(crypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String base64Encode(byte[] data) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
