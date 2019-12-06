package com.zc.exercise;
//Z01demo.T01TenThreads 
//生成1-1000的二维数组,启动十个线程,分别得到每一行的最大值,join
public class T08TenThreadGenArray
{
    public static void main(String[] args) throws InterruptedException
    {
        int[][] arr = new int[10][100];
        genArray(arr);
        for (int i = 0; i < 10; i++)
        {
            Thread t = new Thread(new NumberRunnable(arr[i]));
            t.setName("线程" + i);
            t.start();
            t.join();
        }
    }
    
    public static void genArray(int[][] arr)
    {
        
        for (int i = 0; i < arr.length; i++) 
        {
            for (int j = 0; j < arr[i].length; j++)
            {
                arr[i][j] = (Double.valueOf(Math.random() * i * arr[i].length)).intValue(); 
            }
        }
    }
}

class NumberRunnable implements Runnable
{
    int[] arr;
    public NumberRunnable(int[] arr)
    {
        this.arr = arr;
    }
    
    @Override
    public void run()
    {
        doMax();
    }
    
    public void doMax()
    {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++)
        {
            max = Math.max(arr[i], max);
        }
        System.out.println(Thread.currentThread().getName() + "最大值" + max);
    }
}