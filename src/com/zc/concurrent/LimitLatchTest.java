package com.zc.concurrent;

public class LimitLatchTest
{
    public static void main(String[] args) throws Exception
    {
        LimitLatch latch = new LimitLatch(10);
        for (int i = 0; i < 12; i++) 
        {
            latch.countUpOrAwait();
        }
        latch.countDown();
        latch.countDown();
        latch.countDown();
    }
}
