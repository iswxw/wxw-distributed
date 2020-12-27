package com.wxw.common.consistencehash.hash;

import net.rubyeye.xmemcached.HashAlgorithm;
import org.redisson.misc.Hash;

/**
 * @author: wxw
 * @date: 2020-12-28-0:04
 * @description: hash测试
 */
public class HashTest {
    /**
     * 常用的 哈希算法
     * 1. hashcode() 的哈希算法
     *     (1) 生成有负值，需要取绝对值
     *     (2) 不够散列
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("hashcode():");
        System.out.println("192.168.0.0:111 的哈希值：" + "192.168.0.0:1111".hashCode());
        System.out.println("192.168.0.1:111 的哈希值：" + "192.168.0.1:1111".hashCode());
        System.out.println("192.168.0.2:111 的哈希值：" + "192.168.0.2:1111".hashCode());
        System.out.println("192.168.0.3:111 的哈希值：" + "192.168.0.3:1111".hashCode());
        System.out.println("192.168.0.4:111 的哈希值：" + "192.168.0.4:1111".hashCode());
        System.out.println("192.168.1.0:111 的哈希值：" + "192.168.1.0:1111".hashCode());
        System.out.println();
        // 来源于 memcached
        System.out.println("FNV1_32_HASH");
        System.out.println("192.168.0.0:111 的哈希值：" + HashAlgorithm.FNV1_32_HASH.hash("192.168.0.0:1111"));
        System.out.println("192.168.0.1:111 的哈希值：" + HashAlgorithm.FNV1_32_HASH.hash("192.168.0.1:1111"));
        System.out.println("192.168.0.2:111 的哈希值：" + HashAlgorithm.FNV1_32_HASH.hash("192.168.0.2:1111"));
        System.out.println("192.168.0.3:111 的哈希值：" + HashAlgorithm.FNV1_32_HASH.hash("192.168.0.3:1111"));
        System.out.println("192.168.0.4:111 的哈希值：" + HashAlgorithm.FNV1_32_HASH.hash("192.168.0.4:1111"));
        System.out.println("192.168.1.5:111 的哈希值：" + HashAlgorithm.FNV1_32_HASH.hash("192.168.1.0:1111"));

    }
}
