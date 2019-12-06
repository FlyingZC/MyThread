package com.zc.concurrent.issue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 当持锁的是读线程时，跟随其后的是一个写线程，那么再后面来的读线程是无法获取读锁的，只有等待写线程执行完后，才能竞争。
 * 这是jdk为了避免写线程过分饥渴，而做出的策略。但有坑点就是，如果某一读线程执行时间过长，甚至陷入死循环，后续线程会无限期挂起，严重程度堪比死锁。
 * 为避免这种情况，除了确保读线程不会有问题外，尽量用tryLock，超时我们可以做出响应。
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
    }

    public ReadWriteLockTest() {
        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() throws InterruptedException {

        TestLock testLock = new TestLock();

        Thread read1 = new Thread(new ReadThread(testLock), "读线程 -- 1");
        read1.start();

        Thread.sleep(100);

        Thread write = new Thread(new WriteThread(testLock), "写线程 -- 1");
        write.start();

        Thread.sleep(100);

        Thread read2 = new Thread(new ReadThread(testLock), "读线程 -- 2");
        read2.start();
    }

    private class TestLock {

        private String string = null;

        private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        private Lock readLock = readWriteLock.readLock();

        private Lock writeLock = readWriteLock.writeLock();

        public void set(String s) {
            writeLock.lock();
            try {
//                writeLock.tryLock(10, TimeUnit.SECONDS);
                string = s;
            } finally {
                writeLock.unlock();
            }
        }

        public String getString() {
            readLock.lock();
            System.out.println(Thread.currentThread());
            try {
                while (true) {
                }
            } finally {
                readLock.unlock();
            }
        }
    }

    class WriteThread implements Runnable {
        private TestLock testLock;

        public WriteThread(TestLock testLock) {
            this.testLock = testLock;
        }

        @Override
        public void run() {
            testLock.set("can't get lock");
        }
    }

    class ReadThread implements Runnable {
        private TestLock testLock;

        public ReadThread(TestLock testLock) {
            this.testLock = testLock;
        }

        @Override
        public void run() {
            testLock.getString();
        }
    }
}