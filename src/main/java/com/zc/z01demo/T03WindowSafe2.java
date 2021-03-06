package com.zc.z01demo;

// 使用实现Runnable接口 和 同步方法 的方式.实现线程安全的售票.
public class T03WindowSafe2
{
    public static void main(String[] args)
    {
        Window3 w = new Window3();
        Thread t1 = new Thread(w);
        t1.start();
        Thread t2 = new Thread(w);
        t2.start();
        Thread t3 = new Thread(w);
        t3.start();
    }
}

class Window3 implements Runnable
{
    int ticket = 100;

    @Override
    public void run()
    {
        while (true)
        {
            show();
        }
    }

    //同步方法
    public synchronized void show()
    {
        if (ticket > 0)
        {
            try
            {
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "售票" + ticket--);
        }
    }
}
