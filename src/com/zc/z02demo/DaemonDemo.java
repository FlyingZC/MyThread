package com.zc.z02demo;

public class DaemonDemo
{
    public static void main(String[] args)
    {
        Thread daemonThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                // daemodThread run方法中是一个while死循环，会一直打印,但是当main线程结束后daemonThread就会退出所以不会出现死循环的情况。
                while (true)
                {
                    try
                    {
                        System.out.println("i am alive");
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        // 守护线程在退出的时候并不会执行finnaly块中的代码，所以将释放资源等操作不要放在finnaly块中执行，这种操作是不安全的
                        System.out.println("finally block");
                    }
                }
            }
        });
        // 并且需要注意的是设置守护线程要先于start()方法.否则会报> Exception in thread "main" java.lang.IllegalThreadStateException
        daemonThread.setDaemon(true);
        daemonThread.start();
        //确保main线程结束前能给daemonThread能够分到时间片
        try
        {
            Thread.sleep(800);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
