package com.zc.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
// FutureTask可以自己 run(),一般不这么用.可传入 Callable或 Runnable
public class T08FutureTask
{
    public static void main(String[] args) throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                Thread.sleep(10000);
                return "task1执行结果啊";
            }
        });
        // task.cancel(true);
        System.out.println(task.get());
        task.run();

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
