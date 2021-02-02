package com.wxw.common.redis_distributed_lock;

import com.wxw.common.manager.tools.OrderNumGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 14:42 2021/2/2
 * @description：第一阶段 普通的分布式锁
 * @link: https://zhuanlan.zhihu.com/p/347945687
 * @version: v_0.0.1
 */
@Component
public class OneStageRedisLock{

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 分布式锁加锁
     * 面临的问题：setnx占好了坑，但是业务代码异常或程序在执行过程中宕机，即没有执行成功删除锁逻辑，导致死锁
     * 解决方案：设置锁的自动过期，即使没有删除，会自动删除。
     */
    public void lock() {
        String uniqueId = OrderNumGenerator.getUniqueId();
        boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uniqueId);
        if (lock) {
            // TODO: 2021/2/2 加锁成功... 执行业务

            // 正在执行业务逻辑，突然宕机，后面的释放锁  unlock() ，没有执行，导致死锁

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
