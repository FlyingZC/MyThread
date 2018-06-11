package com.zc.concurrent;

import java.util.Timer;
import java.util.TimerTask;

public class T01Timer
{
    public static void main(String[] args)
    {
        Timer timer = new Timer();
        //timer.schedule(TimerTask子类对象,开始时间ms,间隔)
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println("hehe");
            }
        }, 1000, 2000);
    }
}
