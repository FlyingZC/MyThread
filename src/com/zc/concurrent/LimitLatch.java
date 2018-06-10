package com.zc.concurrent;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author flyingzc
 * tomcat内核剖析-p67 LimitLatch流量控制器的实现.基于并发框架AQS.
 * 通过控制同步器的状态即可实现,具体思路是先初始化状态值，然后每到来一个socket就将状态加1，每关闭一个socket将状态减1，
 * 如此一来一旦状态值大于最大限制值.则AQS机制将接收线程阻塞,从而停止对socket的接收，
 * 直到某个socket处理完释放,则会重新唤醒接收线程往下接收套接字。
 * 
 * 我们把思路拆成两部分，
 * 一是创建一个支持计数的控制器，
 * 另一个是将此控制器嵌入处理流程中。 
 */
public class LimitLatch
{
    private class Sync extends AbstractQueuedSynchronizer
    {
        public Sync()
        {
        }
        // 共享式 获取同步状态.返回大于等于0的值表示成功. 否则表示获取失败
        @Override
        protected int tryAcquireShared(int ignored)
        {
            long newCount = count.incrementAndGet();// 增加并 获取
            if (newCount > limit)// 连接数 > limit
            {// 当计数大于limit时,减小计数,返回-1代表获取失败.当前线程会挂起,直到其他线程操作计数又小于limit
                count.decrementAndGet();// 减小 并 获取
                return -1;// 大于limit返回-1.会在这一行阻塞住
            }
            else
            {
                return 1;
            }
        }
        // 共享式 释放同步状态
        @Override
        protected boolean tryReleaseShared(int arg)
        {
            count.decrementAndGet();
            return true;
        }
    }

    private final Sync sync;

    /**
     * 计数
     */
    private final AtomicLong count;

    /**
     * 上限
     */
    private volatile long limit;

    public LimitLatch(long limit)
    {
        this.limit = limit;
        this.count = new AtomicLong(0);
        this.sync = new Sync();
    }

    /**
     * 计数 增加,当计数超过最大限制 则会阻塞线程
     * @throws InterruptedException
     */
    public void countUpOrAwait() throws InterruptedException
    {
        sync.acquireSharedInterruptibly(1);// 最终会调用sync.tryAcquireShared增加计数
    }

    /**
     * 计数 减小,递减数字并唤醒线程
     * @return
     */
    public long countDown()
    {
        sync.releaseShared(0);
        long result = count.get();
        return result;
    }
}
