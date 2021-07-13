package com.wxw.manager.distributed_lock.redis_distributed_lock;

import com.wxw.common.tools.OrderNumGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 15:08 2021/2/2
 * @description： 第二阶段 设置过期时间，避免死锁
 * @link:
 * @version: v_0.0.1
 */
@Component
public class TwoStageRedisLock {

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 基于OneStageRedisLock中分布式锁加锁优化 是通过设置过期时间使得锁可以被自动释放，避免死锁
     * 但是面临的问题：假如setnx设置完后，正要去设置过期时间，（突然断电或服务宕机，导致设置过期时间不成功，锁无法被释放导致死锁）
     * 解决方案：redis支持使用setNxEx命令,所以需要保证 设置锁 和 给锁设置过期时间 是一个原子操作
     */
    public void lock() {
        String uniqueId = OrderNumGenerator.getUniqueId();
       // boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uniqueId);
        boolean lock =  redisTemplate.opsForValue().setIfAbsent("lock",uniqueId,30, TimeUnit.SECONDS);
        if (lock) {
            // TODO: 2021/2/2 加锁成功... 执行业务
            // 突然断电 || 宕机
            // 设置过期时间 30s过期
            //  redisTemplate.expire("lock", 30, TimeUnit.SECONDS) ;

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

