package com.zc;
/*
 * 运行后输出:
 * 		t1->NEW新建
		t1->RUNNABLE开始
		t1->RUNNABLE->1
		t1->RUNNABLE->2
		t1->RUNNABLE->catch exception
		t1->TIMED_WAITING被interrupt
		t1->TERMINATED
		
		(1)mian线程中启动主线程 t1.start()	new->runnable
		(2)t1线程(执行MyInThread中的run方法) (!isInterrupted())为真 输出第三四句(输出多少看sleep时间)
		(3)随后main线程抢占到cpu继续执行main方法中的代码.
		(4)t1.interrupt(); 随后在MyInThread中的run方法中catch住interrupt
 * */
public class Test18Interrupt {
	public static void main(String[] args) {		
		try {
			Thread t1=new MyInThread("t1");
			System.out.println(t1.getName()+"->"+t1.getState()+"新建");
			
			t1.start();
			System.out.println(t1.getName()+"->"+t1.getState()+"开始");
			Thread.sleep(300);
			t1.interrupt();
			System.out.println(t1.getName()+"->"+t1.getState()+"被interrupt");
			
			Thread.sleep(300);
			System.out.println(t1.getName()+"->"+t1.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class MyInThread extends Thread{
	public MyInThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		try {
				int i=0;
				//
				while(!isInterrupted()){
					Thread.sleep(100);
					i++;
					System.out.println(Thread.currentThread().getName()+"->"+Thread.currentThread().getState()+"->"+i);
				}
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName()+"->"+Thread.currentThread().getState()+"->catch exception");
		}
	}
}
