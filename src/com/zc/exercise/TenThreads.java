package com.zc.exercise;
/*开启十个线程.计算二维数组bigMatrix中的最大值*/
public class TenThreads {
	private static class WorkerThread extends Thread {
		int max = Integer.MIN_VALUE;
		int[] ourArray;

		public WorkerThread(int[] ourArray) {
			this.ourArray = ourArray;
		}

		// Find the maximum value in our particular piece of the array
		public void run() {
			for (int i = 0; i < ourArray.length; i++)
				max = Math.max(max, ourArray[i]);
		}

		public int getMax() {
			return max;
		}
	}

	public static void main(String[] args) {
		//声明十个线程(即一个线程数组)
		WorkerThread[] threads = new WorkerThread[10];
		int[][] bigMatrix = getBigHairyMatrix();

		int max = Integer.MIN_VALUE;
		//数组中每个元素是一个线程.启动线程
		for (int i = 0; i < 10; i++) {
			threads[i] = new WorkerThread(bigMatrix[i]);
			threads[i].start();
		}
		// join()等待每个线程结束
		try {
			for (int i = 0; i < 10; i++) {
				threads[i].join();
				//获取每个线程得到的最大值.再比较
				max = Math.max(max, threads[i].getMax());
			}
		} catch (InterruptedException e) {
			// fall through
		}
		System.out.println("Maximum value was " + max);
	}

	//获取二维数组
	private static int[][] getBigHairyMatrix() {
		int[][] a=new int[10][100];
		for(int i=0;i<10;i++){
			for(int j=0;j<100;j++){
				a[i][j]=(i+1)*(j+1);
			}
		}
		return a;
	}
}