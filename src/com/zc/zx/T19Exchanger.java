package com.zc.zx;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T19Exchanger {
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final Exchanger<String> exchanger = new Exchanger<String>();//交换不同线程间的数据
		service.execute(new Runnable(){
			public void run() {
				try {				

					String drug = "毒品";
					System.out.println("线程" + Thread.currentThread().getName() + 
					"带着" + drug +"前往交易的路上");
					Thread.sleep((long)(Math.random()*10000));
					String money = exchanger.exchange(drug);//会等待另一个线程到此,然后交换
					System.out.println("线程" + Thread.currentThread().getName() + 
					"交易成功拿到" + money);
				}catch(Exception e){
					
				}
			}	
		});
		service.execute(new Runnable(){
			public void run() {
				try {				

					String drug = "金钱";
					System.out.println("线程" + Thread.currentThread().getName() + 
					"带着" + drug +"前往交易的路上");
					Thread.sleep((long)(Math.random()*10000));					
					String money = exchanger.exchange(drug);//会等待另一个线程到此,然后交换
					//随后各干各的
					System.out.println("线程" + Thread.currentThread().getName() + 
					"交易成功拿到" + money);
				}catch(Exception e){
					
				}				
			}	
		});		
	}
}


