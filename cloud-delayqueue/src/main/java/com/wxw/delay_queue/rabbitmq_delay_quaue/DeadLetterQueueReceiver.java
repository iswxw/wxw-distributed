package com.wxw.delay_queue.rabbitmq_delay_quaue;

import com.wxw.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 10:46 2020/12/15
 * @description：死信队列接收者 用于监听死信队列里面的消息, 用于从取消订单的消息队列（wxw.order.cancel）里接收消息。
 * @link:
 * @version: v_0.0.1
 */
@Component
@Slf4j
@RabbitListener(queues = "wxw.order.cancel")
public class DeadLetterQueueReceiver {

    @Resource
    private OrderService orderService;

    @RabbitHandler
    public void receiver(String OrderNumber) {
        log.info("收到延迟消息：" + OrderNumber);
        /**
         * todo 收到消息，说明订单超过 30 分钟
         *   1. 检查数据库，订单状态是否为已支付
         *   2. 根据支付状态，决定是否取消订单
         */
        // 客户是否支付
        boolean isPay = false;
        if (isPay){
            /**
             * todo 如果没有支付，则取消订单
             */
            orderService.cancelOrder(OrderNumber);
        }

    }
}
