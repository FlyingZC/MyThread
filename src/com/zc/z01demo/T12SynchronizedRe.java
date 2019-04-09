package com.zc.z01demo;
// synchronized锁支持可重入
public class T12SynchronizedRe
{
    public static void main(String[] args)
    {
        // main线程执行
        new SubLock().doSomething();
        // 子线程执行
        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                new SubLock().doSomething();
            }

        }).start();
    }
}

class SuperLock
{
    public synchronized void doSomething()
    {
        System.out.println(Thread.currentThread().getName() + ":调用 父类中 的doSomething方法");
    }
}

class SubLock extends SuperLock
{
    @Override
    public synchronized void doSomething()
    {
        System.out.println(Thread.currentThread().getName() + ":调用 子类中 的doSomething方法");
        super.doSomething();
    }
}
