package com.zc.think;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask extends LiftOff{
	@Override
	public void run() {
		while(countDown-->0){
			System.out.println(status());
			//
			try {
				//Old style
				//Thread.sleep(100);
				//java se5
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ExecutorService exec=Executors.newCachedThreadPool();
		for(int i=0;i<5;i++){
			exec.execute(new SleepingTask());
			exec.shutdown();
		}
	}
}
