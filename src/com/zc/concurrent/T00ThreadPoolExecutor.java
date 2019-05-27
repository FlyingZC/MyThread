package com.zc.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class T00ThreadPoolExecutor {

    protected static ThreadPoolExecutor threadPoolExecutor;

    protected static String name = "zc-";

    // 队列数, null代表无界
    private static final Integer QUEUE_SIZE = 5;

    // 任务数
    private static final int TASK_SIZE = 10;

    public static void main(String[] args) {

        initThreadPoolExecutor(QUEUE_SIZE);

        List<Future<Void>> results = new ArrayList<Future<Void>>();

        MyBusinessObject businesses[] = findBusinessObjects(TASK_SIZE);
        for (int i = 0; i < businesses.length; i++) {// 提交 启动子容器的任务.并阻塞当前线程等待执行结果
            // 分 execute()方式 和 submit()方式
            results.add(threadPoolExecutor.submit(new MyCallable(businesses[i])));// 子容器使用startStopExecutor调用新线程来启动
        }

        boolean fail = false;
        for (Future<Void> result : results) {// 启动 多个子容器的结果
            try {
                result.get();// 阻塞住等待所有执行结果返回
            } catch (Exception e) {
                // log.error(sm.getString("containerBase.threadedStartFailed"), e);
                fail = true;
            }
        }
        if (fail) {

        }
    }

    public static MyBusinessObject[] findBusinessObjects(int size) {
        MyBusinessObject[] bs = new MyBusinessObject[size];
//        for (MyBusinessObject b : bs) {
//            b = new MyBusinessObject();
//        }
        for (int i = 0; i < bs.length; i++) {
            bs[i] = new MyBusinessObject();
        }
        return bs;
    }

    private static class MyCallable implements Callable<Void> {

        private MyBusinessObject business;

        public MyCallable(MyBusinessObject business) {
            this.business = business;
        }

        @Override
        public Void call() throws Exception {
            // 如果某个任务执行出现异常，那么执行任务的线程会被关闭，而不是继续接收其他任务。然后会启动一个新的线程来代替它
//            try {
                System.out.println("-- hello before callable --");
                business = null;// TODO 空指针异常被线程池吃掉了
                business.method1();// 擦! 线程池会把异常吃掉.此处看看 business空指针
                System.out.println("-- hello callable --");
//            } catch (Exception e) {
//                System.out.println(Thread.currentThread().getName() + "执行出错");
//            }
            return null;
        }

    }

    public static void initThreadPoolExecutor(Integer queueSize) {

        BlockingQueue<Runnable> workQueue = queueSize == null ?
                new LinkedBlockingQueue<Runnable>() : new LinkedBlockingQueue<Runnable>(queueSize);

        threadPoolExecutor = new ThreadPoolExecutor(
                getCorePoolSize(),
                getMaxPoolSize(), 10, TimeUnit.SECONDS,
                workQueue,
                new MyThreadFactory(getName() + "-my-"));
    }

    public static String getName() {
        return (name);
    }

    private static int getCorePoolSize() {
        return 2;
    }

    private static int getMaxPoolSize() {
        return Runtime.getRuntime().availableProcessors() + 1;
    }

    // 线程工厂
    private static class MyThreadFactory implements ThreadFactory {

        private final ThreadGroup group;

        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final String namePrefix;

        public MyThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    }

    private static class MyBusinessObject {
        public void method1() {
            System.out.println("-- my business --");
        }
    }
}

