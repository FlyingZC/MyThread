package com.zc.zx;

import java.util.concurrent.ArrayBlockingQueue;

//生产者不断产生数据，消费者不断消费数据(doSome()方法处理,需要1秒)
//10个线程消费生产者产生的数据,依次有序的消费数据
public class T21 {
	public static void main(String[] args) {
		final ArrayBlockingQueue<String> q=new ArrayBlockingQueue<String>(10);
		for(int i=0;i<10;i++){
			new Thread(){
				public void run() {
					while(true){
					}
				};
			}.start();
		}
	}
	class Producer{
		public void product() throws InterruptedException{
			int i=0;
			while(true){
				i++;
			}
		}
	}
}

