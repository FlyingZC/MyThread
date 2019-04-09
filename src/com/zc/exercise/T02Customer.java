package com.zc.exercise;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*使用Condition
 * Condition是在java 1.5中才出现的，它用来替代传统的Object的wait()、notify()实现线程间的协作，
 * 相比使用Object的wait()、notify()，使用Condition1的await()、signal()这种方式实现线程间协作更加安全和高效。
 * 因此通常来说比较推荐使用Condition，在阻塞队列那一篇博文中就讲述到了，阻塞队列实际上是使用了Condition来模拟线程间协作。

   Condition是个接口，基本的方法就是await()和signal()方法；
   Condition依赖于Lock接口，生成一个Condition的基本代码是lock.newCondition() 
       调用Condition的await()和signal()方法，都必须在lock保护之内，
       就是说必须在lock.lock()和lock.unlock之间才可以使用
　　Conditon中的await()对应Object的wait()；
　　Condition中的signal()对应Object的notify()；
　　Condition中的signalAll()对应Object的notifyAll()。
 * */
public class T02Customer {
    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
     
    public static void main(String[] args)  {
    	T02Customer test = new T02Customer();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();
          
        producer.start();
        consumer.start();
    }
      
    class Consumer extends Thread{
          
        @Override
        public void run() {
            consume();
        }
          
        private void consume() {
            while(true){
                lock.lock();
                try {
                    while(queue.size() == 0){
                        try {
                            System.out.println("队列空，等待数据");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();                //每次移走队首元素
                    notFull.signal();
                    System.out.println("从队列取走一个元素，队列剩余"+queue.size()+"个元素");
                } finally{
                    lock.unlock();
                }
            }
        }
    }
      
    class Producer extends Thread{
          
        @Override
        public void run() {
            produce();
        }
          
        private void produce() {
            while(true){
                lock.lock();
                try {
                    while(queue.size() == queueSize){
                        try {
                            System.out.println("队列满，等待有空余空间");
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);        //每次插入一个元素
                    notEmpty.signal();
                    System.out.println("向队列取中插入一个元素，队列剩余空间："+(queueSize-queue.size()));
                } finally{
                    lock.unlock();
                }
            }
        }
    }
}