package com.zc;
//单例模式.懒汉式.线程问题
//对于一般方法(不继承).使用同步代码块.可以使用this当锁
//队友静态方法.使用当前类Singleton.class充当锁(是反射类的对象)
public class Test07Singleton {
	public static void main(String[] args){
		Singleton s1=Singleton.getInstance();
		Singleton s2=Singleton.getInstance();
		System.out.println(s1==s2);
	}
}

class Singleton{
	private Singleton(){
		
	}
	
	private static Singleton instance=null;
	public static Singleton getInstance(){
		synchronized(Singleton.class){
			if(instance==null){
				//可能存在线程安全问题
				instance=new Singleton();
			}
		}
		return instance;
	}
}
