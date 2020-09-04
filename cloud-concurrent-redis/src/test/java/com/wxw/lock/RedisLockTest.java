package com.wxw.lock;

import com.wxw.RedisMain8082;
import com.wxw.base.BaseRedisTest;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ Author ：wxw.
 * @ Date ： 16:35 2020/9/4
 * @ Description：Redis锁的描述
 * @ Version:   v_0.0.1
 */
@SpringBootTest(classes = RedisMain8082.class)
public class RedisLockTest extends BaseRedisTest {

    @Resource
    private RedisTemplate redisLock;

    @Test
    public void testData2(){
        redisLock.opsForValue().set("wxw","Redis分布式锁设置数据",30, TimeUnit.SECONDS);
        Object wxw = redisLock.opsForValue().get("wxw");
        logger.info("测试Redis数据 = {}",wxw);
    }
}

