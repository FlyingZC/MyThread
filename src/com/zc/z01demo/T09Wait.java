package com.zc.z01demo;

/*
 * 执行顺序:
 * 	main启动t1前
	main启动t1后
	main等待wait前
	t1准备唤醒notify
	t1唤醒后
	main等待wait后
 * */
public class T09Wait
{
    public static void main(String[] args)
    {
        MyWThread t1 = new MyWThread("t1");
        synchronized (t1)
        {
            System.out.println(Thread.currentThread().getName() + "启动t1前");
            t1.start();
            System.out.println(Thread.currentThread().getName() + "启动t1后");
            try
            {
                System.out.println(Thread.currentThread().getName() + "等待wait前");
                //虽然是t1.wait(),但是wait()是在main线程中调用.所以仍是main线程wait
                t1.wait();//使用同步监视器对象调用wait方法
                System.out.println(Thread.currentThread().getName() + "等待wait后");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

class MyWThread extends Thread
{
    public MyWThread(String name)
    {
        super(name);
    }

    @Override
    public void run()
    {
        synchronized (this)
        {
            System.out.println(Thread.currentThread().getName() + "准备唤醒notify");
            notify();
            System.out.println(Thread.currentThread().getName() + "唤醒后");
        }
    }
}
