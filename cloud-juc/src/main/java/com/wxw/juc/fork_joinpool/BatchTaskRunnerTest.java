package com.wxw.juc.fork_joinpool;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：wxw.
 * @date ： 10:04 2021/2/4
 * @description：
 * @link:
 * @version: v_0.0.1
 */
public class BatchTaskRunnerTest {
    public static void main(String[] args) {

        /**
         * 假设有5个任务(allTasks)，其中 taskPerThread 是指定每个线程处理的任务数
         */
        List<Integer> allTasks = Arrays.asList(1,2,3,4,5);
        int taskPerThread = 1;
        BatchTaskRunner.execute(allTasks, taskPerThread, tasks -> {
            System.out.printf("[%s]: %s\n", Thread.currentThread().getName(), tasks);
        });
    }
}
