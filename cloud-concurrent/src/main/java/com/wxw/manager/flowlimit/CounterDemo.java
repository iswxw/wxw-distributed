package com.wxw.manager.flowlimit;

/**
 * @ Author ：wxw.
 * @ Date ： 13:40 2020/6/29
 * @ Description：技术器方法实现
 * @ Version:   v_0.0.1
 */
public class CounterDemo {

    private static Long timeStamp = System.currentTimeMillis();
    // 限制为 1s 内限制在100个请求
    private static long limitCount = 100;
    private static long interval = 1000;
    // 实际请求数
    private static long reqCount = 0;

    private static boolean grant() {
        long now = System.currentTimeMillis();
        if (now<timeStamp+interval){
            if (reqCount<limitCount){
                ++reqCount;
                return true;
            }else {
                return false;
            }
        }else {
            timeStamp = System.currentTimeMillis();
            reqCount = 0;
            return false;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                @Override
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
 * 计数器：
 * 在一段时间内，进行计数，与阈值进行比较，到了时间临界点将计数器清零
 *
 * 存在问题：临界点可能会承受恶意用户的大量请求，甚至超出系统预期的承受
 * 比如：
 * 在12:01:00到12:01:58这段时间内没有用户请求，然后在12:01:59这一瞬时发出100个请求，OK，然后在12:02:00这一瞬时又发出了100个请求。
 * 这里你应该能感受到，在这个临界点可能会承受恶意用户的大量请求，甚至超出系统预期的承受。
 */
