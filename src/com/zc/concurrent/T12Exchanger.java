package com.zc.concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author flyingzc
 * 为两个线程提供了一个同步点，当两个线程都达到了同步点之后就可以使用exchange方法，互相交换数据；
        如果一个线程先达到了同步点，会在同步点阻塞等待直到另外一个线程也到达同步点
 */
public class T12Exchanger
{
    public static void main(String[] args)
    {
        ExecutorService pool = Executors.newCachedThreadPool();
        // 用于交换不同线程间的数据
        final Exchanger<String> exchanger = new Exchanger<String>();
        // 执行第一个Runnable
        pool.execute(new Runnable()
        {
            public void run()
            {
                try
                {

                    String drug = "毒品";
                    System.out.println("线程" + Thread.currentThread().getName() + "带着" + drug + "前往交易的路上");
                    Thread.sleep((long) (Math.random() * 10000));
                    // 会等待另一个线程到此,然后交换
                    String money = exchanger.exchange(drug);
                    System.out.println("线程" + Thread.currentThread().getName() + "交易成功拿到" + money);
                }
                catch (Exception e)
                {

                }
            }
        });
        
        // 执行第二个Runnable
        pool.execute(new Runnable()
        {
            public void run()
            {
                try
                {

                    String drug = "金钱";
                    System.out.println("线程" + Thread.currentThread().getName() + "带着" + drug + "前往交易的路上");
                    Thread.sleep((long) (Math.random() * 10000));
                    String money = exchanger.exchange(drug);//会等待另一个线程到此,然后交换
                    //随后各干各的
                    System.out.println("线程" + Thread.currentThread().getName() + "交易成功拿到" + money);
                }
                catch (Exception e)
                {

                }
            }
        });
        pool.shutdown();
    }
}
