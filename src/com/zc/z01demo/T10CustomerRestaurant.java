package com.zc.z01demo;

import java.util.concurrent.*;
// wait(),notify()
// 生产者消费者餐厅
public class T10CustomerRestaurant
{
    Meal meal;

    ExecutorService exec = Executors.newCachedThreadPool();

    WaitPerson waitPerson = new WaitPerson(this);

    Chef chef = new Chef(this);

    public T10CustomerRestaurant()
    {
        exec.execute(chef);
        exec.execute(waitPerson);
    }

    public static void main(String[] args)
    {
        new T10CustomerRestaurant();
    }
} 

// 膳食
class Meal
{
    private final int orderNum;

    public Meal(int orderNum)
    {
        this.orderNum = orderNum;
    }

    public String toString()
    {
        return "Meal " + orderNum;
    }
}
// 服务员,等待初始准备好 膳食.消费者
// 当厨师准备好,会通知服务员.
// 之后服务员上菜,然后继续等待
class WaitPerson implements Runnable
{
    private T10CustomerRestaurant restaurant;

    public WaitPerson(T10CustomerRestaurant r)
    {
        restaurant = r;
    }

    public void run()
    {
        try
        {
            while (!Thread.interrupted())
            {
                synchronized (this)
                {
                    while (restaurant.meal == null) // 若没有事物,则等待
                        wait(); // ... for the chef to produce a meal
                }
                System.out.println("Waitperson got " + restaurant.meal);// 取走事物
                synchronized (restaurant.chef)
                {
                    restaurant.meal = null;// 取走事物后,将mean置为null
                    restaurant.chef.notifyAll(); // Ready for another 通知 厨师 生产另一份食物
                }
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("WaitPerson interrupted");
        }
    }
}
// 厨师,生产者 
class Chef implements Runnable
{
    private T10CustomerRestaurant restaurant;

    private int count = 0;

    public Chef(T10CustomerRestaurant r)
    {
        restaurant = r;
    }

    public void run()
    {
        try
        {
            while (!Thread.interrupted())
            {
                synchronized (this)
                {
                    while (restaurant.meal != null)// 若已经 生产了事务,等待事务被取走
                        wait(); // ... for the meal to be taken
                }
                if (++count == 10)// 食物卖完
                {
                    System.out.println("Out of food, closing");
                    restaurant.exec.shutdownNow();
                }
                System.out.println("Order up! ");
                synchronized (restaurant.waitPerson)
                {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("Chef interrupted");
        }
    }
}

