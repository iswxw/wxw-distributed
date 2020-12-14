package com.wxw.service.impl;

import com.wxw.common.tools.DateTools;
import com.wxw.service.OrderService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：wxw.
 * @date ： 10:45 2020/12/14
 * @description：订单实现类
 * @link:
 * @version: v_0.0.1
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public void submitOrder() {
        /**
         * 下单时间，如果当前时间超过下单时间30分钟未支付 则发送短信提醒将自动取消订单
         */
        Date submitOrder = DateTools.string2DateTime("2020-07-22 13:00:00");

        /**
         *  取消订单时间：下单30分钟后 检测如果未支付，则自动取消订单，生成订单60s后，给客户发短信
         */
        Date executeTime = DateUtils.addMinutes(submitOrder,30);

        /**
         * 存入数据库
         * 此处省略 100000万行代码
         */
    }
}
