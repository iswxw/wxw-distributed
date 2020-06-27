package com.wxw.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ Author ：wxw.
 * @ Date ： 16:52 2020/6/27
 * @ Description：使用FutureTask实现创建多线程
 * @ Version:   v_0.0.1
 */
public class Thread_Cast03 {
    public static void main(String[] args) {
        // 创建异步任务
        FutureTask<String> funtureTask = new FutureTask<String>(new CallerTask());
        // 启动线程
        new Thread(funtureTask).start();

        try {
            // 等待任务执行完毕，并返回结果
            String task = funtureTask.get();
            System.out.println("task = " + task);
        } catch (InterruptedException e) {
            System.out.println("中断异常 = " + e);
        } catch (ExecutionException e) {
            System.out.println("执行异常 = " + e);
        }
    }
}

class CallerTask implements Callable<String>{
    public String call() throws Exception {
        return "我是线程执行的返回值";
    }
}