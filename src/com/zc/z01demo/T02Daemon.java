package com.zc.z01demo;

import java.util.concurrent.TimeUnit;

/**
 * 所谓 后台(deemon)线程, 是指在程序运行的时候 在 后台提供一种通用服务的线程.且这种线程并不属于程序中不可或缺的一部分.
 * 即当 所有 非后台线程结束时,程序也就终止了.同时会杀死进程中所有的 后台线程.
 * 只要有 任何 非后台线程 还在运行,程序就不会终止.如main()就是一个 非后台线程.
 * @author flyingzc
 * 
 */
public class T02Daemon implements Runnable
{
    public void run()
    {
        try
        {
            while (true)
            {
                TimeUnit.MILLISECONDS.sleep(100);
                // isDaemon()判断是否是后台线程
                System.out.println(Thread.currentThread() + " " + this +  Thread.currentThread().isDaemon());
                // 使用daemon线程创建的 任何线程,默认也会被设置为后台线程
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("sleep() interrupted");
        }
    }

    public static void main(String[] args) throws Exception
    {
        for (int i = 0; i < 10; i++)
        {
            Thread daemon = new Thread(new T02Daemon());
            daemon.setDaemon(true); // Must call before start()
            daemon.start();
        }
        System.out.println("All daemons started");
        // 调整这个时间,就能看到后台线程在工作打的日志
        TimeUnit.MILLISECONDS.sleep(175);
        // 一旦main()方法完成工作.就没有其他 非后台线程了. 所以所有后台线程被终止
    }
} /* Output: (Sample)
  All daemons started
  Thread[Thread-0,5,main] SimpleDaemons@530daa
  Thread[Thread-1,5,main] SimpleDaemons@a62fc3
  Thread[Thread-2,5,main] SimpleDaemons@89ae9e
  Thread[Thread-3,5,main] SimpleDaemons@1270b73
  Thread[Thread-4,5,main] SimpleDaemons@60aeb0
  Thread[Thread-5,5,main] SimpleDaemons@16caf43
  Thread[Thread-6,5,main] SimpleDaemons@66848c
  Thread[Thread-7,5,main] SimpleDaemons@8813f2
  Thread[Thread-8,5,main] SimpleDaemons@1d58aae
  Thread[Thread-9,5,main] SimpleDaemons@83cc67
  ...
  *///:~
