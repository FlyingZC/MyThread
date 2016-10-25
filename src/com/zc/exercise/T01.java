package com.zc.exercise;
//子线程执行10次.主线程执行100次....有问题!!!
public class T01 {
/*	private CountBusiness countBusiness=new CountBusiness();
	public static void main(String[] args) {
		new Thread(new Runnable(){
			@Override
			public void run() {
				CountBusiness.countTen();
			}
		}).start();
		new Thread(c).start();
	}*/
}


/*class CountBusiness{
	public synchronized static void countTen(){
		notify();
		for(int i=1;i<10;i++){
			System.out.println("A:"+i);
		}
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public synchronized void countHun(){
		notify();
		for(int i=1;i<100;i++){
			System.out.println("B:"+i);
		}
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
*/