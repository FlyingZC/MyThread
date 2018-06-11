package com.zc.z02demo;
// 可能发生指令重排序
public class T01PossibleReordering2
{
    static int x = 0, y = 0;

    static int a = 0, b = 0;
    
    public static void main(String[] args) throws InterruptedException
    {
        int i = 1;
        while(true)
        {
            Thread one = new Thread(new Runnable()
            {
                public void run()
                {
                    a = 1;
                    x = b;
                }
            });
            
            Thread other = new Thread(new Runnable()
            {
                public void run()
                {
                    b = 1;
                    y = a;
                }
            });
            one.start();
            other.start();
            one.join();
            other.join();
            System.out.println("第" + i++ + "次,(" + x + "," + y + ")");
            if (x == 0 || y == 0) {
                throw new RuntimeException();
            }
        }
    }
}
