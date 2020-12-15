package com.wxw.controller;

import com.wxw.common.result.Result;
import com.wxw.domain.Order;
import com.wxw.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 9:17 2020/12/15
 * @description：RabbitMQ消息队列处理
 * @link:
 * @version: v_0.0.1
 */
@RestController
public class RabbitMQController {

    @Resource
    private OrderService orderService;

    @PostMapping("/generateOrder")
    @ResponseBody
    public Result generateOrder(@RequestBody Order order) {
        return orderService.generateOrder(order);
    }
}
