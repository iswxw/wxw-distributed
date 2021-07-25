package com.wxw.juc.spinning_lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author weixiaowei
 * @desc: 自旋锁
 * @date: 2021/7/19
 */
public class DemoSpinLock {
    private AtomicInteger serviceNum = new AtomicInteger();
    private AtomicInteger ticketNum = new AtomicInteger();
    private static final ThreadLocal<Integer> LOCAL = new ThreadLocal<Integer>();

    public void lock() {
        int myTicket = ticketNum.getAndIncrement();
        LOCAL.set(myTicket);
        while (myTicket != serviceNum.get()) {
        }
    }

    public void unlock() {
        int myTicket = LOCAL.get();
        serviceNum.compareAndSet(myTicket, myTicket + 1);
    }
}
