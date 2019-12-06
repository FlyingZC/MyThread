package com.zc.z02demo;
// 创建了10个线程，每个线程都会等待前一个线程结束才会继续运行。
public class JoinDemo {
	    public static void main(String[] args) {
	        Thread previousThread = Thread.currentThread();
	        for (int i = 1; i <= 10; i++) {
	            Thread curThread = new JoinThread(previousThread);
	            curThread.start();
	            previousThread = curThread;
	        }
	    }
	
	    static class JoinThread extends Thread {
	        private Thread thread;
	
	        public JoinThread(Thread thread) {
	            this.thread = thread;
	        }
	
	        @Override
	        public void run() {
	            try {
	                // 该JoinThread实例 会等待 构造方法中传入的thread执行完毕后,继续执行
	                thread.join();
	                System.out.println(thread.getName() + " terminated.");
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}