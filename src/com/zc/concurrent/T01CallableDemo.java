package com.zc.concurrent;

//: concurrency/CallableDemo.java
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
// 线程池提交Callable
public class T01CallableDemo
{
    public static void main(String[] args)
    {
        ExecutorService exec = Executors.newCachedThreadPool();
        // Future保存 线程池执行Callable返回的结果,泛型参数类型为String. 将所有Future对象放入results中
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++)
        {
            // submit()方法会产生Future对象.它用Callable返回结果的特定类型进行了参数化.
            // 可以使用isDone()来查询Future是否已经完成.
            // 当任务完成时,它具有一个结果,可调用get()来获取该结果.
            // 也可以不用isDone()来检查就直接调用get().这种情况下get()将阻塞,直到结果准备就绪
            // 使用ArrayList<Future<String>>来保存每个callable执行方法call中返回的结果
            results.add(exec.submit(new TaskWithResult(i)));// 提交任务
        }
        for (Future<String> fs : results)
            try
            {
                // get() blocks until completion.get()方法会阻塞,直到结果准备就绪
                System.out.println(fs.get());
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
                return;
            }
            catch (ExecutionException e)
            {
                System.out.println(e);
            }
            finally
            {
                // 获取所有执行结果后,结束线程池
                exec.shutdown();
            }
    }
}

// 实现Callable接口,此时泛型为String,则返回结果Future的泛型参数也是String类型
class TaskWithResult implements Callable<String>
{
    private int id;

    public TaskWithResult(int id)
    {
        this.id = id;
    }

    /**
     * Future返回结果保存的是 这个Call方法返回的内容.注意Callable的泛型参数String 就是这个方法的返回值类型
     * */
    @Override
    public String call()
    {
        return "result of TaskWithResult " + id;
    }
}
/* Output:
  result of TaskWithResult 0
  result of TaskWithResult 1
  result of TaskWithResult 2
  result of TaskWithResult 3
  result of TaskWithResult 4
  result of TaskWithResult 5
  result of TaskWithResult 6
  result of TaskWithResult 7
  result of TaskWithResult 8
  result of TaskWithResult 9
  *///:~
