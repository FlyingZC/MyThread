package com.zc;

public class Test06Window3 {
	public static void main(String[] args){
		Window3 w=new Window3();
		Thread t1=new Thread(w);
		t1.start();
		Thread t2=new Thread(w);
		t2.start();
		Thread t3=new Thread(w);
		t3.start();
	}
}

class Window3 implements Runnable{
	int ticket=100;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			show();
		}
	}
	//同步方法.
	public synchronized void show(){
		if(ticket>0){
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"售票"+ticket--);
		}
	}
}