package com.liyuxi.mycode.enumCommon;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch运用枚举类的Demo
 * 秦灭六国
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        sixCountry();
    }
    private static void sixCountry() throws InterruptedException{
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for(int i = 1; i <= 6; i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"国,灭");
                countDownLatch.countDown();
            },EnumDemo.forEach(i).getName()).start();
        }
        countDownLatch.await();
        System.out.println("秦统一");
    }
}
