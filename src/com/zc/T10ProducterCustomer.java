package com.zc;
//生产者消费者
/*
 * 生产者(producter)将产品交给店员(Product).
 * 消费者(customer)从店员(Product)处取走产品
 * 店员(Product)一次只能维持固定数量的产品(20)
 * 如果生产者视图生产更多的产品.店员会叫生产者停一下
 * 若果店员有空位放产品.通知生产者继续生产.
 * 店中没有产品.店员会通知消费者等一下.店中有产品,通知消费者来取走产品
 * 
 * 分析:
 * 1.是否涉及多线程:是.生产者,消费者(同时)
 * 2.是否涉及共享数据:是.考虑线程安全
 * 3.共享数据是什么:产品数量
 * 4.是否涉及线程通信:存在生产者与消费者的通信
 * */
public class T10ProducterCustomer {
	public static void main(String[] args) {
		Product Product=new Product();
		Producer p1=new Producer(Product);
		Consumer c1=new Consumer(Product);
		Thread thread1=new Thread(p1);//一个生产者的线程
		Thread thread11=new Thread(p1);//另一个生产者线程
		Thread thread2=new Thread(c1);//一个消费者的线程
		thread1.setName("生产者1");
		thread11.setName("生产者2");
		thread2.setName("消费者者");
		thread1.start();
		thread11.start();
		thread2.start();
	}
}
//产品
class Product{
	int product;
	//生产
	public synchronized void addProduct(){
		if(product>=20){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			product++;
			System.out.println(Thread.currentThread().getName()+":生产了第"+product+"个产品");
			//只要能生产.就能唤醒消费者继续消费
			notify();
		}
	}
	//消费
	public synchronized void consumeProduct(){
		if(product<=0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println(Thread.currentThread().getName()+":消费了第"+product+"个产品");
			product--;
			//只要能消费.就能唤醒生产者继续生产
			notify();
		}
	}
	
}

//生产者
class Producer implements Runnable{
	Product Product;
	public Producer(Product Product){
		this.Product=Product;
	}
	@Override
	public void run() {
		System.out.println("生产者开始生产产品");
		while(true){
			Product.addProduct();		
		}
	}
	
}

class Consumer implements Runnable{
	Product Product;
	public Consumer(Product Product){
		this.Product=Product;
	}
	@Override
	public void run() {
		while(true){
			Product.consumeProduct();		
		}
	}
	
}
