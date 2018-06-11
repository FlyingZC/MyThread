package com.zc.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author flyingzc
 * AQS 独占模式
 */
public class T02BankServiceWindowAQS
{
    private final Sync sync;

    public T02BankServiceWindowAQS()
    {
        sync = new Sync();
    }

    /**
     * @author flyingzc
     * 使用一个继承AQS同步器的子类实现
     */
    private static class Sync extends AbstractQueuedSynchronizer
    {
        private static final long serialVersionUID = 1L;

        public boolean tryAcquire(int acquires)
        {
            if (compareAndSetState(0, 1))
            {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int releases)
        {
            if (getState() == 0)
                throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    public void handle()
    {
        sync.acquire(1);
    }

    public void unhandle()
    {
        sync.release(1);
    }
}
