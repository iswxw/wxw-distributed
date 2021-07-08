package com.wxw.manager.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.BitSet;

/**
 * @author ：wxw.
 * @date ： 9:55 2020/11/6
 * @description：布隆过滤器
 * @version: v_0.0.1
 */
public class BloomFilterTools {
    public static void main(String[] args) {
        /**
         * @expectedInsertions 创建一个插入对象为10W，
         * @fpp:误报率为0.01%的布隆过滤器
         */
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 100000, 0.0001);
        bloomFilter.put("121");
        bloomFilter.put("122");
        bloomFilter.put("123");
        System.out.println(bloomFilter.mightContain("121"));
        System.out.println(bloomFilter.mightContain("120"));

        // JDK自带的BitSet来实现，但存在两个问题：OOM和持久化问题
        BitSet bitSet = new BitSet();
    }
}
