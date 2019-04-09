package com.zc.concurrent;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//多个读锁不互斥, 读锁和写锁互斥, 写锁和写锁互斥. 即写锁和所有互斥
public class T03ReadWriteLock
{
    public static void main(String[] args)
    {
        // 使用同一个myData,则里面使用同一把锁
        final MyData data = new MyData();
        for (int i = 0; i < 3; i++)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        data.get();
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
                        data.put();
                    }
                }
            }).start();
        }
    }
}

class MyData
{
    /**
     * 共享数据 
     */
    private Integer data;

    //ReadWriteLock为接口		ReentrantReadWriteLock为实现类
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 读取.加读锁,不会影响其他线程读 
     */
    public void get()
    {
        readWriteLock.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName() + "读取data之前");
            System.out.println(Thread.currentThread().getName() + "读取了data: " + data);
            Thread.sleep((long) (Math.random() * 2000));
            System.out.println(Thread.currentThread().getName() + "读取data之后");
            System.out.println();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 写入.阻塞其他写 和 读
     */
    public void put()
    {
        readWriteLock.writeLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName() + "修改data之前");
            data = new Random().nextInt();
            System.out.println(Thread.currentThread().getName() + "将data修改为: " + data);
            try
            {
                Thread.sleep((long) (Math.random() * 2000));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "修改data之后");
            System.out.println();
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

}
