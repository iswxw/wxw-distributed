package com.wxw.manager.pools;


/**
 * 开启线程处理任务
 */
public class RunnableTask1 implements Runnable{
    @Override
    public void run(){
        synchronized (this){
            try {
                for (int i = 1;i <= 100;i++){
                    Thread.sleep( 30000);
                    System.out.println(Thread.currentThread().getName()+"----------异步：>"+i);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}