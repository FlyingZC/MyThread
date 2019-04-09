package com.zc.z01demo;

/**
 * 程序进入了死循环！
 * 为什么会这样呢？这是因为,t1在sleep(阻塞)状态 时,被main线程调用t1.interrupt()中断
 * 此时，会清除中断标记[即isInterrupted()会返回false]，
 * 而且会抛出InterruptedException异常[该异常在while循环体内被捕获]。
 * 因此，t1理所当然的会进入死循环了。
 * 解决该问题，需要我们在捕获异常时，额外的进行退出while循环的处理。
 * 例如，在MyThread的catch(InterruptedException)中添加break 或 return就能解决该问题。
 * */
public class T18Interrupt2
{
    public static void main(String[] args)
    {
        Thread t1 = new MyIntThread();
        t1.start();
        try
        {
            Thread.sleep(300);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        t1.interrupt();
    }
}

class MyIntThread extends Thread
{

    @Override
    public void run()
    {
        synchronized (this)
        {
            int i = 0;
            while (!isInterrupted())// 如果此处采用isInterrupted()进行判断.当抛异常时候会清理掉中断标记.则isInterrupted还会返回false.从而会导致死循环
            {// 解决方法.抛异常时需要跳出循环
                try
                {
                    Thread.sleep(1000);// 如果当前线程再sleep过程中 被其他线程中断,则会抛InterruptedException
                    i++;
                    System.out.println(Thread.currentThread().getName() + "->" + i);
                }
                catch (InterruptedException e)
                {
                    // 如果线程被interrupt()时被抛InterruptedException,则会清除interrupt标记.即interrupted()会返回false
                    System.out.println("catch");
                    //return;
                    //break;
                }

            }
        }
    }
}
