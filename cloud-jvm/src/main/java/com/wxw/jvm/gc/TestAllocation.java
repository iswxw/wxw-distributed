package com.wxw.jvm.gc;

/**
 * @author ：wxw.
 * @date ： 12:19 2020/12/27
 * @description：对象内存分配
 * @link:
 * @version: v_0.0.1
 */
public class TestAllocation {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        // testAllocation();
        // testPretenuresizeThreshold();
        // testTenuringThreshold_1();
        // testTenuringThreshold_2();
        testHandlePromotion();
    }

    /**
     * 触发 Minor cc
     * VM参数﹔-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; //出现一次Minor cc
    }

    /**
     * 触发 超过指定内存空间 的大对象 Major Gc  直接进入老年代
     * VM参数:-verbose:gc -xms20M -Xmx20M -Xmn10M -XX :SurvivorRatio=8
     * -XX:PretenuresizeThreshold=3145728
     * 使用ParNew收集器：
     * -XX:
     * - PretenuresizeThreshold=3145728 B = 3MB
     * - PretenureSizeThreshold参数只对Serial和ParNew两款收集器有效，
     * ParallelScavenge收集器不认识这个参数，Parallel Scavenge 收集器一般并不需要设置。
     * 如果遇到必须使用此参数的场合，可以考虑ParNew 加CMS的收集器组合。
     */
    public static void testPretenuresizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB]; // 直接分配在老年代中
    }

    /**
     * GC 分代年龄阈值 MaxTenuringThreshold
     * VM参数:-verbose:gc -Xms20N -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
     * -XX:MaxTenuringThreshold=1
     * -XX:+PrintTenuringDistribution
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold_1() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4]; // 256KB  大小 survivor可以容量 并不会GC
        // 什么时候进入老年代取决于XX:MaxTenuringThreshold设置
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * survivor区相同年龄对象的占用内存 大于 survivor 空间的一半，则直接进入老年代
     * -verbose:gc
     * -Xms20M
     * -Xmx20M
     * -Xmn10M
     * -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=15
     * -XX:+UseSerialGC
     * -XX:+PrintTenuringDistribution
     * -XX:+PrintGCDetails
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold_2() {
        byte[] allocation1, allocation2, allocation3,allocation4;
        allocation1 = new byte[_1MB / 4];
        // allocation1 + allocation2 > survivor 空间的一半，则直接进入老年代
        allocation2 = new byte[_1MB / 4];
        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }


    /**
     * VM参数:
     *  -verbose:gc -xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8
     *  -XX:-HandlePromotionFailure
     */
    @SuppressWarnings ("unused")
    public static void testHandlePromotion () {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5,
                allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }
}