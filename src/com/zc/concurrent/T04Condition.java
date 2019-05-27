package com.zc.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 子线程循环10次,主线程循环100次,重复五十次.使用condition		await()和signal()
public class T04Condition
{
    public static void main(String[] args)
    {
        final CountBusiness business = new CountBusiness();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 1; i <= 50; i++)
                {
                    business.sub(i);
                }
            }
        }).start();

        for (int i = 1; i <= 50; i++)
        {
            business.main(i);
        }

    }
}

class CountBusiness
{
    Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();
    // 最多同时只有一个线程操作该变量
    private boolean bShouldSub = true;// 应该 sub线程执行的标识

    public void sub(int count)
    {
        lock.lock();
        try
        {
            while (!bShouldSub) // 不应该 sub线程执行,则一直 await
            {
                try
                {
                    condition.await();// await();等!!!
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            // 等到应该 sub线程执行,处理自己的逻辑
            for (int i = 1; i <= 10; i++)
            {
                System.out.println(Thread.currentThread().getName() + "第" + count + "次,i=" + i);
            }
            bShouldSub = false;// 交替执行,给 main线程一个机会..
            condition.signal();// 相当于notify()
        }
        finally
        {
            lock.unlock();// 解锁
        }
    }

    public void main(int count)
    {
        lock.lock();
        try
        {
            while (bShouldSub)
            {
                try
                {
                    condition.await();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            for (int i = 1; i <= 100; i++)
            {
                System.out.println(Thread.currentThread().getName() + "第" + count + "次,i=" + i);
            }
            bShouldSub = true;
            condition.signal();
        }
        finally
        {
            lock.unlock();
        }
    }
}
