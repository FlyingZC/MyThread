package com.zc.exercise;

public class T06Customer {
	public static void main(String[] args) {
		Product p=new Product();
		Consumer c1=new Consumer(p);
		Consumer c2=new Consumer(p);
		Productor p1=new Productor(p);
		Productor p2=new Productor(p);
		
		new Thread(c1,"消费者1").start();
		new Thread(c2,"消费者2").start();
		new Thread(p1,"生产者1").start();
		new Thread(p2,"生产者2").start();
	}
}

class Product{
	private int count;
	public synchronized void consume(){
		if(count<=0){
			System.out.println("没有商品可以消费");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println(Thread.currentThread().getName()+"消费了第"+count+"个商品");
			count--;
			notify();
		}
	}
	public synchronized void produce(){
		if(count>=20){
			System.out.println("商品数量过多,停止生产");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println(Thread.currentThread().getName()+"生产了第"+count+"个商品");
			count++;
			notify();
		}
	}
}

class Consumer implements Runnable{
	Product p;
	public Consumer(Product p){
		this.p=p;
	}
	@Override
	public void run() {
		while(true){
			p.consume();
		}
	}
}

class Productor implements Runnable{
	Product p;
	public Productor(Product p){
		this.p=p;
	}
	@Override
	public void run(){
		while(true){
			p.produce();
		}
	}
}



