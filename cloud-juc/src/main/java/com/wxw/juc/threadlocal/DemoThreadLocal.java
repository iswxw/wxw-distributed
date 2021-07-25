package com.wxw.juc.threadlocal;

import lombok.Data;

/**
 * @author weixiaowei
 * @desc:
 * @link: https://segmentfault.com/a/1190000023142547
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
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "hello world";
        }
    };
    private static ThreadLocal<User> threadLocalUser = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(new MyRunnable(1)).start();

        User user = new User();
        user.setName("Java半颗糖");
        user.setAge(25);
        threadLocalUser.set(user);	// 保存值
        System.out.println(threadLocalUser.get());	// 获取值
    }

    static class MyRunnable implements Runnable {

        private int num;

        public MyRunnable(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            threadLocal.set(String.valueOf(num));
            System.out.println("threadLocalValue:" + threadLocal.get());
            //手动移除
            threadLocal.remove();
        }
    }

    @Data
    static class User {
        String name;
        Integer age;
    }
}


