package com.wxw.juc.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: wxw
 * @date: 2021-02-06-23:05
 * @link:
 * @description:
 */
public class WithMultiThread {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try {
            long startTime = System.currentTimeMillis();
            List<Callable<String>> callableList = new ArrayList<>();
            // 第一个RPC接口，调用花费100ms
            Callable<String> callableA = ()->{Thread.sleep(100L);return "A";};
            callableList.add(callableA);

            // 第二个RPC接口，调用花费200ms
            Callable<String> callableB = ()->{Thread.sleep(200L);return "B";};
            callableList.add(callableB);

            // 第二个RPC接口，调用花费150ms
            Callable<String> callableC = ()->{Thread.sleep(150L);return "C";};
            callableList.add(callableC);

            // 第二个RPC接口，调用花费300ms
            Callable<String> callableD = ()->{Thread.sleep(300L);return "D";};
            callableList.add(callableD);

            List<Future<String>> futures = executorService.invokeAll(callableList);
            StringBuilder stringBuilder = new StringBuilder();
            for (Future<String> future : futures) {
                stringBuilder.append(future.get());
            }
            long endTime = System.currentTimeMillis();
            System.out.println(
                    "四个接口调用完成，组合返回结果：" + stringBuilder.toString() +
                    "，总耗时："+ (endTime-startTime) +"(毫秒)ms");
        } finally {
            executorService.shutdown();
        }

    }
}
