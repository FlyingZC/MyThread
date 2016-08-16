package com.zc.exercise;
/*
 * 重要:线程通信.实现子线程输出1-100完后主线程输出1-10.重复
 * */
public class Test03 {
	static Business bs=new Business();
	public static void main(String[] args) {
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=0;i<50;i++){
					bs.sub(i);					
				}
			}}).start();
		
		for(int i=0;i<50;i++){
			bs.main(i);
		}
	}
}

class Business{
	private boolean flag=true;
	public synchronized void sub(int i){
		//当flag为true时.执行子线程.为false则wait(),等待
		if(!flag){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int j=0;j<100;j++){
			System.out.println("子线程->"+j);
		}
		//子线程执行完.将flag值为true.让主线程执行
		flag=false;
		//唤醒其他线程
		this.notify();
	}
		
	public synchronized void main(int i){
		if(flag){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int j=0;j<100;j++){
			System.out.println("主线程->"+j);
		}
		flag=true;
		this.notify();	
	}
	
}
