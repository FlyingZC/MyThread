package com.zc.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

//读写锁实现缓存
public class T03RWLockCachedData
{
    Object data;

    volatile boolean cacheValid;

    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    void processCachedData()
    {
        rwl.readLock().lock();
        if (!cacheValid)
        {
            // 是否有缓存数据,若没有则查询
            // Must release read lock before acquiring write lock
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            // Recheck state because another thread might have acquired
            //   write lock and changed state before we did.
            if (!cacheValid)
            {
                //data = ...
                cacheValid = true;//查到数据保存缓存,置true
            }
            // Downgrade by acquiring read lock before releasing write lock
            rwl.readLock().lock();//(自己可以同时挂写锁和读锁)锁对自己线程无效.此时写锁未是否,又挂读锁,成为更新锁
            rwl.writeLock().unlock(); // Unlock write, still hold read
        }

        //use(data);
        rwl.readLock().unlock();
    }
}
