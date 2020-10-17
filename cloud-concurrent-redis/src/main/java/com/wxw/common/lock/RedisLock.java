package com.wxw.common.lock;

import com.wxw.common.base.BaseLock;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ Author ：wxw.
 * @ Date ： 16:10 2020/9/4
 * @ Description：Redis一般锁实现
 * @ Version:   v_0.0.1
 */
@Component
public class RedisLock extends BaseLock {

    /**
     * 加锁
     * @param id
     * @return
     */
    public boolean lock(String id){
        stringRedis.opsForValue().set("wxw","Redis分布式锁设置数据_"+id,30,TimeUnit.SECONDS);
        String wxw = stringRedis.opsForValue().get("wxw");
        logger.info("wxw = {}",wxw);
        return true;
    }
}
