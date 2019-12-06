package com.zc.exercise;

import java.util.Random;

public class T03Customer {
	public static void main(String[] args) {
		MProduct p=new MProduct();
		new Thread(new MCustomer(p),"消费者1").start();
		new Thread(new MCustomer(p),"消费者2").start();
		new Thread(new MProductor(p),"生产者1").start();
		new Thread(new MProductor(p),"生产者2").start();
	}
}

class MCustomer implements Runnable{
	private MProduct p;
	public MCustomer(MProduct p){
		this.p=p;
	}
	@Override
	public void run() {
		while(true){
			p.consume();
		}
	}
}

class MProductor implements Runnable{
	private MProduct p;
	public MProductor(MProduct p){
		this.p=p;
	}
	@Override
	public void run() {
		while(true){
			p.product();
		}
	}
}

class MProduct{
	private int count;
	Random rand=new Random();
	public synchronized void product(){
		if(count>=20){
			try {
				notify();//只要wait()就记得notify();成对出现
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			int ci=20-count+1;//用来产生随机数的上限
			int r=rand.nextInt(ci);//实际产生随机数的范围为[0,20-count],表示最多还能生产多少个
			//System.out.println("生产者随机数上限:"+ci+",产生的随机数:"+r);
			for(int i=1;i<=r;i++){
				System.out.println(Thread.currentThread().getName()
						+"生产了第"+(++count)+"件商品");
			}
			System.out.println("生产者count"+count);
			try {
				notify();
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public synchronized void consume(){
		if(count<=0){
			try {
				notify();
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			int ci=count+1;//随机数上限
			int r=rand.nextInt(ci);//实际产生随机数的范围[0,count],表示最多还能消费多少个
		//	System.out.println("消费者随机数上限:"+ci+",产生的随机数:"+r);
			for(int i=1;i<=r;i++){
				System.out.println(Thread.currentThread().getName()
						+"消费了第"+(count--)+"件商品");
			}
			System.out.println("消费者count"+count);
			try {
				notify();
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}