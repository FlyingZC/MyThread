package com.zc.exercise;

public class SyncExample {
	private static Object lockObject = new Object();
	static int x;
	static int y;

	private static class Thread1 extends Thread {
		public void run() {
			x=0;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("a"+x);
				System.out.println("b"+y);
				y = 0;
			
			}
		
	}

	private static class Thread2 extends Thread {
		public void run() {
			synchronized (lockObject) {
				x = 1;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				y=1;
				System.out.println(x+""+y);
			}
		}
	}

	public static void main(String[] args) {
		while(true){
			new Thread1().run();
			new Thread2().run();
			
		}
	}
}
