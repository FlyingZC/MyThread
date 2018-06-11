package com.zc.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*  		抛出异常 		特殊值		 阻塞 			超时 
	插入 		add(e) 		offer(e) 	put(e) 		offer(e, time, unit) 
            移除 		remove() 	poll() 		take() 		poll(time, unit) 
            检查 		element() 	peek() 		不可用 		不可用 
*/

public class T07ArrayBlockQueue
{
    public static void main(String[] args)
    {
        final BlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(5);
        new Thread()
        {
            public void run()
            {
                for (int i = 0; i < 6; i++)
                {
                    try
                    {
                        Thread.sleep(1000);
                        q.put(i);
                        System.out.println("正在放入" + i);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        }.start();

        new Thread()
        {
            public void run()
            {
                for (int i = 0; i < 6; i++)
                {
                    try
                    {
                        System.out.println("正在取出" + q.take());
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }
}
