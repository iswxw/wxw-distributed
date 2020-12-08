package com.wxw.flowlimit;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.naming.LimitExceededException;
import java.util.concurrent.TimeUnit;

/**
 * @author: wxw
 * @date: 2020-11-13-22:40
 */
@SpringBootTest
public class RedisFlowLimit {

    // 限制请求
    private static long limit_count = 100;
    //当前数量
    private static long current_count = 1000;
    // 限制key
    private static String key = "username:day:addcart";

    @Resource
    private RedisTemplate redis;

    /**
     * 架构修炼之道 限流
     */
    public void Test01() throws LimitExceededException {
        if (current_count > limit_count) {
            throw new LimitExceededException("超过限流阈值");
        }
        if (redis.getExpire(key) < 0) { // key 不存在或者没有设置过期时间
            // 事务开始
            redis.opsForValue().increment(key);
            redis.expire(key, 60, TimeUnit.SECONDS); // 设置一分钟过期
            // 事务结束，这两个操作需要在一个事务中
        } else {
            redis.opsForValue().increment(key);
        }
    }
}
