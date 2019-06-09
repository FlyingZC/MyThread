package com.zc.concurrent;

public class T00ThreadGroup {

    private static ThreadGroup workerThreads = new MyThreadGroup("Worker Threads");

    public static void main(String[] args) {
        WorkerThread t = new WorkerThread("thread1");
        t.start();
    }

    // ThreadGroup
    static class MyThreadGroup extends ThreadGroup {
        public MyThreadGroup(String s) {
            super(s);
        }
        // 处理线程中未捕获的异常
        public void uncaughtException(Thread thread, Throwable throwable) {
            System.out.println("Thread " + thread.getName() + " died, exception was: ");
            throwable.printStackTrace();
        }
    }

    public static class WorkerThread extends Thread {
        public WorkerThread(String s) {
            // 使用线程组
            super(workerThreads, s);
        }

        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}

