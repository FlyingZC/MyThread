package com.zc.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class T02Callable
{
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 5; i++)
        {
            futures.add(exec.submit(new SubCallable(i)));
        }
        for (Future<Integer> f : futures)
        {
            System.out.println(f.get());
        }
    }
}

class SubCallable implements Callable<Integer>
{
    private int i;

    public SubCallable(int i)
    {
        this.i = i;
    }

    @Override
    public Integer call() throws Exception
    {
        return i;
    }

}