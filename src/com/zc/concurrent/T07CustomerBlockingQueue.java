package com.zc.concurrent;

import java.util.concurrent.ArrayBlockingQueue;

// 生产者不断产生数据(将生产出来的数据放置在队列中), 消费者不断消费数据(从队列中获取)
// 当队列容器已满，生产者线程会被阻塞，直到队列未满； 当队列容器为空时，消费者线程会被阻塞，直至队列非空时为止。
// 10个线程消费生产者产生的数据,依次有序的消费数据
public class T07CustomerBlockingQueue
{
    public static void main(String[] args)
    {
        final ArrayBlockingQueue<String> q = new ArrayBlockingQueue<String>(10);
        for (int i = 0; i < 10; i++)
        {
            new Thread()
            {
                public void run()
                {
                    while (true)
                    {
                    }
                };
            }.start();
        }
    }

    class Producer
    {
        public void product() throws InterruptedException
        {
            int i = 0;
            while (true)
            {
                i++;
            }
        }
    }
}
