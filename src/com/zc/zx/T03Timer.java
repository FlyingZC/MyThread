package com.zc.zx;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class T03Timer {
	public static void main(String[] args) {
		//周期性定时器,执行多次
		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				System.out.println(new Date().getTime());
			}},0,1000);//开始时间,间隔毫秒数
		
		//一次性定时器,只执行一次
		new Timer().schedule(new TimerTask(){
			@Override
			public void run() {
				System.out.println("定时器2");
			}
		}, 10);//只执行一次
	}
}
