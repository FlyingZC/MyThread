package com.zc.exercise;
//子线程循环10次，接着主线程循环100，接着又回到子线程循环10次，接着再回到主线程又循环100，如此循环50次
public class T07Thread {
	public static void main(String[] args) {
		final MyBusiness bus=new MyBusiness();
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=0;i<50;i++){
					bus.mainMethod();
				}
			}
		},"主线程:").start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=0;i<50;i++){
					bus.subMethod();
				}
			}
		},"子线程:").start();
	}
}


class MyBusiness{
	private boolean flag=true;//执行主线程
	public synchronized void mainMethod(){
		if(flag){
			notify();
			for(int i=0;i<100;i++){
				System.out.println(Thread.currentThread().getName()+i);
			}
			flag=false;
		}else{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void subMethod(){
		if(!flag){
			notify();
			for(int i=0;i<10;i++){
				System.out.println(Thread.currentThread().getName()+i);
			}
			flag=true;
		}else{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
