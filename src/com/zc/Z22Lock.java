package com.zc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Z22Lock {
		private int j;
		private Lock lock = new ReentrantLock();
		public static void main(String[] args) {
			Z22Lock tt = new Z22Lock();
			for(int i=0;i<2;i++)
			{
				new Thread(tt.new Adder()).start();
				new Thread(tt.new Subtractor()).start();
			}
		}

		private class Subtractor implements Runnable
		{
			@Override
			public void run() {
				while(true)
				{
					/*synchronized (ThreadTest.this) {			
						System.out.println("j--=" + j--);
						//这里抛异常了，锁能释放吗？
					}*/
					lock.lock();
					try
					{
						System.out.println("j--=" + j--);
					}finally
					{
						lock.unlock();
					}
				}
			}
			
		}
		
		private class Adder implements Runnable
		{

			@Override
			public void run() {
				while(true)
				{
					/*synchronized (ThreadTest.this) {
					System.out.println("j++=" + j++);	
					}*/
					lock.lock();
					try
					{
						System.out.println("j++=" + j++);
					}finally
					{
						lock.unlock();
					}				
				}			
			}
			
		}

}
