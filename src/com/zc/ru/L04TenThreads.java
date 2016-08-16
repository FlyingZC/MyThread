package com.zc.ru;

public class L04TenThreads {
	public static void main(String[] args) {
		int[][] arr=generateArr();
		EveryThread[] everyThread=new EveryThread[10];
		for(int i=0;i<10;i++){
			everyThread[i]=new EveryThread(arr[i]);
			everyThread[i].start();
			try {
				everyThread[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(everyThread[i].getMax());
			
		}
		
	}
	
	public static int[][] generateArr(){
		int[][] a=new int[10][100];
		for(int i=0;i<10;i++){
			for(int j=0;j<100;j++){
				a[i][j]=i*j;
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


