package com.zc.z01demo;

//生成1-1000的二维数组,启动十个线程,分别得到每一行的最大值,join
public class T01TenThreads
{
    public static void main(String[] args)
    {
        int[][] arr = generateArr();
        EveryThread[] everyThread = new EveryThread[10];// 创建10个线程
        for (int i = 0; i < 10; i++)
        {
            everyThread[i] = new EveryThread(arr[i]);// 初始化每个线程
            everyThread[i].setName("线程" + i);
            everyThread[i].start();
            try
            {
                everyThread[i].join();// 显示thread0.join(),则主线程会等待thread0执行完.然后再是thread1执行...
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
    /**
     * 生成10*100的二维数组
     * */
    public static int[][] generateArr()
    {
        int[][] a = new int[10][100];
        for (int i = 1; i <= 10; i++)
        {
            for (int j = 1; j <= 100; j++)
            {
                a[i - 1][j - 1] = i * j;
            }
        }
        return a;
    }
}

class EveryThread extends Thread
{
    int[] generateArr = new int[100];

    int max;

    public EveryThread(int[] generateArr)
    {
        this.generateArr = generateArr;
    }

    @Override
    public void run()
    {
        doMax();
    }

    /**
     * 比较一唯数组的最大值
     */
    public void doMax()
    {
        for (int i = 0; i < generateArr.length; i++)
        {
            max = Math.max(max, generateArr[i]);
            System.out.println(Thread.currentThread().getName() + "的最大值为" + max);
        }
    }

    public int getMax()
    {
        return max;
    }
}
