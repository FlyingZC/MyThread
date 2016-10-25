package com.zc.zx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//子线程循环10次,主线程循环100次,重复五十次.
//使用condition		await()和signal()实现
public class T14Condition {
	public static void main(String[] args) {
		final CountBusiness business=new CountBusiness();
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=1;i<=50;i++){
					business.sub(i);
				}
			}
		}).start();
		
		for(int i=1;i<=50;i++){
			business.main(i);
		}
		
	}
}

class CountBusiness{
	Lock lock=new ReentrantLock();
	
	Condition condition=lock.newCondition();
	private boolean bShouldSub=true;
	public void sub(int count){
		lock.lock();
		try{
			//推荐使用while,而不是if.因为在等待Condition时,允许发生虚假唤醒.
			//所以建议Condition在蓄暖中等待,当bShouldSub满足条件时,照样执行while外的代码,不会死循环
			while(!bShouldSub){
				try {
					condition.await();//await();等!!!
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int i=1;i<=10;i++){
				System.out.println(Thread.currentThread().getName()+"第"+count+"次,i="+i);
			}
			bShouldSub=false;
			condition.signal();//相当于notify()
		}finally{
			lock.unlock();
		}
	}
	public void main(int count){
		lock.lock();
		try{
			while(bShouldSub){
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int i=1;i<=100;i++){
				System.out.println(Thread.currentThread().getName()+"第"+count+"次,i="+i);
			}
			bShouldSub=true;
			condition.signal();
		}finally{
			lock.unlock();
		}
	}
}