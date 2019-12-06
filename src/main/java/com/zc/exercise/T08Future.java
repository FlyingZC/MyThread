package com.zc.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 主线程等待所有其他线程执行完毕
 */
public class T08Future {
    protected static ThreadPoolExecutor threadPoolExecutor;
    protected static String name = "zc-";

    public static void main(String[] args) {
        initThreadPoolExecutor();
        List<Future<Void>> results = new ArrayList<Future<Void>>();
        MyBusinessObject businesses[] = findBusinessObjects();
        for (int i = 0; i < businesses.length; i++) {// 提交 启动子容器的任务.并阻塞当前线程等待执行结果
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

    public static MyBusinessObject[] findBusinessObjects() {
        MyBusinessObject[] bs = new MyBusinessObject[10];
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
        public Void call() {
            business.method1();// 擦! 线程池会把异常吃掉.此处看看 business空指针
            System.out.println("-- hello callable --");
            return null;
        }
    }

//    private static class MyBusinessObject {
//        public void method1() {
//            System.out.println("-- my business --");
//        }
//    }

    public static void initThreadPoolExecutor() {
        BlockingQueue<Runnable> startStopQueue = new LinkedBlockingQueue<Runnable>();

        threadPoolExecutor = new ThreadPoolExecutor(
                getStartStopThreadsInternal(),
                getStartStopThreadsInternal(), 10, TimeUnit.SECONDS,
                startStopQueue,
                new StartStopThreadFactory(getName() + "-startStop-"));
    }

    public static String getName() {
        return (name);
    }

    private static int getStartStopThreadsInternal() {
        int result = 1;

        // Positive values are unchanged
        if (result > 0) {
            return result;
        }

        // Zero == Runtime.getRuntime().availableProcessors()
        // -ve  == Runtime.getRuntime().availableProcessors() + value
        // These two are the same
        result = Runtime.getRuntime().availableProcessors() + result;
        if (result < 1) {
            result = 1;
        }
        return result;
    }

    private static class StartStopThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public StartStopThreadFactory(String namePrefix) {
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
}

class MyBusinessObject {
    public void method1() {
        System.out.println("-- my business --");
    }
}