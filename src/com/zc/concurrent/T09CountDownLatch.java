package com.zc.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//倒计时计数器,当倒计时结束,所有等待者开始执行
//遇到await()就停住,等待计数为0后开始继续执行.使用countDown来减少计数
public class T09CountDownLatch {
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final CountDownLatch cdOrder = new CountDownLatch(1);//[1]计数初始化为1
		final CountDownLatch cdAnswer = new CountDownLatch(3);		
		for(int i=0;i<3;i++){
			Runnable runnable = new Runnable(){
					public void run(){
					try {
						System.out.println("线程" + Thread.currentThread().getName() + 
								"正准备接受命令");						
						cdOrder.await();//[3]线程会在这里停住,直到等待cdOrder计数减为0再接着往下执行
						System.out.println("线程"+ Thread.currentThread().getName() + 
						"已接受命令");								
						Thread.sleep((long)(Math.random()*10000));	
						System.out.println("线程" + Thread.currentThread().getName() + 
								"回应名利处理结果");						
						cdAnswer.countDown();//[4]每当线程执行到这,cdAnswer计数减1						
					} catch (Exception e) {
						e.printStackTrace();
					}				
				}
			};
			service.execute(runnable);
		}		
		try {
			Thread.sleep((long)(Math.random()*10000));
		
			System.out.println("线程" + Thread.currentThread().getName() + 
					"即将发布命令");		
			
			cdOrder.countDown();//[2]cdOrder计数减一,初始为1,即此时减为0,等待该计数的线程会得到执行.几个runnable中都在cdOrder.await():等待计数为0开始执行
			System.out.println("线程" + Thread.currentThread().getName() + 
			"已发送命令,正在等待结果");	
			
			cdAnswer.await();//[5]等到cdAnswer计数减为0,后面才会执行
			System.out.println("线程" + Thread.currentThread().getName() + 
			"已收到所有响应结果");	
		} catch (Exception e) {
			e.printStackTrace();
		}				
		service.shutdown();
	}
}


	