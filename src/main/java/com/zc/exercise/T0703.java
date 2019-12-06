package com.zc.exercise;
//zxx
public class T0703 {
	public static void main(String[] args) {
		new T0703().init();
	}

	public void init()
	{//只要保证是同一个Business对象即可
		final Business business = new Business();
		new Thread(
			new Runnable()
			{
				public void run() {
					for(int i=0;i<50;i++)
					{
						business.subThread(i);//子线程调用subThread
					}						
				}
			}
		).start();
		//主线程调用mainThread
		for(int i=0;i<50;i++)
		{
			business.mainThread(i);
		}		
	}
	
	private class Business
	{
		boolean bShouldSub = true;//这里相当于定义了控制该谁执行的一个信号灯
		public synchronized void mainThread(int i)
		{
			if(bShouldSub)
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
				
			for(int j=0;j<5;j++)
			{
				System.out.println(Thread.currentThread().getName() + ":i=" + i +",j=" + j);
			}
			bShouldSub = true;
			this.notify();
		}
		
		
		public synchronized void subThread(int i)
		{
			if(!bShouldSub)
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
				
			for(int j=0;j<10;j++)
			{
				System.out.println(Thread.currentThread().getName() + ":i=" + i +",j=" + j);
			}
			bShouldSub = false;				
			this.notify();			
		}
	}
}
