package com.zc.z01demo;
/*
 * 程序进入了死循环！
为什么会这样呢？这是因为,t1在“等待(阻塞)状态”时，被interrupt()中断；
此时，会清除中断标记[即isInterrupted()会返回false]，
而且会抛出InterruptedException异常[该异常在while循环体内被捕获]。
因此，t1理所当然的会进入死循环了。
解决该问题，需要我们在捕获异常时，额外的进行退出while循环的处理。
例如，在MyThread的catch(InterruptedException)中添加break 或 return就能解决该问题。
 * */
public class T19Interrupt2 {
	public static void main(String[] args) {
		Thread t1=new MyIntThread();
		t1.start();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t1.interrupt();
	}
}

class MyIntThread extends Thread{
	
	@Override
	public void run() {
		synchronized(this){
			int i=0;
			while(!interrupted()){
				try {
					Thread.sleep(100);
					i++;
					System.out.println(Thread.currentThread().getName()+"->"+i);
				} catch (InterruptedException e) {
					System.out.println("catch");
					//return;
					//break;
				}
				
			}
		}
	}
}
