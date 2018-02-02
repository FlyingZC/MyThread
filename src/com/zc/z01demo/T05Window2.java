package com.zc.z01demo;
//使用实现Runnable接口的方式.售票.同步代码块
/*Thread-1售票-0
 *Thread-2售票-1
 *可能会出错.因为当最后只剩一张票.进程1执行.然后sleep()
 *进程2执行.sleep()...
 *多线程安全问题的原因
 *由于一个线程在操作共享数据过程中.未执行完毕的情况下.另一个线程也访问共享资源.导致存在安全问题
 *必须让一个线程操作共享数据完毕后.其他线程才能进行操作该资源
 *
 *线程的同步机制
 *	方式1:同步代码块
 *	synchronized(同步监视器对象){
 *		//需要被同步的代码块(即为操作共享数据的代码)
 *	}
 *	同步监视器(锁):由任意一个类的对象来充当.哪个线程获取此监视器.谁就执行大括号里被同步的代码.
 *	方式2:同步方法
 *	方式3:加锁
 */
public class T05Window2 {
	public static void main(String[] args){
		Window2 w=new Window2();
		Thread t1=new Thread(w);
		t1.start();
		Thread t2=new Thread(w);
		t2.start();
		Thread t3=new Thread(w);
		t3.start();
	}
}

class Window2 implements Runnable{
	//共享资源
	int ticket=100;
	//同步监视器.锁
	public void run(){
		while(true){
			synchronized (this){//同一个同步监视器对象w
				if(ticket>0){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"售票"+ticket--);
				}else{
					break;
				}
			}
		}
	}
	
}