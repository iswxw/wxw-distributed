package com.wxw.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @contract: 公众号：Java半颗糖
 * @desc: 有状态bean,有state,user等属性，并且user有存偖功能，是可变的。
 * @link:
 */
@Getter
@Setter
public class StatefulBean {
    public int state;
    // 由于多线程环境下，user是引用对象，是非线程安全的
    public TestParam param;
}
