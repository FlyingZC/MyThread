package com.zc.zx;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class T09ScheduleThreadPool {
	public static void main(String[] args) {
		//一次性定时器		启动3个线程处理该定时器.
		Executors.newScheduledThreadPool(3).schedule(new Runnable(){
			@Override
			public void run() {
				System.out.println("bombing");
			}
			
		}, 10, TimeUnit.SECONDS);//定时器十秒后执行,TimeUnit类里常量定义时间单位
		//周期性定时器		固定频率	启动3个线程执行定时器任务
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable(){
			@Override
			public void run() {
				System.out.println("bang");
			}
		}, 6, 2, TimeUnit.SECONDS);//6秒后开始执行Runnable,每隔两秒执行一次
	}
}
