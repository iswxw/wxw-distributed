package com.wxw.common.zk_distributed_lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 16:34 2020/11/16
 * @description：分布式锁测试
 * @version: v_0.0.1
 */
@Component
public class RedissionTest01 {

    @Resource
    private RedissonClient redissonClient;

    public void test_lock(){
        RLock lock = redissonClient.getLock("-1");
        lock.unlock();
    }


    public static void main(String[] args) {

    }
}
