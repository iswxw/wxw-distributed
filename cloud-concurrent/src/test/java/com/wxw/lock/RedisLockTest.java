package com.wxw.lock;

import com.wxw.base.BaseRedisTest;
import com.wxw.manager.distributed_lock.redission_annotation_lock.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ Author ：wxw.
 * @ Date ： 16:35 2020/9/4
 * @ Description：Redis锁的描述
 * @ Version:   v_0.0.1
 * @RunWith(SpringRunner.class) 注解的作用：
 * 让测试在Spring容器环境下执行。如测试类中无此注解，将导致service,dao等自动注入失败。
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockTest extends BaseRedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisLock redisLock;


    @Test
    public void testData2() {
        redisTemplate.opsForValue().set("wxw", "Redis分布式锁设置数据完成", 30, TimeUnit.SECONDS);
        Object wxw = redisTemplate.opsForValue().get("wxw");
        logger.info("测试Redis数据 = {}", wxw);
    }

    @Test
    public void testData1() {
        boolean lock = redisLock.lock("1234567890");
        assert lock;
    }
}

