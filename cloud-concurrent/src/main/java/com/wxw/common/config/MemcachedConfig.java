package com.wxw.common.config;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: wxw
 * @date: 2020-12-28-0:22
 * @description:
 */
public class MemcachedConfig {
    public static void main(String[] args) throws IOException, InterruptedException, MemcachedException, TimeoutException {
        MemcachedClient client = new XMemcachedClient("host", 11211);

        //同步存储value到memcached，缓存超时为1小时，3600秒。
        client.set("key", 3600, "someObject");
        //从memcached获取key对应的value
        Object someObject = client.get("key");

        //从memcached获取key对应的value,操作超时2秒
        someObject = client.get("key", 2000);
        //更新缓存的超时时间为10秒。
        boolean success = client.touch("key", 10);

        //删除value
        client.delete("key");
    }
}
