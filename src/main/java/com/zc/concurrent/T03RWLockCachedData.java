package com.zc.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

// 读写锁实现缓存
public class T03RWLockCachedData {

    public static void main(String[] args) {
        T03RWLockCachedData cache = new T03RWLockCachedData();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    cache.processCachedData("za");
                }
            }).start();
        }
    }

    String data;

    volatile boolean cacheValid;
    // 读写锁实例
    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    /**
     * 先读缓存,或没有则写缓存后再读
     */
    void processCachedData(String str) {
        rwl.readLock().lock();// 获取读锁
        if (!cacheValid) // 如果缓存过期了，或者为 null
        {
            // 是否有缓存数据,若没有则查询
            // Must release read lock before acquiring write lock
            rwl.readLock().unlock();// 释放掉读锁，然后获取写锁 (后面会看到，没释放掉读锁就获取写锁，会发生死锁情况)
            rwl.writeLock().lock();
            // Recheck state because another thread might have acquired
            //   write lock and changed state before we did.
            try {
                if (!cacheValid) // 重新判断，因为在等待写锁的过程中，可能前面有其他写线程修改了该值
                {
                    data = str;
                    cacheValid = true;//查到数据保存缓存,置true
                }
                // Downgrade by acquiring read lock before releasing write lock
                // 在持有 写锁 的情况下 获取 写锁. 锁降级
                rwl.readLock().lock();// 获取读锁 (持有写锁的情况下，是允许获取读锁的，称为 “锁降级”，反之不行。) (自己可以同时挂写锁和读锁)锁对自己线程无效.此时写锁未是否,又挂读锁,成为更新锁
            } finally {
                // 释放写锁，此时还剩一个读锁
                rwl.writeLock().unlock(); // Unlock write, still hold read
            }
        }
        try {
            // 使用数据
            // use(data);
            System.out.println(data);
        } finally {
            // 释放读锁
            rwl.readLock().unlock();
        }
    }
}
