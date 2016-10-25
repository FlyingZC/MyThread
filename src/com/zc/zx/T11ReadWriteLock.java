package com.zc.zx;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//多个读锁不互斥,读锁和写锁呼出,写锁和写锁互斥
public class T11ReadWriteLock {
	public static void main(String[] args) {
		final MyData data=new MyData();
		for(int i=0;i<3;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						data.get();
					}
				}
			}).start();
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						data.put();
					}
				}
			}).start();
		}
	}
}

class MyData{
	private Integer data;
	//ReadWriteLock为接口		ReentrantReadWriteLock为实现类
	private	ReadWriteLock readWriteLock=new ReentrantReadWriteLock(); 
	public void get(){
		//加读锁,不会影响其他线程读
		readWriteLock.readLock().lock();
		try{
			System.out.println(Thread.currentThread().getName()+"读取data之前");
			System.out.println(Thread.currentThread().getName()+"读取了"+data);
			Thread.sleep((long) (Math.random()*2000));
			System.out.println(Thread.currentThread().getName()+"读取data之后");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			readWriteLock.readLock().unlock();
		}
	}
	
	public void put(){
		readWriteLock.writeLock().lock();
		try{
			System.out.println(Thread.currentThread().getName()+"修改data之前");
			data=new Random().nextInt();
			System.out.println(Thread.currentThread().getName()+"修改了"+data);
			try {
				Thread.sleep((long) (Math.random()*2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"修改data之后");
		}finally{
			readWriteLock.writeLock().unlock();
		}
	}
	
	
	
	
	
	
	
}
