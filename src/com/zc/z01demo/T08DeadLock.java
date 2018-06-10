package com.zc.z01demo;

// 死锁
public class T08DeadLock
{
    static StringBuffer stringBuffer1 = new StringBuffer();

    static StringBuffer stringBuffer2 = new StringBuffer();

    public static void main(String[] args)
    {
        // 线程1
        new Thread()
        {
            public void run()
            {
                synchronized (stringBuffer1)
                {
                    try
                    {
                        // sleep时cpu会被其他线程抢占.但是当前线程占有的同步资源不会释放.
                        // 当前线程占用资源stringBuffer1锁不放.然后sleep.cpu被另一个线程抢占.
                        // 另一个线程2占用stringBuffer2锁不放.而当前线程后面还需要stringBuffer2.死锁
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    stringBuffer1.append("A");
                    synchronized (stringBuffer2)
                    {
                        stringBuffer2.append("B");
                        System.out.println(stringBuffer1);
                        System.out.println(stringBuffer2);
                    }
                }
            }
        }.start();
        
        // 线程2
        new Thread()
        {
            public void run()
            {
                synchronized (stringBuffer2)
                {
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    stringBuffer1.append("C");
                    synchronized (stringBuffer1)
                    {
                        stringBuffer2.append("D");
                        System.out.println(stringBuffer1);
                        System.out.println(stringBuffer2);
                    }
                }
            }
        }.start();
    }
}
