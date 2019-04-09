package com.zc.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
// 线程池
public class T01ExecutorService
{
    @Test
    public void testCachedThreadPool()
    {
        // CachedThreadPool在程序执行过程中通常会创建 与 所需数量相同的线程.
        // 然后在 它回收旧线程时 停止创建新线程.因此它是合理的Executor的首选,
        // 只有当这种方式会引发问题时,才需要切换到FixedThreadPool
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
        {// 会起五个线程
            exec.execute(new SubRunnable());
        }
        // 对shoutdown()的调用 可防止新任务 被提交给这个Executor.
        // 当前线程(此处为main线程)将继续运行在shutdown()被调用前提交的所有任务.
        // 这个程序将在Executor中的所有任务完成后尽快退出.
        exec.shutdown();
    }
    
    @Test
    public void testFixedThreadPool()
    {
        // FixedThreadPool使用 有限的线程集 来执行所提交的任务
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++)
        {
            exec.execute(new SubRunnable());
        }
        exec.shutdown();
    }
    
    @Test
    public void testSingleThreadExecutor()
    {
        // SingleThreadExecutor就像是线程数量为1的FixedThreadPool.
        // 若希望在某个线程中连续运行的任何任务(例如监听套接字).或短任务 同样方便
        // 若向它提交多个任务,这些任务会排队.每个任务都会在 下个任务开始之前运行结束.
        // 即它会 序列化 所有提交给它的任务,并维护自己的悬挂任务队列
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++)
            exec.execute(new SubRunnable());
        exec.shutdown();
    }
    
}

class SubRunnable implements Runnable
{

    @Override
    public void run()
    {
        System.out.println("====" + Thread.currentThread().getName() + "==== begin ====");
        for (int i = 0; i < 10; i++)
        {
            System.out.println(Thread.currentThread().getName() + ", i = " + i);
        }
        System.out.println("====" + Thread.currentThread().getName() + "==== end ====");
    }
    
}
