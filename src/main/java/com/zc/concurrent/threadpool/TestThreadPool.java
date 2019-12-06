package com.zc.concurrent.threadpool;

import java.util.concurrent.ThreadPoolExecutor;

public class TestThreadPool {

    /**
     * 线程池配置
     */
    private static ThreadPoolParameter threadPoolParameter;

    /**
     * 获取线程池
     */
    private static ThreadPoolExecutor threadPoolExecutor;

    public static void main(String[] args) {
        threadPoolParameter = new ThreadPoolParameter();

        getThreadPoolExecutor().submit(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("test run");
            }
        }));
    }

    /**
     * 获取线程池
     *
     * @return 线程池
     */
    private static ThreadPoolExecutor getThreadPoolExecutor() {
        if (null == threadPoolExecutor) {
            threadPoolExecutor = ThreadPool.getInstance(threadPoolParameter).getPoolExecutor();
        }
        return threadPoolExecutor;
    }
}
