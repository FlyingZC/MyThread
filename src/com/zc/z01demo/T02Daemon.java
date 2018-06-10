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
                System.out.println(Thread.currentThread().getName() + ",是后台进行:"  +  Thread.currentThread().isDaemon() + ",是否存活" + Thread.currentThread().isAlive());
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
        // 启动10个后台线程
        for (int i = 0; i < 10; i++)
        {
            Thread daemon = new Thread(new T02Daemon());
            // 必须在start前设置成deamon线程
            daemon.setDaemon(true); // Must call before start()
            daemon.setName("后台线程" + i);
            daemon.start();
        }
        System.out.println("所有后台线程已启动");
        // 调整这个时间,就能看到后台线程在工作打的日志
        TimeUnit.MILLISECONDS.sleep(175);
        // 一旦main()方法完成工作.就没有其他 非后台线程了. 所以所有后台线程daemon被终止
    }
}
