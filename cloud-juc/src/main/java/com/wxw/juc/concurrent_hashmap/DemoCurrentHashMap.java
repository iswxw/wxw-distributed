package com.wxw.juc.concurrent_hashmap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.ObjectUtils;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author ：wxw.
 * @date ： 15:57 2021/2/23
 * @description： ConcurrentHashMap真的安全吗？
 * @link: https://xie.infoq.cn/article/de8fddb5ed82d469e2abfc9d4
 * @version: v_0.0.1
 */
@Slf4j
public class DemoCurrentHashMap {

    public static final Integer ITEM_COUNT = 1000;
    public static final Integer THREAD_COUNT = 10;


    public static void main(String[] args) throws InterruptedException {
        DemoCurrentHashMap currentHashMap = new DemoCurrentHashMap();
        currentHashMap.concurrenthashmap_wrong();
        currentHashMap.concurrenthashmap_right();
    }

    private void concurrenthashmap_wrong() throws InterruptedException {
        ConcurrentHashMap<String,Long> concurrentHashMap = getData(ITEM_COUNT-100);
        // 初始化900 个元素
        log.info("init size = {}",concurrentHashMap.size());
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        // 使用线程池并发处理逻辑
        forkJoinPool.execute(()-> IntStream.rangeClosed(1,10).parallel().forEach(i->{
            // 查询还需要补充多少个元素
            int gap = ITEM_COUNT - concurrentHashMap.size();
            log.info("gap1 size = {}",gap);
            // 补充元素
            concurrentHashMap.putAll(getData(gap));
        }));
        // 等待所有线程完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);

        // 会是1000吗 ？
        log.info("finish size = {}",concurrentHashMap.size());
    }

    private void concurrenthashmap_right() throws InterruptedException {
        ConcurrentHashMap<String,Long> concurrentHashMap = getData(ITEM_COUNT-100);
        // 初始化900 个元素
        log.info("init size = {}",concurrentHashMap.size());
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        // 使用线程池并发处理逻辑
        forkJoinPool.execute(()-> IntStream.rangeClosed(1,10).parallel().forEach(i->{
          synchronized (concurrentHashMap){
              // 查询还需要补充多少个元素
              int gap = ITEM_COUNT - concurrentHashMap.size();
              log.info("gap1 size = {}",gap);
              // 补充元素
              concurrentHashMap.putAll(getData(gap));
              log.info("process size = {}",concurrentHashMap.size());
          }
        }));
        // 等待所有线程完成
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);

        // 会是1000吗 ？
        log.info("finish size = {}",concurrentHashMap.size());
    }




    // 初始化容器大小
    private ConcurrentHashMap<String, Long> getData(int init) {
        ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>();
        while (init > 0) {
            concurrentHashMap.put(getRandom(), new Long(init));
            init--;
        }
        return concurrentHashMap;
    }


    // 生成随机字符串
    private String getRandom(){
        return RandomStringUtils.random(5);
    }
}
