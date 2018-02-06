package com.zc.practice;

public class T01Lock
{
    public static void main(String[] args)
    {
        new SubLock().doSomething();
        new Thread(new Runnable(){

            @Override
            public void run()
            {
                new SubLock().doSomething();
            }
            
        }).start();
    }
}

class SuperLock{
    public synchronized void doSomething(){
        System.out.println("父类中的doSomething");
    }
}
class SubLock extends SuperLock{
    @Override
    public synchronized void doSomething()
    {
        System.out.println("子类中的doSomething");
        super.doSomething();
    }
}
