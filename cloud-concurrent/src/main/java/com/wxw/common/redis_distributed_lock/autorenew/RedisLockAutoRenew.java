package com.wxw.common.redis_distributed_lock.autorenew;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：wxw.
 * @date ： 11:51 2021/2/3
 * @description：分布式锁自动续约
 * @link: https://juejin.cn/post/6844903764868988936
 * @version: v_0.0.1
 */
public class RedisLockAutoRenew implements Runnable  {

    private final static Logger logger = LoggerFactory.getLogger(RedisLockAutoRenew.class);
    private static final int REDIS_EXPIRE_SUCCESS = 1;

    private String field;

    private String key;

    private String value;

    private int lockTime; // 锁续期时长为 lockTime 的 2/3

    public RedisLockAutoRenew(String field, String key, String value, int lockTime) {
        this.field = field;
        this.key = key;
        this.value = value;
        this.lockTime = lockTime;
    }

    //线程关闭的标记
    private volatile Boolean signal;

    void stop() {
        this.signal = Boolean.FALSE;
    }

    @Override
    public void run() {
        // waitTime设置为Math.max(1, lockTime * 2 / 3)，即守护线程许需要等待waitTime后才可以去重新设置锁的超时时间，避免了资源的浪费。
        int waitTime = lockTime * 1000 * 2 / 3;
        while (signal) {
            try {
                Thread.sleep(waitTime);
                // expandLockTime是通过Lua脚本实现
                // 在expandLockTime时也去判断了当前持有锁的对象是否一致，避免了胡乱重置锁超时时间的情况。
                if (CacheUtils.expandLockTime(field, key, value, lockTime) == REDIS_EXPIRE_SUCCESS) {
                    if (logger.isInfoEnabled()) {
                        logger.info("expandLockTime 成功，本次等待{}ms，将重置锁超时时间重置为{}s,其中field为{},key为{}", waitTime, lockTime, field, key);
                    }
                } else {
                    if (logger.isInfoEnabled()) {
                        logger.info("expandLockTime 失败，将导致SurvivalClamConsumer中断");
                    }
                    this.stop();
                }
            } catch (InterruptedException e) {
                if (logger.isInfoEnabled()) {
                    logger.info("SurvivalClamProcessor 处理线程被强制中断");
                }
            } catch (Exception e) {
                logger.error("SurvivalClamProcessor run error", e);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("SurvivalClamProcessor 处理线程已停止");
        }
    }
}
