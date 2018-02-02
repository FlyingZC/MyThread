package com.zc.z01demo;
public class Z03Sleep {
	public static void main(String[] args) {
		final Thread t2=new Thread("线程1"){
			public void run() {
				for(int i=0;i<100;i++){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+":i="+i);
				}
			};
		};
		t2.start();
	}
}
