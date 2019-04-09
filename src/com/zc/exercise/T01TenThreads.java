package com.zc.exercise;
//生成1-1000的二维数组,启动十个线程,分别得到每一行的最大值,join
public class T01TenThreads {
	public static void main(String[] args) {
		int[][] arr=generateArr();
		EveryThread[] everyThread=new EveryThread[10];
		for(int i=0;i<10;i++){
			everyThread[i]=new EveryThread(arr[i]);
			everyThread[i].start();
			try {
				everyThread[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(everyThread[i].getMax());
		}
		
	}
	
	public static int[][] generateArr(){
		int[][] a=new int[10][100];
		for(int i=1;i<=10;i++){
			for(int j=1;j<=100;j++){
				a[i-1][j-1]=i*j;
			}
		}
		return a;
	}
}

class EveryThread extends Thread{
	int[] generateArr=new int[100];
	int max;
	public EveryThread(int[] generateArr){
		this.generateArr=generateArr;
	}
	@Override
	public void run() {
		doMax();
	}
	
	public void doMax(){
		for(int i=0;i<generateArr.length;i++){
			max=Math.max(max,generateArr[i]);
		}
	}
	
	public int getMax(){
		return max;
	}
}


