package com.zc.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// Semaphore可以维护当前访问自身的	线程个数	即当前资源可允许多个线程共同访问,只有超过上限,才不允许进入
// 适用于对特定资源需要控制能够并发访问资源的线程个数。
// 需要先执行acquire方法获取许可证，如果获取成功后线程才能往下继续执行，否则只能阻塞等待；
// 使用完后需要用release方法归还许可
public class T11Semaphore
{
    public static void main(String[] args)
    {
        ExecutorService pool = Executors.newCachedThreadPool();
        final Semaphore sp = new Semaphore(3);// 最多有3个线程同时获取资源
        for (int i = 0; i < 10; i++)
        {
            Runnable runnable = new Runnable()
            {
                public void run()
                {
                    try
                    {
                        sp.acquire();//获取许可证	
                    }
                    catch (InterruptedException e1)
                    {
                        e1.printStackTrace();
                    }
                    // 3-可获得的许可,得到剩余的可用的
                    System.out.println("线程" + Thread.currentThread().getName() + "进入,当前共有" + (3 - sp.availablePermits())
                            + "个并发");
                    try
                    {
                        Thread.sleep((long) (Math.random() * 10000));
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() + "即将离开");
                    // 释放许可证
                    sp.release();
                    System.out.println("线程" + Thread.currentThread().getName() + "已经离开,当前共有"
                            + (3 - sp.availablePermits()) + "个并发");
                }
            };
            pool.execute(runnable);//执行线程池
        }
        pool.shutdown();
    }

}
