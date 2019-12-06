package com.zc.exercise;

public class T0804AllDown {
    public static void main(String[] args) throws InterruptedException {
        int count = 10;
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("didi");
                    try {
                        Thread.sleep(3000);
                        System.out.println("dada");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            t.join();// 会变成串行的了,一个线程完才执行后面的另一个线程. 不可以
        }
        System.out.println("over");
    }
}
