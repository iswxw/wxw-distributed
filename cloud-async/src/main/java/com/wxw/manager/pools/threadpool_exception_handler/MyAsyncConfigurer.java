package com.wxw.manager.pools.threadpool_exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * @author weixiaowei
 * @desc: 通过实现AsyncConfigurer自定义异常线程池，包含异常处理 注意：该线程池被所有的异步任务共享，而不属于某一个异步任务
 * @link: https://www.cnblogs.com/sbj-dawn/p/9075573.html
 * @date: 2021/7/5
 */
@Slf4j
@Component
public class MyAsyncConfigurer implements AsyncConfigurer {

    /**
     * 定义异常线程池 beanName
     * @return
     */
    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(2);//当前线程数
        threadPool.setMaxPoolSize(120);// 最大线程数
        threadPool.setQueueCapacity(1);//线程池所使用的缓冲队列
        threadPool.setWaitForTasksToCompleteOnShutdown(true);//等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setAwaitTerminationSeconds(60 * 15);// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setThreadNamePrefix("async-task-Executor-");//  线程名称前缀
        threadPool.initialize(); // 初始化
        System.out.println("--------------------------》》》开启异常线程池");
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }

}

/**
 * 自定义异常处理类
 * @author hry
 */
@Slf4j
class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        log.info("Exception message - " + throwable.getMessage());
        log.info("Method name - " + method.getName());
        for (Object param : obj) {
            log.info("Parameter value - " + param);
        }
    }

}
