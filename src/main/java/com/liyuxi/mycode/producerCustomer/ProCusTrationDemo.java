package com.liyuxi.mycode.producerCustomer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者：思路 线程 操作 资源类
 *
 * 资源类可以完成自己的生产，消费
 *
 * 线程就是直接调资源类去生产和消费
 */

/**
 * 资源类
 */
class ShareData{
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void  increment(){
        lock.lock();
        try{
            while(num != 0){
                // num不是0的时候，就等着不生产
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            // 生产完了就唤醒所有线程去消费
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void deIncrement(){
        lock.lock();
        try {
            while(num == 0) {
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

/**
 * 一个初始值为0的变量，两个线程交替操作 一个加1，一个减1,交替五次
 */
public class ProCusTrationDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(()->{
            for(int i = 0; i < 5; i++){
                shareData.increment();
            }
        },"AA").start();

        new Thread(()->{
            for(int i = 0; i < 5; i++){
                shareData.deIncrement();
            }
        },"BB").start();
    }
}
