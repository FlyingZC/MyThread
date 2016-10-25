package com.zc.zx;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
//Semaphore可以维护当前访问自身的	线程个数	即当前资源可允许多个线程共同访问,只有超过上限,才不允许进入
public class T16Semaphore {
	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final Semaphore sp = new Semaphore(3);
		for (int i = 0; i < 10; i++) {
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						sp.acquire();//获取灯	
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					System.out.println("线程" + Thread.currentThread().getName()
							+ "进入,当前共有" + (3 - sp.availablePermits())//3-可获得的许可,得到剩余的可用的
							+ "个并发");
					try {
						Thread.sleep((long) (Math.random() * 10000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("线程" + Thread.currentThread().getName()
							+ "即将离开");
					sp.release();//释放灯
					System.out.println("线程" + Thread.currentThread().getName()
							+ "已经离开,当前共有" + (3 - sp.availablePermits())
							+ "个并发");
				}
			};
			service.execute(runnable);//执行线程池
		}
	}

}
