package com.zc;
/*
 * 运行后输出:
	(1)t1->NEW新建
	(2)t1->RUNNABLE开始
	(3)t1->RUNNABLE->1
	(4)t1->RUNNABLE->2
	(5)t1->TIMED_WAITING被interrupt
	(6)t1->RUNNABLE->catch exception
	(7)t1->TERMINATED
	
	(1)mian线程中	:	启动主线程 t1.start()	new->runnable
	(2)t1线程中:		(执行MyInThread中的run方法) (!isInterrupted())为真 输出第三四句(输出多少看sleep时间)
	(3)随后main线程抢占到cpu继续执行main方法中的代码.
	(4)main线程中:	t1.interrupt(); 随后在MyInThread中的run方法中catch住interrupt
 * */
public class T18Interrupt {
	public static void main(String[] args) {		
		try {
			Thread t1=new MyInThread("t1");//1.创建t1线程
			System.out.println(t1.getName()+"->"+t1.getState()+"新建");
			
			t1.start();//2.start时t1进入runnable态,并未running
			System.out.println(t1.getName()+"->"+t1.getState()+"开始");
			Thread.sleep(300);//3.main让出cpu资源,进入睡眠态(阻塞态),被t1抢占执行(t1进入运行态)
			t1.interrupt();//6.main线程中断t1线程的睡眠
			System.out.println(t1.getName()+"->"+t1.getState()+"被interrupt");
			
			Thread.sleep(300);
			System.out.println(t1.getName()+"->"+t1.getState());
		} catch (InterruptedException e) {
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
			while(!isInterrupted()){//4.未被中断,循环
				//5.main线程sleep300ms,所以当循环到大约第三次时,该具的sleep会让出时间片,
				//导致main线程进入running态
				Thread.sleep(100);
				i++;
				System.out.println(Thread.currentThread().getName()+"->"
				+Thread.currentThread().getState()+"->"+i);//(3)(4)
			}
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName()+"->"
		+Thread.currentThread().getState()+"->catch exception");//7.捕获被interrupt的异常
		}
	}
}
