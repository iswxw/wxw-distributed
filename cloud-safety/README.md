## 通信和安全



安全总是很重要的，各个语言对于通用的加密算法都会有实现。

公钥与私钥是用于加密与解密，而加签与验签是用来证明身份，以免被篡改的。



## 基础回顾

### 1. 加密的发展

#### 1.1 对称加密

对称密钥加密，又称私钥加密，即信息的发送方和接收方用一个密钥去加密和解密数据。它的最大优势是加/解密速度快，适合于对大数据量进行加密，但密钥管理困难。

采用单钥密码系统的加密方法，同一个密钥可以同时用作信息的加密和解密，这种加密方法称为对称加密，也称为单密钥加密。需要对加密和解密使用相同密钥的加密算法。由于其速度，对称性加密通常在消息发送方需要加密大量数据时使用。对称性加密也称为密钥加密。所谓对称，就是采用这种加密方法的双方使用方式用同样的密钥进行加密和解密。密钥实际上是一种算法，通信发送方使用这种算法加密数据，接收方再以同样的算法解密数据。因此对称式加密本身不是安全的。

对于普通的对称密码学，加密运算与解密运算使用同样的密钥。通常,使用的对称加密算法比较简便高效，密钥简短，破译极其困难，由于系统的保密性主要取决于密钥的安全性，所以，在公开的计算机网络上安全地传送和保管密钥是一个严峻的问题。正是由于对称密码学中双方都使用相同的密钥，因此无法实现数据签名和不可否认性等功能。

常用的对称加密有：

DES、 IDEA、 RC2、 RC4、 SKIPJACK、 RC5、 AES 算法等采用单钥密码系统的加密方法，同一个密钥可以同时用作信息的加密和解密，这种加密方法称为对称加密，也称为单密钥加密。

#### 1.2 非对称加密

1976 年，美国学者 Dime 和 Henman 为解决信息公开传送和密钥管理问题，提出一种新的密钥交换协议，允许在不安全的媒体上的通讯双方交换信息，安全地达成一致的密钥，这就是“公开密钥系统”。相对于“对称加密算法”这种方法也叫做“非对称加密算法”。与对称加密算法不同，非对称加密算法需要两个密钥：公开密钥（ publickey）和私有密钥（ privatekey）。公开密钥与私有密钥是一对，如果用公开密钥对数据进行加密，只有用对应的私有密钥才能解密；如果用私有密钥对数据进行加密，那么只有用对应的公开密钥才能解密。因为加密和解密使用的是两个不同的密钥，所以这种算法叫作非对称加密算法。

非对称加密算法实现机密信息交换的基本过程是：甲方生成一对密钥并将其中的一把作为公用密钥向其它方公开；得到该公用密钥的乙方使用该密钥对机密信息进行加密后再发送给甲方；甲方再用自己保存的另一把专用密钥对加密后的信息进行解密。甲方只能用其专用密钥解密由其公用密钥加密后的任何信息。非对称加密算法的保密性比较好，它消除了最终用户交换密钥的需要，但加密和解密花费时间长、速度慢，它不适合于对文件加密而只适用于对少量数据进行加密。经典的非对称加密算法如 RSA 算法等安全性都相当高.非对称加密的典型应用是数字签名。

采用双钥密码系统的加密方法，在一个过程中使用两个密钥，一个用于加密，另一个用于解密，这种加密方法称为非对称加密，也称为公钥加密，因为其中一个密钥是公开的（另一个则需要保密）。

#### 1.3 公钥

公钥是与私钥算法一起使用的密钥对的非秘密一半。公钥通常用于加密会话密钥、验证数字签名，或加密可以用相应的私钥解密的数据。公钥和私钥是通过一种算法得到的一个密钥对(即一个公钥和一个私钥)其中的一个向外界公开，称为公钥；另一个自己保留，称为私钥。通过这种算法得到的密钥对能保证在世界范围内是唯一的。使用这个密钥对的时候，如果用其中一个密钥加密一段数据，必须用另一个密钥解密。比如用公钥加密数据就必须用私钥解密，如果用私钥加密也必须用公钥解密，否则解密将不会成功。

1976 年。当时在美国斯坦福大学的迪菲（ Diffie）和赫尔曼(Hellman)两人提出了公开密钥密码的新思想（论文”New Direction in Cryptography”），不仅加密算法本身可以公开，甚至加密用的密钥也可以公开。但这并不意味着保密程度的降低。因为如果加密密钥和解密密钥不一样。而将解密密钥保密就可以。这就是著名的公钥密码体制。也称作非对称密码体制。不同于对称性的 密码学， 在于其加密钥匙只适用于单一用户。

#### 1.4 钥匙的两部分

一把私有的钥匙，仅有用户才拥有。一把公开的钥匙，可公开发行配送，只要有要求即取得。每支钥匙产生一个被使用来改变属性的功能。私有的钥匙产生一个私有改变属性的功能，而公开的钥匙 产生一个 公开改变属性的功能。这些功能是反向相关的，例如，如果一个功能是用来加密消息，另外一个功能则被用来解密消息。不论此改变属性功能的次序为何皆不重要。公开的钥匙系统的优势是两个用户能安全的沟通而不需交换秘密钥匙。例如，假设一个送信者需要传送一个信息给一个收信者，而信息的秘密性是必要的， 送信者以收信者的公开的钥匙来加密，而仅有收信者的私有的钥匙能够对此信息解密。公开的钥匙密码学是非常适合于提供认证，完整和不能否认的服务， 所有的这些服务既是我们所知的数字签名。

#### 1.5 总结

一个有效、可信的 SSL 数字证书包括一个公共密钥和一个私用密钥。公共密钥用于加密信息，私用密钥用于解译加密的信息。因此，浏览器指向一个安全域时，SSL 将同步确认服务器和客户端，并创建一种加密方式和一个唯一的会话密钥。

如果您的网站使用 SSL 证书，您的客户就知道他们的交易安全可靠，并且充分信赖您的网站。数安时代有15年的技术沉淀，技术应用核心行业50多，技术应用业务领域300多处，[SSL证书](https://www.trustauth.cn/)自适应128-256位加密，SHA256签名算法，秘钥长度高达2048-4096位，且一直致力于促进我国信息安全保护的进程。

相关资料

1. [公钥和私钥的解释](https://www.trustauth.cn/news/security-news/27011.html) 

### 2. 签名与验签

通过公私钥机制使用在 **客户接口加签的方式**  为自身应用配置 **公钥** 防止数据篡改，以此来保障商户应用和自身应用交互的安全性。

#### 2.1 名词解释

**公钥：** 即 **应用公钥**（public_key），用于加密信息。

**私钥：** 即 **应用私钥**（private_key），用于解密信息。

**签名：** 即生成签名方 将传送的消息用私钥加密的过程。

**验签：** 指验签方 使用公钥对消息进行验证的过程。

#### 2.2 加签和验签原理

商户在应用中使用自己的 **私钥** 对消息加签之后，消息和签名会传递给A国服务，A国服务则使用应用的 **公钥**/**公钥证书** 验证消息的真实性（来自于合法应用的真实消息）。

![公钥机制.png](https://cdn.nlark.com/yuque/0/2021/png/179989/1637654806878-c06d4953-8d83-4c1f-a3b9-6ae8ae108bde.png) 

#### 2.3 签名的作用

数字签名有两种功效：

- 一是能确定消息确实是由发送方签名并发送的，因为别人假冒不了发送方的签名。
- 二是数字签名能确定消息的完整性。

发送方用自己的私钥完成数字签名，然后再用接收方的公钥对报文进行加密，将数字签名和报文传送给接收方。接收方在拿到密文和数字签名后，先用自己的私钥对密文进行解密，得到明文，然后再用发送方提供的公钥进行验签，确保发送方身份的准确性，以及报文并没有被篡改过。



---

相关资料

1. [支付宝开放助手 签名与验签](https://opendocs.alipay.com/common/02mse2)   

### 3. 私钥和公钥生成工具

#### 3.1 `openssl` 



## 加密算法



## 应用实践

**应用场景** 

- 在与第三方交互时，可以使用 非对称加密+签名算法 避免数据被篡改。

### 1. 签名与验签实践

#### 1.1 RSA算法简介

##### 1.1.1 加密和解密

RSA加密是一种非对称加密，在公开密钥加密和电子商业中RSA被广泛使用。可以在不直接传递密钥的情况下，完成加解密操作。这能够确保信息的安全性，避免了直接传递密钥所造成的被破解的风险。是由一对密钥来进行加解密的过程，分别称为公钥和私钥。该加密算法的原理就是对一极大整数做因数分解的困难性来保证安全性。

##### 1.1.2 签名与验签

数字签名就是信息的来源添加一段无法被伪造的加密字符串，这段数字串作为对信息的来源真实性的一个有效证明。这个过程称为签名和验签。

#### 1.2 场景描述

- 消息发送方：甲方，持有公钥
- 消息接收方：乙方，持有私钥

[![https://gitee.com/wwxw/image/raw/master/20220109/qfrUG@aMU5oz.png](https://gitee.com/wwxw/image/raw/master/20220109/qfrUG@aMU5oz.png)](https://gitee.com/wwxw/image/raw/master/20220109/qfrUG@aMU5oz.png)   

##### 1.2.1 加密解密过程

- 乙方生成一对密钥即公钥和私钥，私钥不公开，乙方自己持有，公钥为公开，甲方持有。

- 乙方收到甲方加密的消息，使用私钥对消息进行解密，获取明文。

##### 1.2.2 签名与验签过程

- 乙方收到消息后，需要回复甲方，用私钥对回复消息签名，并将消息明文和消息签名回复甲方。

- 甲方收到消息后，使用公钥进行验签，如果验签结果是正确的，则证明消息是乙方回复的。 

#### 1.3 源码实现

```java
package com.wxw.common.utils;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/8
 * @description：OpenApi通信协议加解密工具类
 * @version: 1.0.0
 */
public class ApiEncryptUtil {

    // 私钥和公钥的文件位置
    public static final String PUB_KEY = "rsaKey/public.key";
    public static final String PRI_KEY = "rsaKey/private.key";


    public static void main(String[] args) throws Exception {

        // 密钥生成测试
        testCreateKey();

        // 密钥读取测试
        testReadKey();

        // 签名验签测试
        testSignVerify();
    }


    /**
     * 密钥生成测试
     *
     * @throws Exception
     */
    public static void testCreateKey() throws Exception {
        HashMap<String, String> map = ApiEncryptUtil.getTheKeys();
        String privateKeyStr = map.get("privateKey");
        String publicKeyStr = map.get("publicKey");
        System.out.println("私钥：" + privateKeyStr);
        System.out.println("公钥：" + publicKeyStr);

        // 写入资源目录
        writeToFile(publicKeyStr,PUB_KEY);
        writeToFile(privateKeyStr,PRI_KEY);

        //消息发送方
        String originData = "cicada-smile";
        System.out.println("原文：" + originData);
        String encryptData = encrypt(createPublicKey(publicKeyStr), originData.getBytes());
        System.out.println("加密："  + encryptData);

        //消息接收方
        String decryptData = decrypt(createPrivateKey(privateKeyStr), parseBase64Binary(encryptData));
        System.out.println("解密：" + decryptData);


    }

    /**
     * 密钥读取测试
     *
     * @throws Exception
     */
    public static void testReadKey() throws Exception {
        String value = getKey("rsaKey/public.key");
        System.out.println("public: " + value);

        String privateKeyStr = getKey(ApiEncryptUtil.PRI_KEY);
        String publicKeyStr = getKey(ApiEncryptUtil.PUB_KEY);
        //消息发送方
        String originData = "cicada-smile";
        System.out.println("原文：" + originData);
        String encryptData = ApiEncryptUtil.encrypt(ApiEncryptUtil.createPublicKey(publicKeyStr),
                originData.getBytes());
        System.out.println("加密：" + encryptData);
        //消息接收方
        String decryptData = ApiEncryptUtil.decrypt(ApiEncryptUtil.createPrivateKey(privateKeyStr),
                ApiEncryptUtil.parseBase64Binary(encryptData));
        System.out.println("解密：" + decryptData);
    }

    /**
     * 签名验签测试
     *
     * @throws Exception
     */
    public static void testSignVerify() throws Exception {
        String signData = "cicada-smile";
        String privateKeyStr = getKey(ApiEncryptUtil.PRI_KEY);
        String publicKeyStr = getKey(ApiEncryptUtil.PUB_KEY);
        String signValue = sign(signData, ApiEncryptUtil.createPrivateKey(privateKeyStr));
        boolean flag = verify(signData, ApiEncryptUtil.createPublicKey(publicKeyStr), signValue);
        System.out.println("原文:" + signData);
        System.out.println("签名:" + signValue);
        System.out.println("验签:" + flag);
    }


    /**
     * 1. 密钥字符串获取
     *
     * @return
     */
    protected static HashMap<String, String> getTheKeys() {
        HashMap<String, String> keyPairMap = new HashMap();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 密钥大小：1024 位
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        String publicKey = printBase64Binary(keyPair.getPublic().getEncoded());
        String privateKey = printBase64Binary(keyPair.getPrivate().getEncoded());
        keyPairMap.put("publicKey", publicKey);
        keyPairMap.put("privateKey", privateKey);
        return keyPairMap;
    }

    /**
     * 2. 私钥和公钥的文件加载
     *
     * @param keyPlace
     * @return
     * @throws Exception
     */
    public static String getKey(String keyPlace) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(ApiEncryptUtil.getFile(keyPlace)))) {
            String readLine = null;
            StringBuilder keyValue = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (!(readLine.charAt(0) == '-')) {
                    keyValue.append(readLine);
                }
            }
            return keyValue.toString();
        } catch (Exception e) {
            throw new Exception("RSA密钥读取错误", e);
        }
    }

    /**
     * 3. 公钥字符串生成公钥
     *
     * @param publicKeyValue
     * @return
     * @throws Exception
     */
    public static RSAPublicKey createPublicKey(String publicKeyValue) throws Exception {
        try {
            byte[] buffer = DatatypeConverter.parseBase64Binary(publicKeyValue);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new Exception("公钥创建失败", e);
        }
    }

    /**
     * 4. 私钥字符串生成私钥
     *
     * @param privateKeyValue
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey createPrivateKey(String privateKeyValue) throws Exception {
        try {
            byte[] buffer = javax.xml.bind.DatatypeConverter.parseBase64Binary(privateKeyValue);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new Exception("私钥创建失败", e);
        }
    }

    /**
     * 5. 公钥加密
     */
    public static String encrypt(RSAPublicKey publicKey, byte[] clearData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 无法加密");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(clearData);
            return printBase64Binary(output);
        } catch (Exception e) {
            throw new Exception("公钥加密失败", e);
        }
    }

    /**
     * 6. 私钥解密
     *
     * @param privateKey
     * @param cipherData
     * @return
     * @throws Exception
     */
    public static String decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 无法解密");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return new String(output);
        } catch (BadPaddingException e) {
            throw new Exception("私钥解密失败", e);
        }
    }

    /**
     * 7.私钥签名
     *
     * @param signData
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(String signData, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(signData.getBytes());
        return printBase64Binary(signature.sign());
    }

    /**
     * 8. 公钥验签
     *
     * @param srcData
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(parseBase64Binary(sign));
    }

    /**
     * 9. 字节数组转字符
     */
    public static String printBase64Binary(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }

    /**
     * 10. 字符转字节数组
     */
    public static byte[] parseBase64Binary(String value) {
        return DatatypeConverter.parseBase64Binary(value);
    }

    /**
     * 根据文件名获取文件
     * @param fileName
     * @return
     */
    public static InputStream getFile(String fileName){
        return ApiEncryptUtil.class.getClassLoader().getResourceAsStream(fileName);
    }

    /**
     * 根据文件名获取文件路径
     * @return
     */
    public static String getFilePath(){
        return Objects.requireNonNull(ApiEncryptUtil.class.getClassLoader().getResource("")).getPath();
    }

    /**
     * 写数据到指定文件
     * @param fileName
     */
    private static void writeToFile(String keyStr, String fileName) throws IOException {
        File file = new File(getFilePath() + fileName);
        if (file.exists()){
            FileUtil.del(file);
        }
        FileUtil.mkParentDirs(file);
        OutputStream KeyFile = new FileOutputStream(file);
        KeyFile.write(keyStr.getBytes(StandardCharsets.UTF_8));
        KeyFile.flush();
        KeyFile.close();
    }

}
```

 **存在的问题与解决办法** 

1. 通过 **分段加密和分段解密**  解决 待加密字符太长导致加密失败问题

   ```bash
   Data must not be longer than 117 bytes
   ```

2. 通过**签名和验签**  进行身份校验，避免数据被篡改

**分段加密和分段解密** 

```java
    /**
     * 11. 公钥分段加密
     * @throws Exception
     */
    public static String publicPartitionEncrypt(RSAPublicKey publicKey, byte[] clearData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = clearData.length;
        int offSet = 0;
        byte[] cache;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(clearData, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(clearData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new Exception("公钥分段加密失败", e);
        }
    }


    /**
     * 12. 私钥分段解密
     * @throws Exception
     */
    public static String privatePartitionDecrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = cipherData.length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(cipherData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(cipherData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            return out.toString();
        } catch (Exception e) {
            throw new Exception("私钥分段解密失败", e);
        }
    }

```

相关文章

1. [RSA加密算法，签名验签流程详解](https://zhuanlan.zhihu.com/p/89749126) 
2. [ApiEncryptUtil](https://github.com/iswxw/wxw-distributed/blob/dev-wxw/cloud-safety/src/main/java/com/wxw/common/utils/ApiEncryptUtil.java) 
3. [分段加密和分段解密](https://gitee.com/FlyLive/RSAdemo/tree/master) 

