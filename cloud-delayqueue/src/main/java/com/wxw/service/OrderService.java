package com.wxw.service;

import com.wxw.common.result.Result;
import com.wxw.domain.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：wxw.
 * @date ： 10:44 2020/12/14
 * @description：订单接口
 * @link:
 * @version: v_0.0.1
 */
public interface OrderService {

    /**
     * 提交订单，存入数据库
     */
    void submitOrder();

    /**
     * 生成订单
     * @param order
     * @return
     */
    @Transactional
    Result generateOrder(Order order);

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(String orderId);
}
