package com.zc.think;

public class MainThread {
	public static void main(String[] args) {
		LiftOff launch=new LiftOff();
		launch.run();
		
		//LiftOff类是实现Runnable接口
		Thread t=new Thread(new LiftOff());
		t.start();
		
		//启动多个线程
		for(int i=0;i<5;i++){
			new Thread(new LiftOff()).start();
		}
	}
}
