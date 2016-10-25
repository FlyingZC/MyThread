package com.zc;
/*
 * yield():暂停当前正在执行的线程对象，释放cpu资源.进入runnable态,
 * 并执行其他线程,仍有可能接着被自己抢占到资源(进入running态)。
 * 
 * join():在a线程中调用b线程的b.join()方法.A线程停止执行.直到b线程执行完毕再执行a线程
 * 
 * isAlive():判断当前线程是否还存活
 * 
 * sleep(long l):显式的让当前进程睡眠
 * 线程通信:wait() notify() notifyAll()
 * 设置线程优先级:setPriority().默认为5 1最小 10最高.10抢占到cpu的概率更大
 * 		getPriority()
 * */
public class T01Join{
	/*
	 * main线程,载启动两个线程myThread0,myThread1
	 * */
	public static void main(String[] args) throws InterruptedException
	{
		//线程默认有名.可以重命名
		Thread.currentThread().setName("主线程");
		
		MyThread myThread0=new MyThread();
		MyThread myThread1=new MyThread();
		
		myThread0.setName("子线程0");
		//设置优先级
		myThread0.setPriority(Thread.MAX_PRIORITY);
		myThread1.setName("子线程1");
		
		//3.启动另一条新的线程.会自动调用相应的run()方法
		//一个线程只能start()一次
		myThread0.start();
		myThread1.start();
		//下面主线程在调用完子线程后接着做自己的事情
		//两个线程并发执行.输出时候交替输出(但是没有规律).
		for(int i=1;i<=100;i++){
			System.out.println(Thread.currentThread().getName()+" "+Thread.currentThread().getPriority() +" : "+i);
			if(i%10==0){
				
				//yield():暂停当前正在执行的线程对象，释放cpu资源.并执行其他线程,仍有可能接着被自己抢占到资源。
				Thread.currentThread().yield();				
			}
			
			/*if(i==20){
				//当主线程执行到20.先让子线程myThread0执行完(myThread0仍然会和myThread1抢夺cpu).再执行主线程.
				myThread0.join();
			}*/
			//由于myThread0.join();已经执行完.死掉
			System.out.println("线程0是否还存活"+myThread0.isAlive());
			//Thread.currentThread().getName():获取当前线程名
		}
	}
}

class MyThread extends Thread{
	//1.创建一个继承于Thread的子类
		public void run(){
			//重写run()方法.
			for(int i=1;i<=100;i++){
				try {
					//1000ms.1秒
					Thread.currentThread().sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+" "+Thread.currentThread().getPriority()+" : "+i);
			}
		}
}