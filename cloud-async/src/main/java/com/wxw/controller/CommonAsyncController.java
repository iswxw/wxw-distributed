package com.wxw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author weixiaowei
 * @desc: 异步调用
 * @date: 2021/7/5
 */
@Slf4j
@RequestMapping("/common")
@RestController
public class CommonAsyncController {

    @Resource
    public ThreadPoolTaskExecutor taskExecutor;

    /**
     * Servlet 方式实现异步请求
     * curl http://localhost:8080/async/servlet
     */
    @GetMapping(value = "/async/servlet")
    public void servletReq (HttpServletRequest request, HttpServletResponse response) {
        AsyncContext asyncContext = request.startAsync();
        //设置监听器:可设置其开始、完成、异常、超时等事件的回调处理
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("超时了...");
                //做一些超时后的相关操作...
            }
            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("线程开始");
            }
            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("发生错误："+event.getThrowable());
            }
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("执行完成");
                //这里可以做一些清理资源的操作...
            }
        });
        //设置超时时间
        asyncContext.setTimeout(20000);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    System.out.println("内部线程：" + Thread.currentThread().getName());
                    asyncContext.getResponse().setCharacterEncoding("utf-8");
                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                    asyncContext.getResponse().getWriter().println("这是异步的请求返回");
                } catch (Exception e) {
                    System.out.println("异常："+e);
                }
                //异步请求完成通知
                //此时整个请求才完成
                asyncContext.complete();
            }
        });
        //此时之类 request的线程连接已经释放了
        System.out.println("主线程：" + Thread.currentThread().getName());
    }

    /**
     * 直接返回的参数包裹一层callable即可，可以继承WebMvcConfigurer类来设置默认线程池和超时处理
     *
     * @return
     */
    @GetMapping(value = "/async/callable")
    public Callable<String> callableReq () {
        System.out.println("外部线程：" + Thread.currentThread().getName());
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10000);
                System.out.println("内部线程：" + Thread.currentThread().getName());
                return "callable!";
            }
        };
    }

    /**
     * 在Callable外包一层，给WebAsyncTask设置一个超时回调，即可实现超时处理
     * curl http://localhost:8080/async/web-async
     * @return
     */
    @GetMapping(value = "/async/web-async")
    public WebAsyncTask<String> webAsyncReq () {
        System.out.println("外部线程：" + Thread.currentThread().getName());
        Callable<String> result = () -> {
            System.out.println("内部线程开始：" + Thread.currentThread().getName());
            try {
                log.info("任务处理中...");
                TimeUnit.SECONDS.sleep(4);
            } catch (Exception e) {
                // TODO: handle exception
            }
            log.info("副线程返回");
            System.out.println("内部线程返回：" + Thread.currentThread().getName());
            return "success";
        };
        WebAsyncTask<String> wat = new WebAsyncTask<String>(3000L, result);
        wat.onTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "超时";
            }
        });
        return wat;
    }

    /**
     * DeferredResult可以处理一些相对复杂一些的业务逻辑，最主要还是可以在另一个线程里面进行业务处理及返回，
     * 即可在两个完全不相干的线程间的通信。
     *
     */
    @GetMapping(value = "/async/deferredResult")
    public DeferredResult<String> deferredResultReq () {
        System.out.println("外部线程：" + Thread.currentThread().getName());
        //设置超时时间
        DeferredResult<String> result = new DeferredResult<String>(60*1000L);
        //处理超时事件 采用委托机制
        result.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("DeferredResult超时");
                result.setResult("超时了!");
            }
        });
        result.onCompletion(new Runnable() {
            @Override
            public void run() {
                //完成后
                System.out.println("调用完成");
            }
        });
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //处理业务逻辑
                System.out.println("内部线程：" + Thread.currentThread().getName());
                //返回结果
                result.setResult("DeferredResult!!");
            }
        });
        return result;
    }

}
