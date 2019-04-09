package com.zc.concurrent;

import java.util.Random;

// ThreadLocal实现(同一个)线程范围内的共享变量.即同一个线程内实现数据共享,其他线程无法访问该数据
// 若共享多个变量,可以将多个变量打包成一个实体,放入一个ThreadLocal对象中
public class T06ThreadLocalReference
{
    static ThreadLocal<Integer> localVar1 = new ThreadLocal<Integer>();

    static ThreadLocal<MyThreadScopeData> myThreadScopeData = new ThreadLocal<MyThreadScopeData>();

    public static void main(String[] args)
    {
        for (int i = 0; i < 10; i++)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + "放入: " + data);
                    // 1.存,与当前线程相关
                    localVar1.set(data);

                    MyThreadScopeData datas = MyThreadScopeData.getInstance();
                    datas.setName("name" + data);
                    datas.setAge(data);
                    myThreadScopeData.set(datas);// 将数据存入共享变量
                    System.out.println(Thread.currentThread().getName() + "放入datas: " + datas);
                    System.out.println(Thread.currentThread().getName() + "放入datas.name: " + datas.getName());
                    System.out.println(Thread.currentThread().getName() + "放入datas.age: " + datas.getAge());
                    System.out.println();
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A
    {
        public void get()
        {
            //2.取,与当前线程相关的共享数据
            int data = localVar1.get();
            System.out.println("A从" + Thread.currentThread().getName() + "取出data: " + data);

            MyThreadScopeData datas = myThreadScopeData.get();
            System.out.println("A从" + Thread.currentThread().getName() + "取出datas: " + datas);
            System.out.println("A从" + Thread.currentThread().getName() + "取出datas.name: " + datas.getName());
            System.out.println("A从" + Thread.currentThread().getName() + "取出datas.age: " + datas.getName());
            System.out.println();
        }
    }

    static class B
    {
        public void get()
        {
            int data = localVar1.get();//取,与当前线程相关的共享数据
            System.out.println("B从" + Thread.currentThread().getName() + "取出" + data);
            
            MyThreadScopeData datas = myThreadScopeData.get();
            System.out.println("B从" + Thread.currentThread().getName() + "取出datas: " + datas);
            System.out.println("B从" + Thread.currentThread().getName() + "取出datas.name: " + datas.getName());
            System.out.println("B从" + Thread.currentThread().getName() + "取出datas.age: " + datas.getName());
            System.out.println();
        }
    }

}

/**
 * 该类只保证每个线程里是单例的.不同线程间不同 
 */
class MyThreadScopeData
{
    private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();

    private String name;
    
    private int age;

    // 单例模式构造方法私有化
    private MyThreadScopeData()
    {

    }
    // ThreadLocal能保证单线程内 单例,无需加锁
    public static MyThreadScopeData getInstance()
    {
        MyThreadScopeData instance = map.get();
        if (instance == null)
        { 
            // 创建该类的实例,并放入ThreadLocal对象中
            instance = new MyThreadScopeData();
            map.set(instance);
        }
        return instance;
    }



    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}
