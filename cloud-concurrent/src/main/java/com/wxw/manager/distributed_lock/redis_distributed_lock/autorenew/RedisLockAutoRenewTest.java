package com.wxw.manager.distributed_lock.redis_distributed_lock.autorenew;

import com.wxw.common.tools.OrderNumGenerator;

/**
 * @author ：wxw.
 * @date ： 11:56 2021/2/3
 * @description： 锁续约测试
 * @link:
 * @version: v_0.0.1
 */
public class RedisLockAutoRenewTest {




    public void lock(){
        String uniqueId = OrderNumGenerator.getUniqueId();
        RedisLockAutoRenew redisLockAutoRenew = new RedisLockAutoRenew("lockField", "lockKey", uniqueId, 30);
        Thread survivalThread = new Thread(redisLockAutoRenew);
        survivalThread.setDaemon(Boolean.TRUE); // 设置为守护线程
        survivalThread.start();
        // 设置守护线程的关闭标记
        redisLockAutoRenew.stop();
        // 通过interrupt()去中断sleep状态，保证线程及时销毁
        survivalThread.interrupt();

    }

    public static void main(String[] args) {

    }
}
