package com.zc;
/*进程通信:两个线程交替输出1-100
 * wait和notify用来处理线程之间的协作问题。比如说最经典的生产者-消费者模型
 * wait()放弃cpu,放弃同步资源.
 * notify():唤醒等待队列中优先级高的线程
 * notifyAll():唤醒等待队列中所有线程
 * 
 * !!!以上三个方法必须在synchronized里使用.不然报错java.lang.IllegalMonitorStateException;
 * 
 * wait,notify,notifyAll均是Object类中定义的方法.
 * 因为每个对象均可作为锁(同步监视器对象monitor)
 * 而notify,wait,notifyAll方法均是由该同步监视器对象调用的
 * notify用来唤醒和当前线程使用同一个同步监视器对象的某个线程
 * 
 * 一个线程被唤醒不代表立即获取了对象的monitor，
 * 只有等调用完notify()或者notifyAll()并退出synchronized块，
 * 释放对象锁后，其余线程才可获得锁执行。
 * */
public class T09TongXin {
	public static void main(String[] args){
		TongXin tongXin=new TongXin();
		Thread thread1= new Thread(tongXin);
		thread1.setName("线程1");
		Thread thread2= new Thread(tongXin);
		thread2.setName("线程2");
		thread2.start();
		thread1.start();
	}
}

class TongXin implements Runnable{
	int num=1;
	@Override
	public void run() {
			while (true) {
				synchronized (this) {
				//java.lang.IllegalMonitorStateException.当wait()或notify()出现在同步代码块外时报错
				//notify()唤醒等待队列中的优先级高的线程
				//synchronized代码块写在while(true)里
				this.notify();//只要在wait()调用之前唤醒即可
				if (num <= 100) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + ":"+ num++);
					try {
						//wait():写在循环体里.每输出一个数.就把该线程wait()
						this.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					break;//如果数字超过100,break.
				}
			}
		}
	}
}