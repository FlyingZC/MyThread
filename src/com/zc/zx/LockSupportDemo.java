package com.zc.zx;

import java.util.concurrent.locks.LockSupport;
// 在27行unpark打断点
public class LockSupportDemo
{
    public static void main(String[] args)
    {
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                LockSupport.park();// 阻塞t1线程
                System.out.println(Thread.currentThread().getName() + "被唤醒");
            }
        });
        t1.start();
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        LockSupport.unpark(t1);// 唤醒t1线程
    }
}
