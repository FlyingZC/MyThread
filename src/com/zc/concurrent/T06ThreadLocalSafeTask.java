package com.zc.concurrent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class T06ThreadLocalSafeTask implements Runnable
{

    private static ThreadLocal<Date> startDate = new ThreadLocal<Date>()
    {
        /*
         *  加入了ThreadLocal这个类，主要的目的就是这样就能保证每次线程开启的时候都
                                    会去调用他的initvalue方法给这个属性赋一个初值，然后每个线程都会自己维护这个值，
        	各个线程之间都是独立的，我们通过get方法就能获得对应的值，
        	当然他也提供了set方法来更改他的值，或者remove等等，反正现在就是线程安全的了。
         * */
        @Override
        protected Date initialValue()
        {
            return new Date();
        };
    };

    @Override
    public void run()
    {
        System.out.println("start thread " + Thread.currentThread().getId() + " " + startDate.get());
        try
        {
            TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("Thread finish " + Thread.currentThread().getId() + " " + startDate.get());
    }

    public static void main(String[] args)
    {
        T06ThreadLocalSafeTask task = new T06ThreadLocalSafeTask();
        for (int i = 0; i < 10; i++)
        {
            Thread thread = new Thread(task);
            thread.start();
            try
            {
                TimeUnit.SECONDS.sleep(2);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }

}
