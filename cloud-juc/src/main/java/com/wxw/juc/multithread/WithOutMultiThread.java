package com.wxw.juc.multithread;

import java.util.concurrent.Callable;

/**
 * @author: wxw
 * @date: 2021-02-06-23:04
 * @link:
 * @description:
 */
public class WithOutMultiThread {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        // 第一个RPC接口，调用花费100ms
        Thread.sleep(100L);

        // 第二个RPC接口，调用花费200ms
        Thread.sleep(200L);

        // 第二个RPC接口，调用花费150ms
        Thread.sleep(150L);

        // 第二个RPC接口，调用花费300ms
        Thread.sleep(300L);

        long endTime = System.currentTimeMillis();
        System.out.println("四个接口调用完成，总耗时：" + (endTime - startTime) + "(毫秒)ms");
    }
}
