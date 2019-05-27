package com.zc.z01demo;

/*
 * 第三条: 当一个线程访问 某对象 的 synchronized方法 或者 synchronized代码块 时,
 * 其他线程对 该对象 的其他的 synchronized方法 或者 synchronized代码块 的访问将被阻塞。
 * */
public class T12Synchronized4 {
    static MyNewBusiness business = new MyNewBusiness();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                business.synMethod1();
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                business.synMethod2();
            }
        }, "t2").start();
    }
}

class MyNewBusiness {
    public synchronized void synMethod1() {
        for (int i = 0; i < 5; i++) {
            System.out.println("synMethod1->" + i);
        }
        System.out.println("synMethod1()方法执行完毕");
    }

    public synchronized void synMethod2() {
        for (int i = 0; i < 5; i++) {
            System.out.println("synMethod2->" + i);
        }
        System.out.println("synMethod2()方法执行完毕");
    }
}