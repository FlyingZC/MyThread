package com.zc;
public class Z02Join {
	public static void main(String[] args) {
		final Thread t2=new Thread("线程2"){
			public void run() {
				for(int i=0;i<100;i++){
					System.out.println(Thread.currentThread().getName()+":i="+i);
				}
			};
		};
		t2.start();
		
		new Thread("线程1"){
			public void run() {
				for(int i=0;i<100;i++){
					System.out.println(Thread.currentThread().getName()+":i="+i);
					if(i==20){
						try {
							t2.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
		}.start();
	}
}
