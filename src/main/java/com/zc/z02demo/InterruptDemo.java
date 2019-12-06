package com.zc.z02demo;
public class InterruptDemo {
	    public static void main(String[] args) throws InterruptedException {
	        //sleepThread睡眠1000ms
	        final Thread sleepThread = new Thread() {
	            @Override
	            public void run() {
	                try {
	                    // 若该线程 被调用了wait/wait(long) 或被调用Sleep(long),join()/join(long)
	                    // 方法时会抛出InterruptedException并且中断标志位会被清除
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    // sleepThread被sleepThread.interrupt()捕获InterruptedException后清除标志位.
	                    // 而busyThread就不会清除标志位
	                    System.out.println("sleepThread InterruptedException caught");
	                    e.printStackTrace();
	                }
	                super.run();
	            }
	        };
	        //busyThread一直执行死循环
	        Thread busyThread = new Thread() {
	            @Override
	            public void run() {
	                while (true) ;
	            }
	        };
	        sleepThread.start();
	        busyThread.start();
	        sleepThread.interrupt();
	        busyThread.interrupt();
	        // while (sleepThread.isInterrupted()) 表示在Main中会持续监测sleepThread，一旦sleepThread的中断标志位清零，
	        // 即sleepThread.isInterrupted()返回为false时才会继续Main线程才会继续往下执行
	        while (sleepThread.isInterrupted()) ;
	        System.out.println("sleepThread isInterrupted: " + sleepThread.isInterrupted());
	        System.out.println("busyThread isInterrupted: " + busyThread.isInterrupted());
	    }
	}