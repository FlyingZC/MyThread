package com.zc;

public class Test11Thread {
	public static void main(String[] args) {
		//执行Thread里的run方法而不是Runnable里的run方法.因为start的是外面的
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Runnable里"+Thread.currentThread().getName());					
				}
			}
			
		}){
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());					
				}
			};
		}.start();
		
	}
}
