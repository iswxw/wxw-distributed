package com.wxw.delay_queue.rabbitmq_delay_quaue;

import com.wxw.common.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ：wxw. 取消订单的消息发出者
 * @date ： 10:45 2020/12/15
 * @description：用于向订单延迟消息队列（wxw.order.cancel.ttl）里发送消息。
 * @link:
 * @version: v_0.0.1
 */
@Component
@Slf4j
public class DeadLetterQueueSender {

    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String orderId, final Long delayTimes) {
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(),
                QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId,
                new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //给消息设置延迟毫秒值
                        message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                        return message;
                    }
                });
        log.info("send delay message orderId:{}", orderId);
    }
}
