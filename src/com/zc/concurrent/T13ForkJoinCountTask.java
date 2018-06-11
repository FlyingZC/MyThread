package com.zc.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class T13ForkJoinCountTask
{
    public static void main(String[] args) throws Exception
    {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //生成一个计算任务.负责计算1+2+3+4
        ForkJoinCountTask task = new ForkJoinCountTask(1, 4);
        //执行一个任务
        Future<Integer> result = forkJoinPool.submit(task);
        System.out.println(result.get());
    }

}

class ForkJoinCountTask extends RecursiveTask<Integer>
{
    //阈值
    private static final int THRESHOLD = 2;

    private int start;

    private int end;

    public ForkJoinCountTask(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute()
    {
        int sum = 0;
        //若任务足够小就计算任务
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute)
        {
            for (int i = start; i <= end; i++)
            {
                sum += i;
            }
        }
        //若任务大于阈值,则分裂成两个子任务
        else
        {
            int middle = (end - start) / 2;
            ForkJoinCountTask leftTask = new ForkJoinCountTask(start, middle);
            ForkJoinCountTask rightTask = new ForkJoinCountTask(middle + 1, end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();
            //等待子任务执行完毕,并得到结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            //合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }

}
