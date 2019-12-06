package com.zc.exercise;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T0802AllDown {
    public static void main(String[] args) throws InterruptedException {
        int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);
        ExecutorService threadPool = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("didi");
                    try {
                        Thread.sleep(3000);
                        System.out.println("dada");
                        latch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        latch.await();
        System.out.println("over");

    }
}
