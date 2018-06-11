package com.zc.concurrent;

public class T02LimitLatchTest
{
    public static void main(String[] args) throws Exception
    {
        // 连接数上线为10
        final T02LimitLatch latch = new T02LimitLatch(10);
        /*  for (int i = 0; i < 12; i++) 
        {
            latch.countUpOrAwait();
        }
        latch.countDown();
        latch.countDown();
        latch.countDown();*/

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    for (int i = 0; i < 20; i++)
                    {
                        latch.countUpOrAwait();
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

        }).start();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {// 模拟20个线程处理完了,释放连接的情况
                for (int i = 0; i < 20; i++)
                {
                    latch.countDown();
                }
            }

        }).start();

    }
}
