package com.wxw.manager.pools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author weixiaowei
 * @desc:  自定义线程池 实现异常处理
 * @date: 2021/7/5
 */
@Slf4j
@Configuration
public class AsyncThreadPoolTaskExecutor {

    @Bean
    public AsyncTaskExecutor taskExecutor_1() {
        log.info("新建一个线程池");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("common-task-Executor-");
        executor.setMaxPoolSize(10);
        /**
         * rejection-policy：当pool已经达到max size的时候，如何处理新任务
         * CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
