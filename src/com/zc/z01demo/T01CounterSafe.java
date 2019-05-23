package com.zc.z01demo;

import java.util.concurrent.atomic.AtomicInteger;

public class T01CounterSafe {

    // public volatile static int count = 0;

    public volatile static AtomicInteger count = new AtomicInteger(0);

    public static void inc()
    {
        try
        {
            Thread.sleep(1);
        }
        catch (InterruptedException e)
        {
        }
        System.out.println(count.incrementAndGet());
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < 1000; i++)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    T01CounterSafe.inc();
                }
            }).start();
        }
    }
}
