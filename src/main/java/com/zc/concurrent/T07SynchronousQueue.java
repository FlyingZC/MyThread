package com.zc.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 一种阻塞队列，其中每个插入操作必须等待另一个线程的对应移除操作 ，反之亦然。
 * 同步队列没有任何内部容量，甚至连一个队列的容量都没有。不能在同步队列上进行 peek，因为仅在试图要移除元素时，该元素才存在；
 * 除非另一个线程试图移除某个元素，否则也不能（使用任何方法）插入元素；也不能迭代队列，因为其中没有元素可用于迭代。
 * 队列的头 是尝试添加到队列中的首个已排队插入线程的元素；如果没有这样的已排队线程，则没有可用于移除的元素并且 poll() 将会返回 null。
 * 对于其他 Collection 方法（例如 contains），SynchronousQueue 作为一个空 collection。此队列不允许 null 元素。
 */
public class T07SynchronousQueue {
    private static final BlockingQueue<Integer> q = new SynchronousQueue<>();
    public static void main(String[] args) {
        // 生产者线程
        new Thread() {
            public void run() {
                for (int i = 0; i < 6; i++) {
                    try {
                        Thread.sleep(1000);
                        q.put(i);
                        System.out.println("正在放入" + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        // 消费者线程
        new Thread() {
            public void run() {
                for (int i = 0; i < 6; i++) {
                    try {
                        System.out.println("正在取出" + q.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
