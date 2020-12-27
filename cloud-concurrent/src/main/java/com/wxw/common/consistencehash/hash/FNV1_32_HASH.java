package com.wxw.common.consistencehash.hash;

/**
 * @author: wxw
 * @date: 2020-12-28-0:38
 * @description: 来自于memcached包
 */
public class FNV1_32_HASH {

    /**
     * HashAlgorithm
     * FNV1_32_HASH hash 算法
     * @param str
     * @return
     */
    public static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数，则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }
}
