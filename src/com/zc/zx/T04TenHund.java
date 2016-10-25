package com.zc.zx;
//主线程100次,子线程10次.只实现互斥,不能按次序
public class T04TenHund {
	public static void main(String[] args) {
		new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=1;i<=50;i++){
					synchronized(T04TenHund.class){
						for(int j=1;j<=10;j++){
							System.out.println("子线程第"+i+"次,j="+j);
						}
					}
				}
			}
			
		}).start();
		
		for(int i=1;i<=50;i++){
			synchronized(T04TenHund.class){
				for(int j=1;j<=100;j++){
					System.out.println("主线程第"+i+"次,j="+j);
				}
			}
		}
		
	}
}
