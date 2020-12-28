package com.wxw.common.manager.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * @ Author ：wxw.
 * @ Date ： 16:28 2020/9/4
 * @ Description：Redis分布式锁的基类
 * @ Version:   v_0.0.1
 */
public abstract class BaseLock {

    @Resource
    protected StringRedisTemplate stringRedis;

    protected final Logger logger = LoggerFactory.getLogger(getClass());
}
