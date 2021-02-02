package com.wxw.common.redis_distributed_lock;

import com.wxw.common.manager.tools.OrderNumGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 15:14 2021/2/2
 * @description： 第三阶段 保证 加锁+过期时间原子性
 * @link:
 * @version: v_0.0.1
 */
@Component
public class ThreeStageRedisLock {

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 1. 占分布式锁，去redis占坑
     * 分布式锁加锁
     * 面临的问题：setnx设置好，并原子性设置过期时间，但是在获取key后准备释放锁之前 突然断电或宕机，锁没有释放，又导致死锁
     * 解决方案：设置过期时间和占位必须是原子操作。redis支持使用setNxEx命令
     */
    public void lock() {
        String uniqueId = OrderNumGenerator.getUniqueId();
        //  分布式锁占坑
        boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uniqueId,300,TimeUnit.SECONDS);
        if (lock) {
            // TODO: 2021/2/2 加锁成功... 执行业务

            // 突然断电

            // 设置过期时间 30s过期, 必须和加锁一起作为原子性操作
            // redisTemplate.expire("lock", 30, TimeUnit.SECONDS) ;

            // 业务逻辑执行完后 删除Key 释放锁，不能删除别人的锁
            redisTemplate.delete("lock");
        } else {
            // 加锁失败，重试。 synchronized()
            // 休眠100ms重试
            // 自旋
            lock();
        }
    }

    /**
     * 释放锁
     */
    public void unlock(){
        // 业务逻辑执行完后 删除Key 释放锁
        redisTemplate.delete("lock");
    }
}