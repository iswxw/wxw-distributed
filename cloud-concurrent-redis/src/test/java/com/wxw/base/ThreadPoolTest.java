package com.wxw.base;

import jodd.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 13:55 2020/11/26
 * @description：用于测试的线程池
 * @version: v_0.0.1
 */
public class ThreadPoolTest {

    protected ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolTest() {
        this.threadPoolExecutor = threadPoolExecutor();
    }

    private  ThreadPoolExecutor threadPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                0L,TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(1024),
                new ThreadFactoryBuilder().setNameFormat("Thread-pool-%d").get(),
                new ThreadPoolExecutor.AbortPolicy());
        return threadPoolExecutor;
    }
}
