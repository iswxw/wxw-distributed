package com.wxw.common.flowlimit;

/**
 * @ Author ：wxw.
 * @ Date ： 15:07 2020/6/29
 * @ Description：令牌桶
 * @ Version:   v_0.0.1
 */

public class TokenBucketDemo {

    // 时间刻度
    private static long time = System.currentTimeMillis();
    // 生成令牌桶的速率
    public static int createTokenRate = 1;
    // 桶的大小
    public static int size = 10;
    // 当前令牌数量
    private static int tokens = 0;

    private static boolean grant() {
        // 计算出水的数量
        long now = System.currentTimeMillis();
        //在这段时间内需要生产的令牌数量
        int in = (int) ((now - time) / 50 * createTokenRate);
        tokens = Math.max(size,tokens+in);
        time = now;
        if (tokens > 0) {
            -- tokens;
            return true;
        } else {
            return false;
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
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
 * 漏桶的缺点：
 *   漏桶的出水速度是恒定的，那么如果瞬间大流量的话，将有大部分的请求被丢弃掉（溢出）
 * 令牌桶：
 *   生成令牌桶的速度是恒定的，而请求去拿令牌是没有速度限制的，对于瞬时大流量，该算法可以在短时间内请求拿到大量令牌
 *   而且拿令牌的过程并不会有很大消耗，（类似生产令牌，消耗令牌），无论是令牌桶拿不到令牌被拒绝，还是漏桶的水满了溢出
 *   都是为了保证大部分流量的正确使用，而牺牲掉了少部分流量，这是合理的，如果因为极少部分流量需要保证的话，那么久可能
 *   导致系统达到极佳而挂掉，得不偿失
 * */