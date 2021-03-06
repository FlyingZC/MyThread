package com.zc.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 两个线程 无规则获取锁并输出zccc或xddd
public class T01LockPrint
{
    public static void main(String[] args)
    {
        // 两个线程必须使用同一个Outputer对象,才能起到使用同一把锁的作用
        final Outputer printer = new Outputer();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    printer.output("zccccccccc");
                }
            }
        }).start();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    printer.output("xddddddddd");
                }
            }
        }).start();

    }

    static class Outputer
    {
        //创建锁对象	一个可重入的互斥锁 Lock
        Lock lock = new ReentrantLock();

        public void output(String name)
        {
            int len = name.length();
            lock.lock();// 加锁,放在try外面.不要放到try里,如果在获取锁（自定义锁的实现）时发生了异常，异常抛出的同时，也会导致锁无故释放
            try
            {
                System.out.print(Thread.currentThread().getName() + "输出: ");
                for (int i = 0; i < len; i++)
                {
                    // 若此时抛异常,不会释放锁.所以建议在finally里unlock()
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }
            finally
            {//可以没有catch块
                lock.unlock();//在finally中解锁
            }
        }
    }
}
