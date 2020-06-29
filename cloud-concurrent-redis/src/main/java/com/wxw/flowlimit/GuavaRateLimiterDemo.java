package com.wxw.flowlimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ Author ：wxw.
 * @ Date ： 15:34 2020/6/29
 * @ Description：限流神器 Guava RateLimiter
 * @ Version:   v_0.0.1
 */
public class GuavaRateLimiterDemo {

    public static ConcurrentHashMap<String, RateLimiter> resourceRateLimiter = new ConcurrentHashMap<>();

    static {
        createResourceLimiter("order",50);
    }

    private static void createResourceLimiter(String resource, int qps) {
           if (resourceRateLimiter.contains(resource)){
               resourceRateLimiter.get(resource).setRate(qps);
           }else {
               RateLimiter rateLimiter = RateLimiter.create(qps);
               resourceRateLimiter.putIfAbsent(resource,rateLimiter);
           }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                public void run() {
                    if (resourceRateLimiter.get("order").tryAcquire(10, TimeUnit.MICROSECONDS)){
                        System.out.println(" 执行业务逻辑...");
                    }else {
                        System.out.println(" 限流中...");
                    }
                }
            }).start();
        }
    }
}
