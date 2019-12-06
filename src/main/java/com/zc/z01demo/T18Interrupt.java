package com.zc.z01demo;

/* debug看运行过程
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
            Thread t1 = new MyInThread("t1"); // 1.创建t1线程
            System.out.println(t1.getName() + "->" + t1.getState() + "新建");

            // 2.start时 t1进入 runnable态,并未 running
            t1.start();
            System.out.println(t1.getName() + "->" + t1.getState() + "开始");

            // 3.main让出 cpu资源,进入睡眠态(阻塞态),被 t1抢占执行(t1进入运行态)
            Thread.sleep(300);

            // 6.main线程中断 t1线程的睡眠
            t1.interrupt();
            System.out.println(t1.getName() + "->" + t1.getState() + "被interrupt");

            Thread.sleep(300);
            System.out.println(t1.getName() + "->" + t1.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyInThread extends Thread {
    public MyInThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while (!isInterrupted())// 未被中断,则一直循环
            {
                // 4.未被中断,循环
                // 5.main线程 sleep300ms,所以当循环到大约第三次时,该线程的 sleep会让出时间片,导致 main线程进入 running态.每次循环 sleep 100ms
                Thread.sleep(100);
                i++;
                System.out.println(
                        Thread.currentThread().getName() + "->" + Thread.currentThread().getState() + "->" + i);//(3)(4)
            }
        } catch (InterruptedException e) {
            // 7.捕获被interrupt的异常
            System.out.println(
                    Thread.currentThread().getName() + "->" + Thread.currentThread().getState() + "->catch exception");
        }
    }
}
