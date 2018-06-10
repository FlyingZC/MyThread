package com.zc.z01demo;

import java.util.Random;
// 启动4个线程.并向ThreadLocal里存入随机数值. 每个线程都取两次. 同一个线程里取两次取出来的值应该相同. 不同线程取出来的不同.即 每个线程都有单独的数据
public class T21ThreadLocal
{
    // 线程范围内共享变量
    static ThreadLocal<Integer> x = new ThreadLocal<Integer>();

    public static void main(String[] args)
    {
        //启动四个线程
        for (int i = 0; i < 4; i++)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + "放入了" + data);
                    // 放入数据到ThreadLocal中
                    x.set(data);
                    new A().get();
                    new B().get();

                }
            }).start();
        }
    }

    //内部类可用static修饰
    static class A
    {
        public void get()
        {
            //取出数据
            int data = x.get();
            System.out.println("A获取" + Thread.currentThread().getName() + "的数据: " + data);
        }
    }

    static class B
    {
        public void get()
        {
            int data = x.get();
            System.out.println("B获取" + Thread.currentThread().getName() + "的数据: " +  data);
        }
    }
}
