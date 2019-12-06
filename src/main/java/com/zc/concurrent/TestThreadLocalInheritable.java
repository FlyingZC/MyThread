package com.zc.concurrent;

public class TestThreadLocalInheritable {

    public static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<Integer>();

    public static void main(String args[]) {

        threadLocal.set(new Integer(666));

        Thread thread = new MyThread();
        thread.start();

        System.out.println("main = " + threadLocal.get());
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread = " + threadLocal.get());
        }
    }
}
