package com.zc.z01demo;

public class T22Volatile
{
    private volatile boolean shouldShutdown;
    public static void main(String[] args)
    {
        for(int i=0;i<10;i++){
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                   
                }
            }).start();
        }
    }
}
