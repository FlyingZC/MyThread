package com.zc.z01demo;
//synchronized中的同步监视器为同一个对象.所以会加锁
/*
 * run()方法中存在	synchronized(this)代码块,
 * 而且t1和t2都是基于"runnable这个Runnable对象"创建的线程。
 * 这就意味着，我们可以将synchronized(this)中的this看作是“runnable这个Runnable对象”；
 * 因此，线程t1和t2共享“runnable对象的同步锁”(共享同一个对象的同步锁)。所以，当一个线程运行的时候，
 * 另外一个线程必须等待“运行线程”释放“runnable的同步锁”之后才能运行。
 * 
 * 第一条: 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，
 * 其他线程对“该对象”的该“synchronized方法”或者“synchronized代码块”的访问将被阻塞。
 * */
public class T12Synchronized {
	public static void main(String[] args){
		//同步监视器对象是同一个
		MyNewRunnable runnable=new MyNewRunnable();
		Thread t1=new Thread(runnable,"t1");
		Thread t2=new Thread(runnable,"t2");
		t1.start();
		t2.start();
	}
}

class MyNewRunnable implements Runnable{
	@Override
	public void run() {
		try {
			synchronized(this){
				for(int i=0;i<5;i++){
					Thread.sleep(100);
					System.out.println(Thread.currentThread().getName()+"->"+i);
				}				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
