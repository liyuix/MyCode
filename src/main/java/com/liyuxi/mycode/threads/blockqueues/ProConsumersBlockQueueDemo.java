package com.liyuxi.mycode.threads.blockqueues;

import com.sun.deploy.util.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource{
    /**
     * 默认开启  进行生产消费的交互
     */
    private volatile  boolean flag = true;

    private volatile AtomicInteger atomicInteger = new AtomicInteger();

    private BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void myProd() throws InterruptedException{
        String data = null;
        boolean returnValue;
        while(flag){
            data = atomicInteger.incrementAndGet() + "";

            returnValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);

            if(returnValue){
                System.out.println(Thread.currentThread().getName() + "\t 插入队列数据" + data + "成功");
            }else{
                System.out.println(Thread.currentThread().getName() + "\t 插入队列数据" + data + "失败");

            }

            TimeUnit.SECONDS.sleep(1);

        }
        System.out.println(Thread.currentThread().getName() + "\t 停止表示flag:" + flag);
    }

    public void myConsumer()throws Exception{
        String result = null;
        while(flag){
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);

            if(result == null || "".equals(result)){
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t" + "超过2s没有取到 消费退出");
            }
            System.out.println(Thread.currentThread().getName() + "消费队列" + result +"成功");

            TimeUnit.SECONDS.sleep(1);
        }
    }

    public void stop() throws Exception{
        flag = false;
    }
}

public class ProConsumersBlockQueueDemo {
    public static void main(String[] args) throws Exception {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t 生产着启动");
            try {
                myResource.myProd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Producer").start();


        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t 消费者启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }

        },"Consumer").start();

        try{
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("时间到，停止活动");
        myResource.stop();
    }

}
