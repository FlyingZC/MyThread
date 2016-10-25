package com.zc;

public class Test20Customer {
	static Good good=new Good();
	public static void main(String[] args) {
		Thread pro=new Thread(new Runnable(){
			@Override
			public void run() {
				synchronized(Test20Customer.class){
						try {
							if(good.getCount()>=30){
								Test20Customer.class.wait();
							}else{
								good.setCount(good.getCount()+1);
								System.out.println("生产者生产了第"+good.getCount()+"个商品");
								Test20Customer.class.notify();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			}
		});

		Thread cust = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (Test20Customer.class) {
					try {
						if (good.getCount() <= 0) {
							Test20Customer.class.wait();
						} else {
							good.setCount(good.getCount() - 1);
							System.out.println("消费者消费了第" + good.getCount()
									+ "个商品");
							Test20Customer.class.notify();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		cust.setName("生产者");
		pro.setName("消费者");
		
		cust.start();
		pro.start();
	}
	
	
}

class Customer{
	
}

class Productor{
	
}

class Good{
	static int count;
	public Good(){
		count=0;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}