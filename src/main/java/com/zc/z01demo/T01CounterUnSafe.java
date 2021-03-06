package com.zc.z01demo;
// 多线程计数线程不安全
public class T01CounterUnSafe
{
    public static int count = 0;

    public static void inc()
    {
        //这里延迟1毫秒，使得结果明显
        try
        {
            Thread.sleep(1);
        }
        catch (InterruptedException e)
        {
        }
        count++;
        System.out.println(count);
    }

    public static void main(String[] args)
    {
        //同时启动1000个线程，去进行i++计算，看看实际结果
        for (int i = 0; i < 1000; i++)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    T01CounterUnSafe.inc();
                }
            }).start();
        }
    }
}
