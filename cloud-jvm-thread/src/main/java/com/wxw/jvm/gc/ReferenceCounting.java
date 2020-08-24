package com.wxw.jvm.gc;

/**
 * @Author: wxw
 * @create: 2020-08-24-23:37
 */
public class ReferenceCounting {
    /**
     * testGC() 方法执行后，A 和 B 会不会被回收？
     */
    public Object instance = null;

    private static final int _1MB = 1024 * 1024;
    /**
     * 这个成员属性的唯一意义就是占点内存，以便能在GC日志中看清楚是否被回收过
     */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        testGC();
    }
    public static void testGC() {
        ReferenceCounting A = new ReferenceCounting();
        ReferenceCounting B = new ReferenceCounting();

        A.instance = B;
        B.instance = A;

        A = null;
        B = null;

        // 假设在这行发生GC,A和 B 是否能被回收？
        System.gc();
    }
}
