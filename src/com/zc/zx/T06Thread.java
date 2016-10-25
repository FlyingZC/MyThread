package com.zc.zx;
//4个线程,其中两个每次对j加1,另外两个对j每次减1
//多个线程间的共享数据
public class T06Thread {
	public static void main(String[] args) {
		final ShareData data=new ShareData();
		for(int i=0;i<2;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						data.increment();
					}
				}
			},"增加线程"+i).start();
		}
		
		for(int i=0;i<2;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						data.decrement();
					}
				}
			},"减小线程"+i).start();
		}
	}
}

class ShareData{
	private int j=0;
	public synchronized void increment(){
		j++;
		System.out.println(Thread.currentThread().getName()+",j="+j);
	}
	public synchronized void decrement(){
		j--;
		System.out.println(Thread.currentThread().getName()+",j="+j);
	}
	
}
