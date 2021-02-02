package com.wxw.juc.threadpool.demo;

import com.wxw.juc.threadpool.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * @author: wxw
 * @date: 2021-01-17-9:47
 * @link:
 * @description:
 */
public class ThreadPoolReturnValue {

    public static final Integer THREAD_COUNT = 5;

    public static void main(String[] args) {
        // 构造函数
        ThreadPoolExecutor executor = buildThreadPoolExecutor();
        Future<?> submit = executor.submit(() -> {  });
        executor.execute(()->{});

        // 工具类
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        Future<?> submit1 = executorService.submit(() -> { });
        executorService.execute(()->{});


        // 新型工具
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        // 使用线程池并发处理逻辑
        ForkJoinTask<?> submit2 = forkJoinPool.submit(() -> { });
        forkJoinPool.execute(()->{});
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
