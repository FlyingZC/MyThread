package com.zc.z01demo;

public class T22Volatile {

    private static volatile Integer volatileNum = 0;

    private static Integer commonNum = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10000];
        for (int i = 0; i < threads.length; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    volatileNum++;
                    commonNum++;
                    try {
                        Thread.sleep((long) Math.random());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

            threads[i] = thread;
        }
        // 等待所有线程执行完毕
        for (int i = 0; i < threads.length;i++) {
            threads[i].join();
        }

        // ++操作不是原子性的,用 volatile也没用
        System.out.println(volatileNum + ", " + commonNum);// 9806, 9997
    }
}
