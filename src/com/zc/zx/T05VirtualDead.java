package com.zc.zx;
/**
 * 每个 Java 应用程序都有一个 Runtime 类实例，使应用程序能够与其运行的环境相连接。
 * 可以通过 getRuntime 方法获取当前运行时。 
       应用程序不能创建自己的 Runtime 类实例。
 * */
public class T05VirtualDead {
	public static void main(String[] args) {
		Runtime runtime=Runtime.getRuntime();
		runtime.addShutdownHook(new Thread(){
			@Override
			public void run() {
				System.out.println("虚拟机死亡");
			}
		});
	}
}
