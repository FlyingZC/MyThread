package com.zc.zx;
//创建线程
public class T01CreateThread {
	public static void main(String[] args) {
		Thread thread=new Thread(){
			@Override
			public void run() {
				while(true){//该线程一直执行
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
					System.out.println(this.getName());
				}
			}
		};
		thread.start();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){//该线程一直执行
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
				}
			}
		}).start();
		
	}
}
