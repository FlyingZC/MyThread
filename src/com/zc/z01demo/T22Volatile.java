package com.zc.z01demo;

public class T22Volatile
{
    private static volatile Integer volatileNum = 0;

    private static Integer commonNum = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++)
        {
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
            thread.join();
        }
        System.out.println(volatileNum + ", " + commonNum);// 100000, 100000 有啥区别
    }
}
