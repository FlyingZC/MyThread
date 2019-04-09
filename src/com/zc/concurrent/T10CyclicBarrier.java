package com.zc.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier就是一个栅栏，等待所有线程到达后再执行相关的操作。barrier 在释放等待线程后可以重用。 
 * @author zc
 */
public class T10CyclicBarrier
{
    public static void main(String[] args)
    {
        // ExecutorService:具有服务生命周期的Executor(例如关闭),它知道如何构建恰当的上下文来执行Runnable对象
        ExecutorService exec = Executors.newCachedThreadPool();
        final Random random = new Random();
        final CyclicBarrier barrier = new CyclicBarrier(4, new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("大家都到齐了，开始happy去");
            }
        });

        for (int i = 0; i < 4; i++)
        {
            exec.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(random.nextInt(1000));
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "到了，其他哥们呢");
                    try
                    {
                        barrier.await();//等待其他哥们  
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    catch (BrokenBarrierException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
        exec.shutdown();
    }

}
