package com.zc.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 多个线程按顺序执行,使用多个Condition(执行条件)
public class T04ConditionMore
{
    public static void main(String[] args)
    {
        final Cond con = new Cond();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                con.sub1();
            }
        }).start();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                con.sub2();
            }
        }).start();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                con.sub3();
            }
        }).start();
    }
}

class Cond
{
    Lock lock = new ReentrantLock();

    private int bShoudSub = 1;

    //多个Condition对象
    Condition condition1 = lock.newCondition();

    Condition condition2 = lock.newCondition();

    Condition condition3 = lock.newCondition();

    public void sub1()
    {
        try
        {
            lock.lock();//加锁
            while (bShoudSub != 1)
            {
                // 不是1则await()
                try
                {
                    condition1.await();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 10; i++)
            {
                System.out.println("sub1" + ",i=" + i);
            }
            bShoudSub = 2;//修改
            condition2.signal();// 唤醒condition2
        }
        finally
        {
            lock.unlock();
        }
    }

    public void sub2()
    {
        try
        {
            lock.lock();// 如果不加锁就解锁,会报IllegalMonitorStateException	
            while (bShoudSub != 2)
            {
                try
                {
                    condition2.await();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 10; i++)
            {
                System.out.println("sub2" + ",i=" + i);
            }

            bShoudSub = 3;
            condition3.signal();// 唤醒condition3
        }
        finally
        {
            lock.unlock();
        }
    }

    public void sub3()
    {
        lock.lock();
        try
        {
            while (bShoudSub != 3)
            {
                try
                {
                    condition3.await();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            for (int i = 0; i < 10; i++)
            {
                System.out.println("sub3" + ",i=" + i);
            }

            bShoudSub = 1;
            condition1.signal();// 唤醒condition1
        }
        finally
        {
            lock.unlock();
        }
    }
}
