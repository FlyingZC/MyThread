package com.zc.z01demo;

// 实现Runnable接口.创建多线程
public class T02CreateThreadRunnable
{
    public static void main(String[] args)
    {
        MyRunnable myRunnable = new MyRunnable();
        // 要想启动一个多线程.必须调用start()方法
        // Thread类的构造方法中.有一个Thread(Runnable target)
        // new Thread(..)对象的start()方法启动的是myRunnable对象的run()方法.底层源码.Runnable()接口只有run()方法
        // Thread类implements Runnable
        new Thread(myRunnable).start();
        Thread t1 = new Thread(myRunnable);
        t1.start();
        Thread t2 = new Thread(myRunnable);
        t2.start();

    }
}

class MyRunnable implements Runnable
{
    @Override
    public void run()
    {
        for (int i = 1; i <= 100; i++)
        {
            if (i % 2 == 0)
            {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}
