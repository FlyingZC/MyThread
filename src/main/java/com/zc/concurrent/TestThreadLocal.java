package com.zc.concurrent;

public class TestThreadLocal {
    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    public static void main(String args[]) {
        // 1.在main线程中向 threadLocal中设置值
        threadLocal.set(new Integer(666));

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 2.其他线程里获取不到 main线程里设置的 threadLocal值
                System.out.println("InnerThread = " + threadLocal.get());
            }
        }).start();

        Thread thread = new MyThread();
        thread.start();

        // 4.只能在同一个线程中获取到设置到 threadLocal中的值
        System.out.println("main = " + threadLocal.get());


    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            // 3.其他线程里获取不到 main线程里设置的 threadLocal值
            System.out.println("MyThread = " + threadLocal.get());
        }
    }
}
