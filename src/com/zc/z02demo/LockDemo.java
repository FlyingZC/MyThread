package com.zc.z02demo;

import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    // 实例代码中开启了5个线程，先获取锁之后再睡眠10S中，实际上这里让线程睡眠是想 模拟出当线程无法获取锁时进入同步队列的情况
                    lock.lock();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            });
            thread.start();
        }
    }
}