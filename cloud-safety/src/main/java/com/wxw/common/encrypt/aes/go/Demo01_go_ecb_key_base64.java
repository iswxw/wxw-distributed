package com.wxw.common.encrypt.aes.go;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/24
 * @description：
 * @version: 1.0.0
 */
public class Demo01_go_ecb_key_base64 {

    private static final String CHARACTER_CODING = "UTF-8";

    // Wqm8+LnVkjfurtZT5ntvJ23fwA4H7dd6frJegphx2v0=
    public static void main(String[] args) {
        String content = "weixiaowei@qoogle.com";
        String aesKey = "0123456789ABCDEF";

        String encodingByAES = encodingByAES(content, aesKey);
        System.out.println("加密后 = " + encodingByAES);

        String decodingByAES = decodingByAES(encodingByAES, aesKey);
        System.out.println("解密后 = " + decodingByAES);

    }

    public static String encodingByAES(String message, String pwd) {
        try{
            byte[] raw = pwd.getBytes(CHARACTER_CODING);
            byte[] encrypt = encrypt(message.getBytes(CHARACTER_CODING), raw);
            return Base64.getEncoder().encodeToString(encrypt);
//			return Base64.encodeBase64String(encrypt);
        } catch (Exception e) {
//			logger.error("加密异常");
//			logger.error(CrmCommonUtils.getTrace(e));
        }
        return "";
    }

    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        Key k = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,k);
        return cipher.doFinal(data);
    }

    // aes解密
    public static String decodingByAES(String message, String pwd) {
        try{
//			byte[] encrypt =Base64.decodeBase64(message);
            byte[] encrypt =Base64.getDecoder().decode(message);
            byte[] raw = pwd.getBytes(CHARACTER_CODING);
            byte[] original = decrypt(encrypt, raw);
            return new String(original,CHARACTER_CODING);
        } catch (Exception e) {
//			logger.error("解密异常");
//			logger.error(CrmCommonUtils.getTrace(e));
        }
        return "";
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        Key k = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

}
