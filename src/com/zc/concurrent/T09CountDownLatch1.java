package com.zc.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch可以用来管理一组相关的线程执行，只需在主线程中调用CountDownLatch 的await方法（一直阻塞），
 * 让各个线程调用countDown方法。当所有的线程都只需完countDown了，await也顺利返回，不再阻塞了。
 * 在这样情况下尤其适用：将一个任务分成若干线程执行，等到所有线程执行完，再进行汇总处理。 
 * @author zc
 *
 */
public class T09CountDownLatch1
{
    private static final int N = 10;

    public static void main(String[] args) throws InterruptedException
    {
        // 每个线程执行完的信号
        CountDownLatch doneSignal = new CountDownLatch(N);
        // 开始执行信号
        CountDownLatch startSignal = new CountDownLatch(1);  
        /*
         * CounDownLatch对于管理一组相关线程非常有用。上述示例代码中就形象地描述了两种使用情况。
         * 第一种是计算器为1，代表了两种状态，开关。第二种是计数器为N，代表等待N个操作完成。
         * 今后我们在编写多线程程序时，可以使用这个构件来管理一组独立线程的执行。 
         * */

        for (int i = 1; i <= N; i++)
        {
            //Work类实现Runnable接口
            new Thread(new Worker(i, doneSignal, startSignal)).start();//线程启动了  
        }
        System.out.println("begin------------");
        // startSingnal对象的信号量为1,countDonw()减1后为0,开始执行啦.发出开始信号
        startSignal.countDown();
        // 等待所有的线程执行完毕.(所有子线程未必有序执行,但是最终全执行完毕才会走到下一步)
        doneSignal.await();  
        System.out.println("main线程已经收到所有线程执行完毕的信号Ok");
    }

    static class Worker implements Runnable
    {
        private final CountDownLatch doneSignal;

        private final CountDownLatch startSignal;

        private int beginIndex;

        Worker(int beginIndex, CountDownLatch doneSignal, CountDownLatch startSignal)
        {
            this.startSignal = startSignal;
            this.beginIndex = beginIndex;
            this.doneSignal = doneSignal;
        }

        public void run()
        {
            try
            {
                startSignal.await(); //等待开始执行信号的发布
                System.out.println(Thread.currentThread().getName() + ": 开始执行");
                beginIndex = (beginIndex - 1) * 10 + 1;
                for (int i = beginIndex; i <= beginIndex + 10; i++)
                {
                    System.out.println(i);
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                System.out.println(Thread.currentThread().getName() + ": 执行完毕");
                System.out.println();
                // 每个线程执行完毕后.都调用countDown().每个线程执行完毕的信号
                doneSignal.countDown();
            }
        }
    }
}
