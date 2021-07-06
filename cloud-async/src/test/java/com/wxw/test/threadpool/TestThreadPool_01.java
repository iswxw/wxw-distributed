package com.wxw.test.threadpool;

import com.wxw.TestAsync;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

/**
 * @author weixiaowei
 * @desc: 注意：该线程池被所有的异步任务共享，而不属于某一个异步任务
 * @date: 2021/7/6
 */
public class TestThreadPool_01 extends TestAsync {

    // 异常捕获线程池
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    // 普通线程池
    @Resource
    private ThreadPoolTaskExecutor taskExecutor_1;


    @Test
    public void test_1() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int exception = 1/0;
            }
        });
    }

    @Test
    public void test_2() {
        taskExecutor_1.execute(new Runnable() {
            @Override
            public void run() {
                int exception = 1/0;
            }
        });
    }
}
