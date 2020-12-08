package com.wxw.juc.juc_volatile;

/**
 * synchronized 关键字保证线程安全
 * @author: wxw
 * @date: 2020-11-21-12:39
 */
public class ClassA {
    static int count = 0;
    // 锁对象
    public synchronized void do1(){
        count++;
    }
    // 锁类
    public static synchronized void do2(){
        count++;
    }
    // 锁对象
    public  void do3(){
       synchronized (this){
           count++;
       }
    }
    public static void main(String[] args) {
        ClassA a = new ClassA();
        a.do1();
        ClassA b = new ClassA();
        b.do1();
    }
}
