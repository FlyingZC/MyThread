package com.zc.zx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T08ThreadPool {
	public static void main(String[] args) {
		//工具类s:内部有一些静态方法
		//创建固定数量的线程池
		//ExecutorService threadPool=Executors.newFixedThreadPool(3);
		//创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。可能会创建很多个线程
		//ExecutorService threadPool=Executors.newCachedThreadPool();
		//保证线程池中永远只有一个线程
		ExecutorService threadPool=Executors.newSingleThreadExecutor();//相当于一个线程死亡后,再创建一个新的线程
		for(int i=0;i<10;i++){
			Runnable runnable=new Runnable(){
				@Override
				public void run() {
					for(int i=0;i<10;i++){
						System.out.println(Thread.currentThread().getName()+"循环了"+i);
					}
				}
			};
			threadPool.execute(runnable);
		}
		//默认所有线程执行完,线程池不会关闭,eclipse控制台红色按钮
		threadPool.shutdown();//所有线程任务执行完shutdown
		//threadPool.shutdownNow();//强制shutdown,无论此时线程池中的任务是否执行完成
	}
}
