package com.zc.exercise;

public class R09TongXin {
	public static void main(String[] args) {
		Tong tong=new Tong();
		new Thread(tong).start();
		new Thread(tong).start();
	}
}

class Tong implements Runnable{
	private int num=1;
	@Override
	public void run() {
		while(true){
			//java.lang.IllegalMonitorStateException.当wait()或notify()出现在同步代码块外时报错
			synchronized(this){
				notify();
				if(num<=100){
					System.out.println(Thread.currentThread()+"num:"+num++);
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else{
					break;
				}
			}
		}
	}
}