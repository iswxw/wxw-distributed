package com.wxw.service.impl;

import cn.hutool.core.util.IdUtil;
import com.wxw.common.result.Result;
import com.wxw.common.manager.tools.DateTools;
import com.wxw.delay_queue.rabbitmq_delay_quaue.DeadLetterQueueSender;
import com.wxw.domain.Order;
import com.wxw.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author ：wxw.
 * @date ： 10:45 2020/12/14
 * @description：订单实现类
 * @link:
 * @version: v_0.0.1
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private DeadLetterQueueSender deadLetterQueueSender;

    @Override
    public void submitOrder() {
        /**
         * 下单时间，如果当前时间超过下单时间30分钟未支付 则发送短信提醒将自动取消订单
         */
        Date submitOrder = DateTools.string2DateTime("2020-07-22 13:00:00");

        /**
         *  取消订单时间：下单30分钟后 检测如果未支付，则自动取消订单，生成订单60s后，给客户发短信
         */
        Date executeTime = DateUtils.addMinutes(submitOrder, 30);

        /**
         * 存入数据库
         * 此处省略 100000万行代码
         */
    }

    @Override
    public Result generateOrder(Order order) {
        // todo 执行一些列下单操作
        log.info("生成订单的过程...");
        // 生成订单号
        String orderNumber = IdUtil.objectId();
        /**
         * todo 下单完成后
         *  1。开启一个延迟消息，用于当用户没有付款时取消订单（orderId在下单后生成）
         */
        sendDelayMessageCancelOrder(orderNumber);
        return Result.success(orderNumber, "下单成功");
    }

    @Override
    public void cancelOrder(String orderId) {
        // todo 执行一系列取消订单操作
        log.info("订单取消中... 订单号：{}", orderId);
    }

    private void sendDelayMessageCancelOrder(String orderId) {
        //获取订单超时时间 假设为60分钟
        long delayTimes = 30 * 1000;
        //发送延迟消息
        deadLetterQueueSender.sendMessage(orderId, delayTimes);
    }
}
