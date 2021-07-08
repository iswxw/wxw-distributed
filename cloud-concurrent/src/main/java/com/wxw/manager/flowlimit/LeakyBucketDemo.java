package com.wxw.manager.flowlimit;

/**
 * @ Author ：wxw.
 * @ Date ： 14:00 2020/6/29
 * @ Description：基于漏桶算法实现的 滑动窗口
 * @ Version:   v_0.0.1
 */
public class LeakyBucketDemo {

    // 时间刻度
    private static long time = System.currentTimeMillis();
    // 桶里面现在的水
    public static int water = 0;
    // 桶的大小
    public static int size = 10;
    // 出水速率
    private static int rate = 3;

    private static boolean grant() {
        // 计算出水的数量
        long now = System.currentTimeMillis();
        //先执行漏水，因为rate是固定的，所以可以认为“(时间间隔*rate)/700”即为漏出的水量
        int out = (int) ((now - time) / 700 * rate);
        water = Math.max(0, water - out);
        time = now;
        if ((water + 1) < size) {
            ++water;
            return true;
        } else {
            return false;
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {
                public void run() {
                    if (grant()){
                        System.out.println(" 执行业务逻辑...");
                    }else {
                        System.out.println(" 限流中...");
                    }
                }
            }).start();
        }
    }

}


/**
 * 滑动窗口：
 *   把固定时间片，进行划分，并且随着时间的流逝，进行移动，这样就巧妙的避免了计数器临界点的问题
 *   也就是说：这些固定数量的可以移动的格子，将会进行计数判断阈值，因此格子的数量影响着滑动窗口算法的精度；
 *
 * 漏桶算法：
 *   虽然滑动窗口有效的避免了时间临界点的问题，但是依然有时间片的概念，而漏桶算法在这方面比滑动窗口而言，更先进
 *   理解为：有一个固定的值，进水的速率是不确定的，但是出水的速率是恒定的，当水满的时候是会溢出的
 *   网络图：https://www.processon.com/mindmap/5eeb31f4e0b34d4dba454dcc
 * */