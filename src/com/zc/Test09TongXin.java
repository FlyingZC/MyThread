package com.zc;
/*进程通信:两个线程交替输出1-100
 * wait()放弃cpu,放弃同步资源.
 * notify():唤醒等待队列中优先级高的线程
 * notifyAll():唤醒等待队列中所有线程
 * 
 * 以上三个方法必须在synchronized里使用.不然报错java.lang.IllegalMonitorStateException;
 * */
public class Test09TongXin {
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
					// TODO Auto-generated method stub
				//唤醒等待队列中的优先级高的线程.notify():写在while(true)里.原因??
				notify();
				if (num <= 100) {
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + ":"
							+ num);
					num++;
					try {
						//wait():写在循环体里.每输出一个数.就把该线程wait()
						wait();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					break;
				}
			}
		}
		
	}
	
}