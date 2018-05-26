package com.zc.z01demo;
//模拟售票窗口.共100张票.3个窗口卖.有线程安全问题.可能会出现几个窗口卖出同一张票
public class T03Window {
	public static void main(String[] args){
		Window window1=new Window();
		Window window2=new Window();
		Window window3=new Window();
		
		window1.setName("窗口1");
		window2.setName("窗口2");
		window3.setName("窗口3");
		window1.start();
		window2.start();
		window3.start();		
	}
}

class Window extends Thread{
    // 共享变量.票数
	static int ticket=100;
	public void run(){
		while(true){
			if(ticket>0){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+"售票号为:"+ticket--);
			}else{
				break;
			}
		}
	}
}
