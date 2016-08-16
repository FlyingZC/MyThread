package com.zc.exercise;
//子线程执行10次.主线程执行100次....
public class Test01 {
	public static void main(String[] args) {
		while(true){
			synchronized(Test01.class){
				for(int i=0;i<100;i++){
					System.out.println(Thread.currentThread().getName()+i);
				}			
			}
			
			new Thread(new Runnable(){
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					synchronized (Test01.class) {
						for(int i=0;i<10;i++){
							System.out.println(Thread.currentThread().getName()+i);
						}	
					}
				}}).start();			
		}
		
	}
}
