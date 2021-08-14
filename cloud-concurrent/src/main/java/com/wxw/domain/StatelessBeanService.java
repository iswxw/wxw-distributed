package com.wxw.domain;

import com.wxw.service.OrderService;

import java.util.List;

/**
 * @contract: 公众号：Java半颗糖
 * @desc: 无状态bean,不能存偖数据。因为没有任何属性，所以是不可变的。只有一系统的方法操作
 * @link:
 */
public class StatelessBeanService {

    // 虽然有 orderService 属性，但borderService 是没有状态信息的，是Stateless Bean.
    OrderService orderService;

    public List<TestParam> findUser(String Id) {
        return null;
    }
}
