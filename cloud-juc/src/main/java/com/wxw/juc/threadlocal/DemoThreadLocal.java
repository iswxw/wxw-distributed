package com.wxw.juc.threadlocal;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/15
 */
public class DemoThreadLocal {

    /**
     * 建议使用 static 静态变量修饰
     * 也就是每个线程Thread 都存在一个 ThreadLocalMap 用来维护线程信息 也就是value,而ThreadLocal通过
     * 弱引用的方式维护对应的key,这样
     * - 如果存在GC时，首先 弱引用的key会被回收，而value 强引用一直无法被回收，可能导致 内存泄漏，所以设置为static 时可以
     * 使得类被第一次使用时装载，只分配一块存储空间，所有此类的对象都可以操作这个变量
     */
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

    }
}
