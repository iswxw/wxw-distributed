package com.wxw.manager.distributed_lock.redission_annotation_lock.redission;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Mapping https://segmentfault.com/a/1190000037526833
 * @ Author ：wxw.
 * @ Date ： 11:24 2020/9/28
 * @ Description： 分布式锁注解化
 * @ Version:   v_0.0.1
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiLock {

    /**
     * @AliasFor 用法
     * 用到注解 属性上，表示两个属性互相为别名，互相为别名的属性值必须相同，若设置成不同，则会报错
     * - 锁的资源，key。支持spring El表达式
     */
    @AliasFor("key")
    String value() default "";

    @AliasFor("value")
    String key() default "";

    /**
     * 锁类型
     */
    ApiLockType lockType() default ApiLockType.REENTRANT_LOCK;

    /**
     * 获取锁等待时间，默认0秒，0是马上尝试获取，大于0是等待获取，并在配置的释放时间释放锁，-1是永远等待获取锁
     */
    long waitTime() default 0;

    /**
     * 锁自动释放时间，默认-1，永远不释放，等待unlock
     */
    long leaseTime() default -1;

    /**
     * 时间单位，默认毫秒（获取锁等待时间和锁自动释放时间都用此单位）
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;
}
