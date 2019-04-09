package com.zc.z01demo;

public class T12Synchronized2 {
	public static void main(String[] args) {
		//synchronized中同步监视器对象this不是同一个对象.不能起到加锁的作用
		Thread t1=new MyNewThread("t1");
		Thread t2=new MyNewThread("t2");
		t1.start();
		t2.start();
	}
}

class MyNewThread extends Thread{
	public MyNewThread(String name) {
		super(name);
	}
	@Override
	public void run() {
		synchronized(this){
			for(int i=0;i<5;i++){
				System.out.println(Thread.currentThread().getName()+"->"+i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			}
		}
	}
}
