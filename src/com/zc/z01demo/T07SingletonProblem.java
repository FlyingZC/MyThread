package com.zc.z01demo;
//启动十条线程创建测试懒汉式,发现有线程安全问题
public class T07SingletonProblem {
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					for(int i=0;i<100;i++){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Single.getInstance());
					}
				}
			}).start();
		}
	}
}
class Single{
	private Single(){
		
	}
	private static Single instance;
	public  static Single getInstance(){
		if(instance==null){//在这一行打断点,即可发现并发问题.多个线程可能同时判断instance==null,从而创建了不同的Single对象
			instance=new Single();
		}
		return instance;
	}
}