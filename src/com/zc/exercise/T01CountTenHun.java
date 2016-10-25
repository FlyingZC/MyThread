package com.zc.exercise;
//子线程执行10次.主线程执行100次....
public class T01CountTenHun {
	public static void main(String[] args) {
		new Thread(new Runnable(){//一定先创建子线程,因为后面main死循环
			@Override
			public void run() {
				while(true){
					synchronized (T01CountTenHun.class) {
						T01CountTenHun.class.notify();
						for(int i=1;i<=10;i++){
							System.out.println(Thread.currentThread().getName()+i);
						}
						try {
							T01CountTenHun.class.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}}).start();		
		while(true){
			synchronized(T01CountTenHun.class){
				while(true){
					T01CountTenHun.class.notify();
					for(int i=1;i<=100;i++){
						System.out.println(Thread.currentThread().getName()+i);
					}			
					try {
						T01CountTenHun.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
