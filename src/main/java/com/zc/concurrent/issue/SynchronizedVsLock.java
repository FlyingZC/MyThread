package com.zc.concurrent.issue;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedVsLock {

    private static int synValue;

    @Test
    public void test00() {
        // 使用synchronized所花费的时间为：434160722
        // 使用lock所花费的时间为：        299438691
        syncVsLock(10, 1);
    }

    @Test
    public void test01() {
        // 使用synchronized所花费的时间为：434160722
        // 使用lock所花费的时间为：        299438691
        syncVsLock(10, 1000000);
    }

    @Test
    public void test02() {
        // 使用synchronized所花费的时间为：434160722
        // 使用lock所花费的时间为：        299438691
        syncVsLock(100, 1000000);
    }

    private static void syncVsLock(int threadNum, int maxValue) {
        testSync(threadNum, maxValue);
        testLock(threadNum, maxValue);
    }

    // test lock
    public static void testLock(int threadNum, int maxValue) {
        Thread[] t = new Thread[threadNum];
        Long begin = System.nanoTime();
        for (int i = 0; i < threadNum; i++) {
            Lock locks = new ReentrantLock();
            synValue = 0;
            t[i] = new Thread(() -> {

                for (int j = 0; j < maxValue; j++) {
                    locks.lock();
                    try {
                        synValue++;
                    } finally {
                        locks.unlock();
                    }
                }

            });
        }
        for (int i = 0; i < threadNum; i++) {
            t[i].start();
        }
        //main线程等待前面开启的所有线程结束
        for (int i = 0; i < threadNum; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("使用lock所花费的时间为：" + (System.nanoTime() - begin));
    }

    // test synchronized
    public static void testSync(int threadNum, int maxValue) {
        int[] lock = new int[0];
        Long begin = System.nanoTime();
        Thread[] t = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            synValue = 0;
            t[i] = new Thread(() -> {
                for (int j = 0; j < maxValue; j++) {
                    synchronized (lock) {
                        ++synValue;
                    }
                }
            });
        }
        for (int i = 0; i < threadNum; i++) {
            t[i].start();
        }
        //main线程等待前面开启的所有线程结束
        for (int i = 0; i < threadNum; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("使用synchronized所花费的时间为：" + (System.nanoTime() - begin));
    }

}
