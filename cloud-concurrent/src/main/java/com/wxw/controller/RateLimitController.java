package com.wxw.controller;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 14:46 2020/11/25
 * @description：限流 https://segmentfault.com/a/1190000020272200?utm_source=tag-newest
 * @version: v_0.0.1
 */
@RestController
@Slf4j
public class RateLimitController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * double permitsPerSecond  生成令牌数量/单位时间内
     * RateLimiter.SleepingStopwatch.createFromSystemTimer() 创建一个每秒包含permitsPerSecond个令牌的令牌桶，可以理解为QPS最多为permitsPerSecond
     */
    private static final RateLimiter smoothBursty = RateLimiter.create(2); // 每秒生成两个令牌

    /**
     * double permitsPerSecond  生成令牌数量/单位时间内
     * warmupPeriod 预热时间 包含某个时间段的预热期
     */
    private static final RateLimiter smoothWarmingUp = RateLimiter.create(5,10, TimeUnit.SECONDS);

    /**
     * tryAcquire尝试获取 permit，默认超时时间是0，意思是拿不到就立即返回false
     * @return
     */
    @GetMapping("/try_limit_rate")
    public String tryLimitRate(){
        if (smoothBursty.tryAcquire()) { //  一次拿1个
           log.info("当前时间：{}",sdf.format(new Date()));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
          log.info("开始限流：{}",smoothBursty.getRate());
        }
        return "tryAcquire尝试获取 permit，默认超时时间是0，意思是拿不到就立即返回false";
    }

    /**
     * acquire拿不到就等待，拿到为止
     * @return
     */
    @GetMapping("/limit_rate")
    public String limitRate(){
        double acquire = smoothBursty.acquire(5); // 一次拿5个
        log.info("acquire拿不到就等待，拿到为止:{}, acquire = {}",sdf.format(new Date()),acquire);
        return "acquire拿不到就等待，拿到为止";
    }
}
