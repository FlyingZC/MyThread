package com.zc.exercise;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T0803AllDown {
    private static volatile int globelCount = 0;
    public static void main(String[] args) throws InterruptedException {
        int count = 10;
        ExecutorService threadPool = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("didi");
                    try {
                        Thread.sleep(3000);
                        synchronized (Object.class) {
                            globelCount++;
                        }
                        System.out.println(globelCount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        while(true) {
            if (globelCount == 10) {
                break;
            }
            Thread.sleep(1000);
        }
        System.out.println("over");
    }
}
