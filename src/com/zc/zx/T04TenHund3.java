package com.zc.zx;

public class T04TenHund3 {
	public static void main(String[] args) {
		final MyBusiness business=new MyBusiness();
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

class MyBusiness{
	private boolean bShouldSub=true;
	public synchronized void sub(int count){
		if(!bShouldSub){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i=1;i<=10;i++){
			System.out.println("子"+count+"次,i="+i);
		}
		bShouldSub=false;
		notify();
	}
	
	public synchronized void main(int count){
		if(bShouldSub){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i=1;i<=100;i++){
			System.out.println("主线程第"+count+"次,i="+i);
		}
		bShouldSub=true;
		notify();
	}
	
}