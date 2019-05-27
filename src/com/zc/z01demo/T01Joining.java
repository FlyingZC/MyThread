package com.zc.z01demo;

//: concurrency/Joining.java

/**
 * @author flyingzc
 * thinking in java 中的例子.在Joiner线程中调用Sleeper线程.join()
 */
class Sleeper extends Thread
{
    /**
     * 间隔
     * */
    private int duration;

    /**
     * sleeper是一个Thread的子类,通过构造器设置休眠时间.
     * @param name
     * @param sleepTime
     */
    public Sleeper(String name, int sleepTime)
    {
        super(name);
        duration = sleepTime;
        // 线程start()
        start();
    }

    public void run()
    {
        try
        {
            // sleep()可能在指定时间期满时返回,也可能被中断.
            Thread.sleep(duration);
        }
        catch (InterruptedException e)
        {
            // 根据isInterrupted()的返回值报告这个中断
            // 当另一个线程 在该线程上调用interrupt()时,将给该线程设定一个标志,表明该线程已被中断.
            // 然而,异常被捕获时将清理这个标志,所以在catch子句中,在异常被捕获的时候 这个标志总是为false
            System.out.println(getName() + " was interrupted. " + "isInterrupted(): " + isInterrupted());
            return;
        }
        System.out.println(getName() + " has awakened");
        System.out.println(getName() + "执行完毕");
    }
}

class Joiner extends Thread
{
    /**
     * 线程 对象
     * */
    private Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper)
    {
        super(name);
        this.sleeper = sleeper;
        // 启动joiner线程
        start();
    }

    public void run()
    {
        try
        {
            // Joiner线程 通过 在 Sleeper对象上调用join()方法 来 等待Sleeper醒来.
            // 在main方法里,每个Sleeper都有一个Joiner.
            // 输出中可发现,若Sleeper被中断 或 正常结束.Joiner将和Sleeper一同结束
            // java.util.concurrent.CyclicBarrier比join()更适合
            // 该线程 会等待sleeper执行完毕再继续执行
            sleeper.join();
            // 上一句join,会等待sleeper线程执行完毕(第90行调用sleeper2.interrupt()也会导致sleeper线程执行完毕)
            System.out.println(sleeper.getName() + "是否存活:" + sleeper.isAlive());// false,sleeper线程不再存活
            System.out.println(this.getName() + "是否存活:" + this.isAlive());// true
        }
        catch (InterruptedException e)
        {
            System.out.println("Interrupted");
        }
        System.out.println(getName() + " join completed");
        System.out.println(getName() + "执行完毕");
    }
}

public class T01Joining
{
    public static void main(String[] args)
    {
        // joiner等待 sleeper线程执行完毕后继续执行
        Sleeper sleeper1 = new Sleeper("Sleeper1", 1500),
                sleeper2 = new Sleeper("Sleeper2", 1500);

        Joiner joiner1 = new Joiner("Joiner1", sleeper1),
                joiner2 = new Joiner("Joiner2", sleeper2);

        // main线程中断 sleeper2线程,由于 sleeper2.join(),则 joiner2在 sleeper2线程被中断后(即执行完毕)继续执行
        sleeper2.interrupt();
    }
} 
