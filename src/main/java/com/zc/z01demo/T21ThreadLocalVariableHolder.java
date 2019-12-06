package com.zc.z01demo;

// ThreadLocal给每个线程都是单独存储的,没有并发问题.get(),set()时也无需加锁
import java.util.concurrent.*;
import java.util.*;

public class T21ThreadLocalVariableHolder
{ 
    // ThreadLocal匿名内部类对象,保证不会出现竞争
    private static ThreadLocal<Integer> localValue = new ThreadLocal<Integer>()
    {
        private Random rand = new Random(47);
        // 重写ThreadLocal中的initialValue方法,给localValue赋随机数初值
        @Override
        protected synchronized Integer initialValue()
        {
            return rand.nextInt(10000);
        }
    };

    // 所以get,set都不需要加锁
    public static void increment()
    {// 加 1
        localValue.set(localValue.get() + 1);
        System.out.println(Thread.currentThread().getName() + ", getLocalValue: " + localValue.get());
    }

    public static int get()
    {
        return localValue.get();
    }

    public static void main(String[] args) throws Exception
    {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
            exec.execute(new Accessor(i));
        TimeUnit.SECONDS.sleep(3); // Run for a while
        exec.shutdownNow(); // All Accessors will quit
    }
}

class Accessor implements Runnable
{
    private final int id;

    public Accessor(int idn)
    {
        id = idn;
    }

    public void run()
    {
        while (!Thread.currentThread().isInterrupted())
        {
            T21ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    public String toString()
    {
        return "#" + id + ": " + T21ThreadLocalVariableHolder.get();
    }
}
