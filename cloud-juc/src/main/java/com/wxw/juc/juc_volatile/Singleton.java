package com.wxw.juc.juc_volatile;

/**
 * 单例模式
 *
 * @author: wxw
 * @date: 2020-11-21-21:48
 */
public class Singleton {

    private static volatile Singleton singleton = null;
    private Singleton() { }
    // Spring 中单例是如何保证的 DCL 双重检测
    public Singleton getSingleton() {
        // 第一次检测
        if (singleton == null) {
            // 创建实例
            synchronized (Singleton.class) {
                // 第二次检测
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
