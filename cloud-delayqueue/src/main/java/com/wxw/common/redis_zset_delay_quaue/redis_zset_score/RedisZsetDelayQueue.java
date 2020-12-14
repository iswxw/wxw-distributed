package com.wxw.common.redis_zset_delay_quaue.redis_zset_score;

import com.wxw.common.config.JedisClient;
import com.wxw.common.tools.DateTools;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Calendar;
import java.util.Set;

/**
 * @author ：wxw.
 * @date ： 15:56 2020/12/14
 * @description：基于Zset实现延迟队列  消费消息
 * @link: https://www.runoob.com/redis/redis-sorted-sets.html
 * @version: v_0.0.1
 */
public class RedisZsetDelayQueue {

    private static String DELAY_QUEUE = "delay_queue";

    public static void main(String[] args) {
        RedisZsetDelayQueue redisDelay = new RedisZsetDelayQueue();
        redisDelay.pushOrderQueue();
        redisDelay.pollOrderQueue();
        redisDelay.deleteZSet();
    }

    /**
     * 消息入队
     */
    public void pushOrderQueue() {
        Jedis jedis = JedisClient.JedisClient();
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.SECOND, 10);
        int order1 = (int) (cal1.getTimeInMillis() / 1000); // 10s 后的时间戳

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.SECOND, 20);
        int order2 = (int) (cal2.getTimeInMillis() / 1000); // 20s 后的时间戳

        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.SECOND, 30);
        int order3 = (int) (cal3.getTimeInMillis() / 1000); // 30s 后的时间戳

        /**
         * String key, double score, String member
         * key = DELAY_QUEUE
         * order1 = 权重
         * member = order1
         */
        jedis.zadd(DELAY_QUEUE, order1, "order1");
        jedis.zadd(DELAY_QUEUE, order2, "order2");
        jedis.zadd(DELAY_QUEUE, order3, "order3");
        System.out.println(DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()) + " add finished.");
    }

    /**
     * 删除队列
     */
    public void deleteZSet() {
        Jedis jedis = JedisClient.JedisClient();
        jedis.del(DELAY_QUEUE);
    }

    /**
     * 消费消息
     */
    public void pollOrderQueue() {

        Jedis jedis = JedisClient.JedisClient();
        while (true) {
            Set<Tuple> set = jedis.zrangeWithScores(DELAY_QUEUE, 0, 0);
            String value = ((Tuple) set.toArray()[0]).getElement();
            int score = (int) ((Tuple) set.toArray()[0]).getScore();

            Calendar cal = Calendar.getInstance();
            int nowSecond = (int) (cal.getTimeInMillis() / 1000);

            if (nowSecond >= score) {
                // 移除有序集合中的一个或多个成员
                jedis.zrem(DELAY_QUEUE, value);
                System.out.println(DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()) + " removed key:" + value);
            }
            // 获取有序集合的成员数
            if (jedis.zcard(DELAY_QUEUE) <= 0) {
                System.out.println(DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()) + " zset empty ");
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
