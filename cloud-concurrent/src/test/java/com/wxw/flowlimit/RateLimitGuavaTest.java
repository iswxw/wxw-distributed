package com.wxw.flowlimit;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ：wxw.
 * @date ： 9:26 2020/11/26
 * @description：Guava限流单元测试
 * @version: v_0.0.1
 */
@SpringBootTest
@Slf4j
public class RateLimitGuavaTest {


    // 透支未来令牌
    @Test
    public void testData1(){
        RateLimiter rateLimiter = RateLimiter.create(1);
        log.info("获取10个令牌需要消耗时间：{} s",rateLimiter.acquire(10));
        log.info("获取1个令牌需要消耗时间：{} s",rateLimiter.acquire(1));
    }

}
