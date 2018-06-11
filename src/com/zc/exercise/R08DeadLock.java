package com.zc.exercise;

public class R08DeadLock {
	static StringBuffer s1=new StringBuffer();
	static StringBuffer s2=new StringBuffer();
	public static void main(String[] args) {
		new Thread(new Runnable(){
			@Override
			public void run() {
				synchronized(s1){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					s1.append("a");
					s1.append(s2);
					System.out.println(s1);
				}
			}
		}).start();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				synchronized(s2){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					s2.append("a");
					s2.append(s1);
					System.out.println(s2);
				}
			}
		}).start();

	}
}
