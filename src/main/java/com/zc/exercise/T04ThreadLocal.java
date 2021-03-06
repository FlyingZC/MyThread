package com.zc.exercise;

import java.util.Random;

public class T04ThreadLocal {
	//线程范围内共享变量
	static ThreadLocal<Integer> x=new ThreadLocal<Integer>();
	public static void main(String[] args) {
		//启动四个线程
		for(int i=0;i<4;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					int data=new Random().nextInt();
					System.out.println(Thread.currentThread().getName()+"放入了"+data);
					//放入数据到ThreadLocal中
					x.set(data);
					new A().get();
					new B().get();
					
				}}).start();
		}
	}
//内部类可用static修饰
	static class A{
		public void get(){
			//取出数据
			int data=x.get();
			System.out.println("A获取"+Thread.currentThread().getName()+data);
		}
	}
	
	static class B{
		public void get(){
			int data=x.get();
			System.out.println("B获取"+Thread.currentThread().getName()+data);
		}
	}
}










