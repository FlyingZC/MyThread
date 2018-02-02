package com.zc.z01demo;
public class Z01Yield {
	public static void main(String[] args) {
		new Thread("线程1"){
			public void run() {
				for(int i=0;i<100;i++){
					System.out.println(Thread.currentThread().getName()+":i="+i);
					if(i==20){
						Thread.yield();
					}
				}
			};
		}.start();
		
		new Thread("线程2"){
			public void run() {
				for(int i=0;i<100;i++){
					System.out.println(Thread.currentThread().getName()+":i="+i);
				}
			};
		}.start();
	}
}
