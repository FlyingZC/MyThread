package com.zc.concurrent.issue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class LockPerformanceTest {

    static int threadCount = 8;

    public static void main(String[] args) throws InterruptedException {
        testReentrantLock();

        Thread.sleep(1000);

        testSynchronized();

    }

    private static void testSynchronized() {
        List<Thread> threads = new ArrayList<>();
        AtomicBoolean breakTag = new AtomicBoolean(false);
        HashSet<String> set = new HashSet<String>();
        final AtomicLong counter = new AtomicLong();
        String val = "1";
        for (int i = 0; i < threadCount; i++) {
            Thread t = new Thread() {
                public void run() {
                    this.setPriority(NORM_PRIORITY);
                    while (!breakTag.get()) {
                        synchronized (set) {
                            set.contains(val);
                        }
                        counter.incrementAndGet();
                    }
                }
            };
            threads.add(t);
            t.start();

        }

        Thread t = new Thread() {
            public void run() {
                this.setPriority(NORM_PRIORITY);
                long lastCount = counter.get();
                int loop = 0;
                long total = 0;
                while (true) {
                    long tempCount = counter.get() - lastCount;
                    total += tempCount;

                    lastCount = counter.get();
                    loop++;
                    if (loop >= 5) {
                        System.out
                                .println("Synchronized平均处理个数：" + total / loop);
                        breakTag.set(true);
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        threads.add(t);
        t.start();
        for (Thread th : threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void testReentrantLock() {
        List<Thread> threads = new ArrayList<>();
        AtomicBoolean breakTag = new AtomicBoolean(false);
        HashSet<String> set = new HashSet<String>();
        final AtomicLong counter = new AtomicLong();
        ReentrantLock lock = new ReentrantLock();
        String val = "1";
        for (int i = 0; i < threadCount; i++) {
            Thread t = new Thread() {
                public void run() {
                    this.setPriority(NORM_PRIORITY);
                    while (!breakTag.get()) {
                        lock.lock();
                        try {
                            set.contains(val);
                        } finally {
                            lock.unlock();
                        }
                        counter.incrementAndGet();
                    }
                }
            };
            threads.add(t);
            t.start();
        }

        Thread t = new Thread() {
            public void run() {
                this.setPriority(NORM_PRIORITY);
                long lastCount = counter.get();
                int loop = 0;
                long total = 0;
                while (true) {
                    long tempCount = counter.get() - lastCount;
                    total += tempCount;

                    lastCount = counter.get();
                    loop++;
                    if (loop >= 5) {
                        System.out.println("ReentrantLock平均处理个数：" + total
                                / loop);
                        breakTag.set(true);
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        threads.add(t);
        t.start();
        for (Thread th : threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}