package com.zc.zx;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class T10Lock {
	public static void main(String[] args) {
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					new Outputer().output("zccccccccc");
				}
			}
		}).start();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					new Outputer().output("xdddddddd");
				}
			}
		}).start();
		
	}
	static class Outputer{
		//创建锁对象	一个可重入的互斥锁 Lock
		Lock lock=new ReentrantLock();
		public void output(String name){
			int len=name.length();
			lock.lock();//加锁
			try{
				synchronized(Outputer.class){
					for(int i=0;i<len;i++){
						//若此时抛异常,不会释放锁.所以建议在finally里unlock()
						System.out.print(name.charAt(i));
					}
					System.out.println();
				}
			}finally{//可以没有catch块
				lock.unlock();//在finally中解锁
			}
		}
	}
}
