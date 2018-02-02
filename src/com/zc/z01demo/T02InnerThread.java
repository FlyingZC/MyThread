package com.zc.z01demo;
//匿名内部类创建线程
public class T02InnerThread {
	public static void main(String[] args){
		//继承于Thread类的匿名内部类对象
		new Thread(){
			@Override
			public void run(){
				for(int i=1;i<=100;i++){
					if(i%2==0){
						System.out.println(Thread.currentThread().getName()+":"+i);
					}
				}
			}
		}.start();
		
		new Thread(){
			public void run(){
				for(int i=1;i<=100;i++){
					if(i%2!=0){
						System.out.println(Thread.currentThread().getName()+":"+i);
					}
				}
			}
		}.start();
	}
}
