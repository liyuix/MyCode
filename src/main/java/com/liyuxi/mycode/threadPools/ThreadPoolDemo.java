package com.liyuxi.mycode.threadPools;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    /**
     * 创建核心线程数为2，最大线程数为5，
     * 协助线程为3（5-2）,协助线程存活时间为1秒，
     * 利用默认的线程工厂来创建线程，拒绝策略为直接抛出异常的线程池
     * @param args
     */
    public static void main(String[] args) {
        int corePoolSize = 2;
        int maxPoolSize = 5;
        long keepAliveTime = 1L;
        ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3), Executors.defaultThreadFactory(),
                // 拒绝策略
                // CallerRunsPolicy:回退给调用者
                // DiscardPolicy:直接丢弃任务不处理，也不抛异常
                // AbortPolicy:直接抛出rejectedException异常阻止系统正常运行
                // DiscardOldestPolicy:抛弃等待时间最久的任务，然后把当前要执行的任务加入队列尝试再次提交
                new ThreadPoolExecutor.DiscardOldestPolicy());
        try{
            // 模拟10个用户来办业务
            for(int i = 1; i <= 10; i++){
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });

            }
        }catch (Exception e){
            e.printStackTrace();

        }finally{
            // 核心线程为设为0，或者设置threadPool.awaitTermination(1, TimeUnit.SECONDS);可自动关闭线程，不然需要调用才能关闭
            threadPool.shutdown();
        }

    }
}
