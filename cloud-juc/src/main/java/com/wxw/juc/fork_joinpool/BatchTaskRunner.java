package com.wxw.juc.fork_joinpool;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

/**
 * @author ：wxw.
 * @date ： 9:53 2021/2/4
 * @description：用 Java 的 ForkJoin 实现一个工具类，任务批量并发处理
 * @link: https://my.oschina.net/javayou/blog/4870860
 * @version: v_0.0.1
 */
public class BatchTaskRunner extends RecursiveAction {

    protected int threshold = 5; //每个线程处理的任务数
    protected List taskList;     // 任务列表
    Consumer<List> action;       // 消费一个数据


    /**
     * @param taskList      任务列表
     * @param threshold     每个线程处理的任务数
     */
    private BatchTaskRunner(List taskList, int threshold, Consumer action) {
        this.taskList = taskList;
        this.threshold = threshold;
        this.action = action;
    }

    /**
     * 多线程批量执行任务
     * @param taskList
     * @param threshold
     * @param action
     */
    public static <T> void execute(List<T> taskList, int threshold, Consumer<List<T>> action) {
        new BatchTaskRunner(taskList, threshold, action).invoke();
    }

    /**
     * 任务计算
     */
    @Override
    protected void compute() {
        if (taskList.size() <= threshold) {
            this.action.accept(taskList);
        }
        else {
            this.splitFromMiddle(taskList);
        }
    }

    /**
     * 任务中分
     *  Math.ceil()  向上取整
     *  Math.round() 四舍五入
     *  Math.floor() 向下取整
     * @param list
     */
    private void splitFromMiddle(List list) {
        int middle = (int)Math.ceil(list.size() / 2.0);
        List leftList = list.subList(0, middle);
        List RightList = list.subList(middle, list.size());
        BatchTaskRunner left = newInstance(leftList);
        BatchTaskRunner right = newInstance(RightList);
        ForkJoinTask.invokeAll(left, right);
    }

    private BatchTaskRunner newInstance(List taskList) {
        return new BatchTaskRunner(taskList, threshold, action);
    }

}
