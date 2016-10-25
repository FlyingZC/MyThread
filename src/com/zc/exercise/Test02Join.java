package com.zc.exercise;

public class Test02Join {
	public static void main(String[] args) {
		
		final Thread t2=new Thread(new Runnable(){
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0;i<50;i++){
					System.out.println("线程2->"+i);
				}
			}});
		t2.start();
		Thread t1=new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					//在线程t1中调用t2.join().则t1等待t2结束后才执行
					t2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Thread.currentThread().setName("子线程1");
				for(int i=0;i<100;i++)
				{
					System.out.println(Thread.currentThread().getName()+"->"+i);
				}
			}});
		
		
		t1.start();
		
	}
	
}
