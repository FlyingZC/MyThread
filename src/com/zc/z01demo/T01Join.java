package com.zc.z01demo;

import java.util.concurrent.TimeUnit;

/*
 * yield():(让步,放弃的意思,即释放cpu资源).暂停当前正在执行的线程对象，释放cpu资源.进入runnable态,
 * 并执行其他线程,仍有可能接着被自己抢占到资源(进入running态)。是在建议 具有形同优先级的其他线程可以运行
 * 
 * join():在a线程中调用b线程的b.join()方法.a线程将被挂起(停止执行).直到b线程执行完毕(此时b.isAlive()返回false)再执行a线程
 * 
 * isAlive():判断当前线程是否还存活
 * 
 * sleep(long l):显式的让当前进程睡眠.sleep时cpu会被其他线程抢占.但是当前线程占有的同步资源不会释放.
 * 线程通信:wait() notify() notifyAll()
 * 设置线程优先级:setPriority().默认为5 1最小 10最高.10抢占到cpu的概率更大
 * getPriority():优先级 和 底层系统映射关系不够好.唯一可移植的做法是只使用MAX_PRIORITY,MIN_PRIORITY,NORM_PRIORITY    
 * */
public class T01Join
{
    /*
     * main线程,启动两个线程myThread0,myThread1.这三个线程都循环100次.主线程会在i=20时等待子线程0执行完毕再继续执行
     * */
    public static void main(String[] args) throws InterruptedException
    {
        //线程默认有名.可以重命名
        Thread.currentThread().setName("主线程");

        MyThread myThread0 = new MyThread();
        MyThread myThread1 = new MyThread();

        myThread0.setName("子线程0");
        //设置优先级
        myThread0.setPriority(Thread.MAX_PRIORITY);
        myThread1.setName("子线程1");

        //3.启动另一条新的线程.会自动调用相应的run()方法
        //一个线程只能start()一次
        myThread0.start();
        myThread1.start();
        
        //下面主线程在创建完子线程后接着做自己的事情
        // 主线程循环100次.当主线程循环到i=20时,让thread0先执行完
        for (int i = 1; i <= 100; i++)
        {
            System.out.println(
                    Thread.currentThread().getName() + ", 优先级" + Thread.currentThread().getPriority() + ", i = " + i);
            if (i % 10 == 0)
            {
                //yield():暂停当前正在执行的线程对象，释放cpu资源.并执行其他线程,仍有可能接着被自己抢占到资源。
                Thread.yield();
            }

            if (i == 20)
            {
                //当 主线程 执行到20.先让子线程myThread0执行完(myThread0仍然会和myThread1抢夺cpu).再执行主线程.
                myThread0.join();
            }
            //由于myThread0.join();则此时myThread0已经执行完死掉
            System.out.println("线程0是否还存活" + myThread0.isAlive());
            //Thread.currentThread().getName():获取当前线程名
        }
    }
}

class MyThread extends Thread
{
    //1.创建一个继承于Thread的子类
    public void run()
    {
        //重写run()方法,每个线程循环100次.
        for (int i = 1; i <= 100; i++)
        {
            try
            {
                //1000ms = 1s
                // Thread.sleep(100);
                // java5引入的新sleep()api,有更好的语义.如毫秒为单位
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(
                    Thread.currentThread().getName() + ", 优先级" + Thread.currentThread().getPriority() + ", i = " + i);
        }
    }
}
