package com.zc.z01demo;
// synchronized锁支持可重入
public class T12SynchronizedRe
{
    public static void main(String[] args)
    {
        SubLock subLock = new SubLock();
        // main线程执行
        subLock.doSomething();
        // 子线程执行
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                subLock.doSomething();
            }
        }).start();
    }
}

class SuperLock
{
    // 加不加 synchronized均可
    public void doSomething()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":调用 父类中 的doSomething()方法");
    }
}

class SubLock extends SuperLock
{
    @Override
    public synchronized void doSomething()
    {
        System.out.println(Thread.currentThread().getName() + ":调用 子类中 的doSomething()方法");
        super.doSomething();
    }
}
