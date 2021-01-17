package com.wxw.juc.threadpool;

import java.util.concurrent.*;

/**
 * @author: wxw
 * @date: 2020-12-05-22:02
 */
public class ThreadPoolTest_01 {
    public static void main(String[] args) {
        // 构造函数
        ThreadPoolExecutor executor = buildThreadPoolExecutor();
        Future<?> submit = executor.submit(() -> {  });
        executor.execute(()->{});
        // 工具类
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<?> submit1 = executorService.submit(() -> { });
        executorService.execute(()->{});
    }

    // 构建一个线程池
    private static ThreadPoolExecutor buildThreadPoolExecutor(){
        return new ThreadPoolExecutor(10,
                30,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new NamedThreadFactory("Java半颗糖"));
    }
}
