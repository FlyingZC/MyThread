package com.zc.zx;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class CallableAndFuture
{
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException
    {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //线程执行完返回的结果
        //submit()可以有返回结果,execute没有
        Future<String> future = //future会一直等待,知道拿到结果
                threadPool.submit(new Callable<String>()
                {//执行完有返回结果
                    @Override
                    public String call() throws Exception
                    {
                        return "hello";//泛型类型与返回值类型相同
                    }
                });
        System.out.println("等待结果");
        //future.get():获取线程返回的结果.future.get(time,TimeUnit.SECONDS)
        System.out.println("拿到结果" + future.get());

        threadPool.shutdown();

        ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
        //CompletionService用于提交一组Callable任务,其take方法返回已完成任务的一个
        CompletionService<Integer> com = new ExecutorCompletionService<Integer>(threadPool2);
        for (int i = 0; i < 10; i++)
        {//提交十个任务
            final int seq = i;//
            com.submit(new Callable<Integer>()
            {
                @Override
                public Integer call() throws Exception
                {
                    Thread.sleep(new Random().nextInt(5000));
                    return seq;
                }
            });
        }
        for (int i = 0; i < 10; i++)
        {
            System.out.println(com.take().get());
        }
        threadPool2.shutdown();
    }
}
