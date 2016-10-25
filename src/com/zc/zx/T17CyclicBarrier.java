package com.zc.zx;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//循环路障,多个线程等待,集合好后再开始
public class T17CyclicBarrier {
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final  CyclicBarrier cb = new CyclicBarrier(3);//3个线程要到达
		for(int i=0;i<3;i++){
			Runnable runnable = new Runnable(){
					public void run(){
					try {
						Thread.sleep((long)(Math.random()*10000));//每个人休息一会	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点A,当前共有" + (cb.getNumberWaiting()+1) + "个已经到达" 
								+ (cb.getNumberWaiting()==2?",都到齐了继续走啊":"正在等待他人"));						
						cb.await();//到达目的地,等待他人,所有人都到了在继续向下执行
						
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点B,当前共有" + (cb.getNumberWaiting()+1) + "个已经到达" 
								+ (cb.getNumberWaiting()==2?",都到齐了继续走啊":"正在等待他人"));										
						cb.await();//新的集合点,所有线程都到达后才会继续执行	
						
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"即将到达集合地点C,当前共有" + (cb.getNumberWaiting()+1) + "个已经到达" 
								+ (cb.getNumberWaiting()==2?",都到齐了继续走啊":"正在等待他人"));																
						cb.await();						
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
			};
			service.execute(runnable);
		}
		service.shutdown();
	}
}
