package com.liyuxi.mycode.threads.blockqueues;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 不存储元素的阻塞队列，队列中只能有一个元素，多了存不进去，少了也取不到
 * 取不到的话会一直等待其他线程赛一个进去再取
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQeque = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t put 1");
                blockingQeque.put("1");
                System.out.println(Thread.currentThread().getName()+"\t put 2");
                blockingQeque.put("2");
                System.out.println(Thread.currentThread().getName()+"\t put 3");
                blockingQeque.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AA").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t take " + blockingQeque.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t take " + blockingQeque.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\t take " + blockingQeque.take());
                /*blockingQeque.take();*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();
    }
}
