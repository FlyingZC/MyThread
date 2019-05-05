package com.zc.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class T07LinkedBlockingQueue {
    private static final BlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();

    public static void main(String[] args) {
        new Thread() {
            public void run() {
                for (int i = 0; i < 6; i++) {
                    try {
                        Thread.sleep(1000);
                        q.put(i);
                        System.out.println("正在放入 " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread() {
            public void run() {
                for (int i = 0; i < 6; i++) {
                    try {
                        System.out.println("正在取出 " + q.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
