package com.wxw.common.redission_distributed_lock;

import org.redisson.client.handler.ConnectionWatchdog;

/**
 * @author ：wxw.
 * @date ： 14:12 2021/2/3
 * @description：Redission 源码验证
 * @link:
 * @version: v_0.0.1
 */
public class RedissionSource {
    public static void main(String[] args) {
        // 加锁脚本
        String lockScript =
                "if (redis.call('exists', KEYS[1]) == 0) then" +
                "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                "return nil;" +
                " end; " +
                "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                        "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return nil;" +
                 " end; " +
                 "return redis.call('pttl', KEYS[1]);";

    }
}
