package com.wxw.jvm.monitoring;

import java.util.Map;

/**
 * @author ：wxw.
 * @date ： 17:17 2020/12/16
 * @description：JVM 堆栈跟踪工具
 * @link: https://www.cnblogs.com/huangjuncong/p/8995333.html
 * @version: v_0.0.1
 */
public class JStackDemo {
    public static void main(String[] args) {
        /**
         * 在JDK1.5中，java.lang.Thread类新增了一个getAllStackTraces()方法
         *  用于获取虚拟机中所有线程的StackTraceElement对象
         */
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> en : allStackTraces.entrySet()){
            Thread t = en.getKey();
            StackTraceElement[] v = en.getValue();
            System.out.println("The Thread name is :" + t.getName());
            for (StackTraceElement s : v){
                System.err.println("\t" + s.toString());
            }
        }
    }
}
