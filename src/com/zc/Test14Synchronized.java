package com.zc;
//第二条: 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，其他线程仍然可以访问“该对象”的非同步代码块。
public class Test14Synchronized {
	public static void main(String[] args) {
		NewRunnable runnable=new NewRunnable();
		Thread t1=new Thread(runnable,"t1");
		Thread t2=new Thread(runnable,"t2");
		t1.start();
		t2.start();
	}
}

class NewRunnable implements Runnable{
	private MyBusiness business=new MyBusiness();
	@Override
	public void run() {
		business.synMethod();
		business.normalMethod();
	}
}

class MyBusiness{
	public synchronized void synMethod(){
		for(int i=0;i<5;i++){
			System.out.println(Thread.currentThread().getName()+"->synMethod->"+i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void normalMethod(){
		for(int i=0;i<5;i++){
			System.out.println(Thread.currentThread().getName()+"->normalMethod->"+i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}