package com.wxw.service.impl;

import com.wxw.manager.distributed_lock.zk_distributed_lock.ZKDistributeImproveLock;
import com.wxw.common.tools.OrderNumGenerator;
import com.wxw.service.OrderService;

import java.util.concurrent.locks.Lock;

/**
 * @author: wxw
 * @date: 2020-11-28-21:35
 */
public class OrderServiceImplWithZkDisLock implements OrderService {

    // 用static 修饰，模拟共用一个订单号
    private static OrderNumGenerator ong = new OrderNumGenerator();

//    private Lock lock = new ReentrantLock();

    // 创建订单接口
    @Override
    public void createOrder() {
        // 获取订单号
        String orderNum = null;
//        Lock lock = new ZKDistributeLock("/BigTree11111");  // 临时节点  有惊群效应
        Lock lock = new ZKDistributeImproveLock("/BigTree22222");  // 临时顺序节点
        try {
            lock.lock();
            orderNum = ong.getOrderNum();
        } finally {
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName()+":  orderNum = " + orderNum);
        // 业务代码 此处省略100行
    }
}
