package com.wxw.flowlimit;

import com.wxw.base.ThreadPoolTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.concurrent.Semaphore;

/**
 * @author ：wxw.
 * @date ： 13:53 2020/11/26
 * @description：基于Semaphore控制并发数量
 * @version: v_0.0.1
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class SemaphoreRateLimitTest extends ThreadPoolTest {

    @Test
    public void testData1() {
        Semaphore semaphore = new Semaphore(10);
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    semaphore.acquireUninterruptibly(1);
                    try {
                        System.out.println("开始做我的事情！！"+ semaphore.toString());
                    } finally {
                        semaphore.release();
                    }
                }
            },"result");
        }

    }
}
