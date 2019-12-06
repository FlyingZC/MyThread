package com.zc.z01demo;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程状态
 *
 * @see java.lang.Thread.State
 */
public class T23ThreadState {
    public static void main(String[] args) {
        Thread t1 = new Thread();
        // 1. 创建后尚未启动的线程
        System.out.println(t1.getState()); // NEW

        // 2. RUNNABLE 状态包括了操作系统线程状态中的 Running 和 Ready
        t1.start();
        System.out.println(t1.getState()); // RUNNABLE

        t1.yield();
        System.out.println(t1.getState()); // RUNNABLE
    }

    // blocked
    @Test
    public void test02() throws InterruptedException {
        // jmx 查看 com.intellij.rt.execution.junit.JUnitStarter (pid 6648)
        // thread-1 和 thread-2 中 有一个一直运行(绿色),一个一直 blocked(红色,监视)
        Object monitor = new Object();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                method1(monitor);
            }
        });
        t1.setName("running-thread-1");
        t1.start();

        // 让 t1 拿锁, t2 挂起
        Thread.sleep(200);

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                method1(monitor);
            }
        });
        t2.setName("blocked-thread-2");
        t2.start();

        // ============= 休眠,紫色
        // 一直 sleep
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t3.setName("sleep-thread");
        t3.start();

        // =========== 等待,黄色. 超时等待,黄色
        // wait
        Object monitor2 = new Object();
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (monitor2) {
                        monitor2.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t4.setName("wait-thread");
        t4.start();

        // join
        Thread joinThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        joinThread.setName("join-thread");
        joinThread.start();

        // TIMED_WAITING
        Object monitor3 = new Object();
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 锁当前对象
                    synchronized (monitor3) {
                        monitor3.wait(Integer.MAX_VALUE);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t5.setName("timed-wait-thread");
        t5.start();

        // ========== 驻留,橙色 ============
        // WAITING (parking)
        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
            }
        });
        t6.setName("parking-thread");
        t6.start();

        // TIMED_WAITING (parking)
        Thread t7 = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park(Integer.MAX_VALUE);
            }
        });
        t7.setName("timed-parking-thread");
        t7.start();

        while (true) {

        }
    }

    public void method1(Object monitor) {
        synchronized (monitor) {
            while (true) {

            }
        }
    }

}
