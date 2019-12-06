package com.zc.z01demo;

import org.junit.Test;

/*
 * 同一个类SyncClass的两个对象x,y
 * (1)x.syncMethodA()与x.syncMethodB()有同步锁.不能同时访问
 * 
 * (2)x.syncMethodA()与y.syncMethodA()可以同时被访问.
 * 		因为访问的不是同一个对象的同步锁,
 * 	  	x.syncMethodA()访问的是x的同步锁,而y.syncMethodA()访问的是y的同步锁
 * 
 * (3)x.syncStatA()与y.syncStatB().不能同时访问.
 * 		因为syncStatA()和syncStatB()都是static类型,
		x.syncStatA()相当于 类名.syncStatA(),
		y.syncStatB()相当于 类名.syncStatB(),
		因此它们共用一个同步锁(SyncClass.class),不能被同时访问
		
   (4)x.syncMethodA()和x.syncStatA()可以被同时访问。
   		因为syncMethodA()是实例方法,x.syncMethodA()使用的是对象x的锁,
		而syncStatA()是静态方法,x.syncStatA()相当于SyncClass.syncStatA(),使用的锁为SyncClass.class
 * */
public class T12SynchronizedMonitor {
    static SyncClass x = new SyncClass();
    static SyncClass y = new SyncClass();

    //(1)x.syncMethodA()与x.syncMethodB()有同步锁.不能同时访问
    @Test
    public void test1() {
        //(01) x.syncMethodA()与x.syncMethodB()
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                x.syncMethodA();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                x.syncMethodB();
            }
        });

        t1.start();
        t2.start();
    }

    @Test
    public void test2() {
        //(01) x.syncMethodA()与y.syncMethodA()可以同时访问,使用的不是同一把锁
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                x.syncMethodA();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                y.syncMethodA();
            }
        });

        t1.start();
        t2.start();
    }

    @Test
    public void test3() {
        //(01) x.syncStatA()与y.syncStatB()不可以同时访问.使用的都是SyncClass.class这把锁
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                x.syncStatA();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                y.syncStatB();
            }
        });

        t1.start();
        t2.start();
    }

    @Test
    public void test4() {
        //(01) x.syncMethodA()与 x.syncStatA()
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                x.syncMethodA();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                x.syncStatA();
            }
        });

        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                x.syncMethodA();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                x.syncMethodB();
            }
        });

        t1.start();
        t2.start();
    }
}

class SyncClass {
    // 同步实例方法 A
    public synchronized void syncMethodA() {
        System.out.println("syncMethodA()开始执行");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println(Thread.currentThread().getName() + "->syncMethodA->" + i);
        }
        System.out.println("syncMethodA()执行完毕");
    }

    // 同步实例方法 B
    public synchronized void syncMethodB() {
        System.out.println("syncMethodB()开始执行");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println(Thread.currentThread().getName() + "->syncMethodB->" + i);
        }
        System.out.println("syncMethodB()执行完毕");
    }

    // 静态同步方法 A
    public static synchronized void syncStatA() {
        System.out.println("syncStatA()开始执行");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println(Thread.currentThread().getName() + "->syncStatA->" + i);
        }
        System.out.println("syncMethodA()执行完毕");
    }

    // 静态同步方法 B
    public static synchronized void syncStatB() {
        System.out.println("syncStatB()开始执行");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println(Thread.currentThread().getName() + "->syncStatB->" + i);
        }
        System.out.println("syncMethodA()执行完毕");
    }
}
