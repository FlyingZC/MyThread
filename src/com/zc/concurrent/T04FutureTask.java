package com.zc.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
// FutureTask可以自己run(),一般不这么用.可传入Callable或Runnable
public class T04FutureTask
{
    public static void main(String[] args) throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                return "task1执行结果啊";
            }
        });
        // task.cancel(true);
        task.run();
        System.out.println(task.get());
        
        // 传入Runnable
        FutureTask<String> task2 = new FutureTask<String>(new Runnable()
        {

            @Override
            public void run()
            {

            }

        }, "这是task2的执行结果啊");// 执行完毕get会返回后面的结果
        // task.cancel(true);
        task2.run();
        System.out.println(task2.get());
    }
}
