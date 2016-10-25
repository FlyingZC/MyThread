package com.zc.zx;
//输出结果为:"Thread中的run方法"
//new Thread(){};创建了一个Thread的匿名内部类,
//是Thread的子类,其中的run方法相当于重写了父类中的run方法,所以理应执行外面的run()
//如果没有重写run(),会调用父类Thread中的run方法,此时target不为null,则会执行runnable里的run()
public class T02WhichRun {
	public static void main(String[] args) {
		new Thread(new Runnable(){
			@Override
			public void run() {//Runnable中的run方法
				System.out.println("Runnable中的run方法");
			}
		}){
			@Override
			public void run() {//Thread中的run方法
				System.out.println("Thread中的run方法");
			}
		}.start();
	}
}
