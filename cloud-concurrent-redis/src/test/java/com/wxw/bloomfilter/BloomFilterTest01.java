package com.wxw.bloomfilter;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author ：wxw.
 * @date ： 9:57 2020/11/6
 * @description：单元测试
 * @version: v_0.0.1
 */
@SpringBootTest
public class BloomFilterTest01 {

    @Test
    public void testData1(){

        /**
         *  初始化容量为10万大小的字符串布隆过滤器,默认误差率为0.03
         *  布隆过滤器容量为10万并非指bitmap的长度就是10万，因为需要考虑到存在hash冲突的情况，所以bitmap的实际长度要比10万要大很多
         *  bitmap长度比需要存在的数据量大小越大，误差率会越低
         * */
       int capacity = 100000; // bitmap 默认初始大小为10万
        // 误差率为 0.03
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), capacity);

        Set<String> sets = new HashSet<>();

        List<String> lists = new ArrayList<>();

        // 分别添加到三个集合当中
        for (int i = 0; i < capacity; i++) {
            String random = UUID.randomUUID().toString();
            bloomFilter.put(random);
            sets.add(random);
            lists.add(random);
        }

        int existsCount = 0;
        int mightExistsCount = 0;

        for (int i = 0; i < 100000; i++) {
            // 能被100整除则取实际的值，否则就随机一个字符串
            // String data = i%100 == 0 ? lists.get(i/100) :UUID.randomUUID().toString();
            String data = UUID.randomUUID().toString();
            // 通过布隆过滤器判断字符串是否存在
            if (bloomFilter.mightContain(data)){
                // 如果布隆过滤器认为存在，则表示可能存在的数量 mightExistsCount 自增1
                mightExistsCount++;
                if (sets.contains(data)){
                    existsCount++;
                }
            }
        }

        // 测试总次数
        BigDecimal total = new BigDecimal(100000);
        // 错误总次数
        BigDecimal error = new BigDecimal(mightExistsCount-existsCount);
        /**
         * 误差率
         * scale 保留小数
         * BigDecimal.ROUND_HALF_UP 向上取整 1.25 —— 1.30
         */
        BigDecimal rate = error.divide(total, 2, BigDecimal.ROUND_HALF_UP);

        System.out.println("初始化10万条数据,判断100个真实数据,9900个不存在数据");
        System.out.println("实际存在的字符串个数为:" + existsCount);
        System.out.println("布隆过滤器认为存在的个数为:" + mightExistsCount);
        System.out.println("误差率为:" + rate.doubleValue());

    }

    @Test
    public void testData2(){
        int capacity = 1000; // bitmap 默认初始大小为1千
        // 误差率默认为 0.03
        BloomFilter<CharSequence> bloomFilter =
                BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), capacity);
        // 去重存储
        Set<String> sets = new HashSet<>();
        // 存储总量
        List<String> lists = new ArrayList<>();
        // 分别添加到三个集合当中
        for (int i = 0; i < capacity; i++) {
            String random = UUID.randomUUID().toString();
            bloomFilter.put(random);
            sets.add(random);
            lists.add(random);
        }
        // 实际存在数量
        int existsCount = 0;
        // 可能存在的数量
        int mightExistsCount = 0;
        // 测试1万次
        for (int i = 0; i < 1000; i++) {
            // 能被10整除则取实际的值，否则就随机一个字符串
            String data = i%10 == 0 ? lists.get(i/10) :UUID.randomUUID().toString();
            // 通过布隆过滤器判断字符串是否存在
            if (bloomFilter.mightContain(data)){
                // 如果布隆过滤器认为存在，则表示可能存在的数量 mightExistsCount 自增1
                mightExistsCount++;
                // 再判读实际是否存在，existsCount
                if (sets.contains(data)){
                    existsCount++;
                }
            }
        }
        // 测试总次数 1000
        BigDecimal total = new BigDecimal(1000);
        // 错误总次数
        BigDecimal error = new BigDecimal(mightExistsCount-existsCount);
        /**
         * 误差率
         * scale 保留小数
         * BigDecimal.ROUND_HALF_UP 向上取整 1.25 —— 1.30
         */
        BigDecimal rate = error.divide(total, 2, BigDecimal.ROUND_HALF_UP);
        System.out.println("初始化1000条数据,判断100个真实数据,900个不存在数据");
        System.out.println("实际存在的字符串个数为:" + existsCount);
        System.out.println("布隆过滤器认为存在的个数为:" + mightExistsCount);
        System.out.println("误差率为:" + rate.doubleValue());
    }
}
