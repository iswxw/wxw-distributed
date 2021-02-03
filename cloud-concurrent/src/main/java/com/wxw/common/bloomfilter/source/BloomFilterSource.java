package com.wxw.common.bloomfilter.source;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.Serializable;


/**
 * https://www.cnblogs.com/jackion5/p/13691769.html
 * @param <T>
 */
public final class BloomFilterSource<T> implements Predicate<T>, Serializable {
    /**
     * @param funnel  存入元素的类型
     * @param expectedInsertions 期望保存元素的个数
     * @param <T>
     * @return
     */
    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int expectedInsertions) {
        //调用重载函数
        return create(funnel, (int) expectedInsertions);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long expectedInsertions) {
        /** 默认误差率为0.03 */
        return create(funnel, expectedInsertions, 0.03D);
    }

    /**
     * @param fpp 误差率
     */
    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long expectedInsertions, double fpp) {
        //return create(funnel, expectedInsertions, fpp, BloomFilterSource.super);
        return null;
    }

    /**
     * @param strategy 哈希函数的策略
     * @return
     */
    @VisibleForTesting
    static <T> BloomFilter create(Funnel<? super T> funnel, long expectedInsertions, double fpp, CommandNaming.Strategy strategy) {
        // 存入元素的类型 不能为空
        Preconditions.checkNotNull(funnel);
        // 检测参数  期望保存元素的个数 必须大于0
        Preconditions.checkArgument(expectedInsertions >= 0L, "Expected insertions (%s) must be >= 0", expectedInsertions);
        //参数校验,误差率必须为大于0且小于1
        Preconditions.checkArgument(fpp > 0.0D, "False positive probability (%s) must be > 0.0", fpp);
        Preconditions.checkArgument(fpp < 1.0D, "False positive probability (%s) must be < 1.0", fpp);
        Preconditions.checkNotNull(strategy);
        /** 期待容量不可为0*/
        if (expectedInsertions == 0L) {
            expectedInsertions = 1L;
        }
        /** 根据期待容量和误差率,计算bitmap的位数 */
//        long numBits = optimalNumOfBits(expectedInsertions, fpp);
        /** 根据期待容量和bitmap的位数,计算需要的hash函数的数量 */
//        int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
        try {
            /** 创建BloomFilter对象 构造函数私有化，无法直接使用 */
          // return BloomFilter.create(new BloomFilterStrategies.LockFreeBitArray(10L),10, funnel, strategy);
        } catch (IllegalArgumentException var10) {
            throw new IllegalArgumentException("Could not create BloomFilter of numBits bits", var10);
        }
        return null;
    }

    // 向布隆过滤器中插入元素
    // BloomFilter插入元素调用了BloomFilter内部类Strategy的实现类的put方法

    // 查询布隆过滤器是否存在元素


    @Override
    public boolean apply(@Nullable T t) {

        return false;
    }

    @Override
    public boolean test(@Nullable T input) {
        return false;
    }
}
