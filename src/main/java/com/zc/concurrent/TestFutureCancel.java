package com.zc.concurrent;

import java.util.concurrent.*;
// 验证future.get()超时取消,目前有问题.TODO fixme
public class TestFutureCancel {
    public static void main(String[] args) {
        FutureTask<String> task = new FutureTask<String>(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                System.out.println("任务开始执行");
                Thread.sleep(20000);
                System.out.println("任务执行完毕");
                return "task1执行结果啊";
            }
        });
        try {
            task.run();
            // 阻塞获取一秒
            String result = task.get(1L, TimeUnit.SECONDS);
            System.out.println("future获取到的任务执行结果: " + result);
            System.out.println("准备cancel任务");
            task.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
