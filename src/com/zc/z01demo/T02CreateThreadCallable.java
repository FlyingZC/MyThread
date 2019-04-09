package com.zc.z01demo;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// 实现Callback创建线程,并通过Future接收执行结果
public class T02CreateThreadCallable
{
    public static void main(String[] args)
    {
        // 线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        // 保存 线程执行结果
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++)
        {
            // 提交任务,并保存返回结果
            results.add(pool.submit(new MyCallable(i)));
        }
        for (Future<String> future : results)
        {
            try
            {
                // 阻塞返回线程执行结果
                System.out.println(future.get());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
            finally
            {
                pool.shutdown();
            }
        }
    }
}

class MyCallable implements Callable<String>
{
    private int id;

    public MyCallable(int id)
    {
        this.id = id;
    }

    @Override
    public String call() throws Exception
    {
        return "" + id;
    }

}
