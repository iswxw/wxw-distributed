package com.wxw.juc.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: wxw
 * @date: 2020-12-05-22:02
 */
public class ThreadPoolTest_01 {
    public static void main(String[] args) {

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
