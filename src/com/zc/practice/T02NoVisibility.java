package com.zc.practice;

public class T02NoVisibility
{
    private static boolean ready;
    private static int number;
    public static void main(String[] args)
    {
        new ReaderThread().start();
        number=42;
        ready=true;
    }
    
    private static class ReaderThread extends Thread{
        @Override
        public void run()
        {
            while(!ready){
                Thread.yield();
            }
            System.out.println(number);
        }
    }
}
class NoVisibility{
    
}
