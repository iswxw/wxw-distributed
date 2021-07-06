package com.wxw.controller;

import com.wxw.manager.pools.RunnableTask1;
import com.wxw.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/5
 */
@Slf4j
@RequestMapping("/async")
@RestController
public class AsyncController {

    @Resource
    private AsyncService asyncService;

    /**
     * 线程池 异常捕获
     *
     */
    @GetMapping(value = "test1")
    public String test1(){
        asyncService.asyncMethodWithVoidReturnType();
        log.info("============>" + Thread.currentThread().getName());
        return "异步线程池，捕获异常,正在解析......";
    }

    /**
     * 异步处理2：使用springBoot自带async注解
     * curl http://localhost:8080/async/test2
     */
    @GetMapping(value = "test2")
    public String test2() {
        asyncService.async_test2();
        log.info("============>" + Thread.currentThread().getName());
        return "异步,正在解析......";
    }


    /**
     * 异步处理3：线程池 ExecutorService 创建新线程处理
     * curl http://localhost:8080/async/test3
     *
     * @return
     */
    @GetMapping(value = "test3")
    public String test3() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        RunnableTask1 task1 = new RunnableTask1();
        service.execute(task1);
        log.info("=========》当前线程名：" + Thread.currentThread().getName());
        return "异步,正在解析......";
    }



}
