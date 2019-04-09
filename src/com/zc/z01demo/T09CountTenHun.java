package com.zc.z01demo;
// wait(),notify()
// 子线程执行10次.主线程执行100次....
public class T09CountTenHun
{
    public static void main(String[] args)
    {
        new Thread(new Runnable()
        {  
            // 一定先创建子线程,因为后面main死循环
            @Override
            public void run()
            {
                while (true)
                {
                    synchronized (T09CountTenHun.class)
                    {
                        // 唤醒其他等待T09CountTenHun.class锁的线程
                        T09CountTenHun.class.notify();
                        for (int i = 1; i <= 10; i++)
                        {
                            System.out.println(Thread.currentThread().getName() + i);
                        }
                        try
                        {
                            // 释放锁.当前线程进入WAITING状态,直到等待其他线程的通知 或 中断 才会返回
                            T09CountTenHun.class.wait();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        
        while (true)
        {
            synchronized (T09CountTenHun.class)
            {
                while (true)
                {
                    T09CountTenHun.class.notify();
                    for (int i = 1; i <= 100; i++)
                    {
                        System.out.println(Thread.currentThread().getName() + i);
                    }
                    try
                    {
                        T09CountTenHun.class.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
