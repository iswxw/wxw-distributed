package com.wxw.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

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
     * 异步方法 不带返回值
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

    /**
     * 异步方法 带返回值
     */
    @Async
    public Future<String> doReturn(int i){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(String.format("这个是第{%s}个异步调用的证书",i));
    }

    @Async
    public String asyncMethodWithVoidReturnType() {
        System.out.println("线程名称："+Thread.currentThread().getName() + " be ready to read data!");
        try {
            Thread.sleep(1000 * 5);
            System.out.println("---------------------》》》无返回值延迟3秒：");
           //int exception = 1/0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "已进入到异步";
    }
}
