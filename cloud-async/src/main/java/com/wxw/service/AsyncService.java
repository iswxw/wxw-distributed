package com.wxw.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/5
 */
@Slf4j
@Service
public class AsyncService {


    /**
     * 同步方法
     */
    public void sync_1() {
        synchronized (this) {
            try {
                for (int i = 1; i <= 100; i++) {
                    this.wait(3000);
                    log.info(Thread.currentThread().getName() + "----------同步：>" + i);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 异步方法
     * 有 @Async注解的方法，默认就是异步执行的，会在默认的线程池中执行，
     * 但是此方法不能在本类调用；启动类需添加直接开启异步执行@EnableAsync。
     * */
    @Async
    public void async_test2() {
        synchronized (this) {
            try {
                for (int i = 1; i <= 100; i++) {
                    this.wait(3000);
                    log.info(Thread.currentThread().getName() + "----------异步：>" + i);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
