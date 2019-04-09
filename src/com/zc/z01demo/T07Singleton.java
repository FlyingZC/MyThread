package com.zc.z01demo;

//单例模式.参见MyPattern里的单例模式.懒汉式.线程问题
//对于一般方法(不继承).使用同步代码块.可以使用this当锁
//对于静态方法.使用当前类Singleton.class充当锁(是反射类的对象)
public class T07Singleton
{
    public static void main(String[] args)
    {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1 == s2);
    }
}

class Singleton
{
    private Singleton()
    {

    }

    private static Singleton instance = null;

    public static Singleton getInstance()
    {
        synchronized (Singleton.class)
        {  // 如果已经创建过该对象的实例,则直接返回该实例即可,保证了单例性
            if (instance == null)
            {
                // 可能存在线程安全问题
                // 在这一行打断点,即可发现并发问题.多个线程可能同时判断instance==null,从而创建了不同的Single对象
                instance = new Singleton();
            }
        }
        return instance;
    }
}
