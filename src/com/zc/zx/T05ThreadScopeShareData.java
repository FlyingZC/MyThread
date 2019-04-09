package com.zc.zx;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
//多线程并发下map获取数据出问题
public class T05ThreadScopeShareData {
	private static int data=0;
	public static Map<Thread,Integer> threadData=new HashMap<Thread,Integer>();
	public static void main(String[] args) {
		threadData=Collections.synchronizedMap(threadData);
		for(int i=0;i<10;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					data=new Random().nextInt();
					System.out.println(Thread.currentThread().getName()+"放入数据"+data);
					threadData.put(Thread.currentThread(), data);
					new A().get();
					new B().get();
					new C().get();
				}
			}).start();
		}
	}
	
	static class A{
		public void get(){
			int getData=threadData.get(Thread.currentThread());
			System.out.println("A从"+Thread.currentThread().getName()+"获取数据"+getData);
		}
	}
	
	static class B{
		public void get(){
			int getData=threadData.get(Thread.currentThread());
			System.out.println("B从"+Thread.currentThread().getName()+"获取数据"+getData);
		}
	}
	static class C{
		public void get(){
			int getData=threadData.get(Thread.currentThread());
			System.out.println("C从"+Thread.currentThread().getName()+"获取数据"+getData);
	}
}
	
}

