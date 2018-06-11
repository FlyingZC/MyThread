package com.zc.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// 模拟16个日志对象,并且需要运行16秒才能打印完这些日志。
// 开启4个线程调用parseLog()分别打印16个日志对象,运行4秒打印完
public class T07ParseLogBlockingQueue
{
    public static void main(String[] args)
    {
        final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(16);// 一个也可以
        for (int i = 0; i < 4; i++)
        {
            new Thread()
            {
                public void run()
                {
                    while (true)
                    {
                        try
                        {
                            String log = queue.take();
                            parseLog(log);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
            }.start();
        }
        for (int i = 0; i < 16; i++)
        {
            String log = "日志" + i;
            try
            {
                queue.put(log);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    // 虽然从queue中取出来数据是有序的.但是打印动作未同步,所以打出来还是乱序的
    public static void parseLog(String log)
    {
        System.out.println(log + ":" + (System.currentTimeMillis()));
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
