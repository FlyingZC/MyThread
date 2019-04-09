package com.zc.concurrent;
// 独占式获取锁的Test
public class T02BankServiceWindowTest
{
    public static void main(String[] args)
    {
        final T02BankServiceWindowAQS bankServiceWindow = new T02BankServiceWindowAQS();
        Thread tom = new Thread()
        {
            public void run()
            {
                bankServiceWindow.handle();
                System.out.println("tom开始办理业务");
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("tom结束办理业务");
                System.out.println();
                bankServiceWindow.unhandle();
            }
        };
        Thread jim = new Thread()
        {
            public void run()
            {
                bankServiceWindow.handle();
                System.out.println("jim开始办理业务");
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("jim结束办理业务");
                System.out.println();
                bankServiceWindow.unhandle();
            }
        };
        Thread jay = new Thread()
        {
            public void run()
            {
                bankServiceWindow.handle();
                System.out.println("jay开始办理业务");
                try
                {
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("jay结束办理业务");
                System.out.println();
                bankServiceWindow.unhandle();
            }
        };
        tom.start();
        jim.start();
        jay.start();

    }
}
