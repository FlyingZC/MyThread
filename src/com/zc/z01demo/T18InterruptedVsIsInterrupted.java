package com.zc.z01demo;

/**
 * @author flyingzc
 * interrupted()是静态方法,会清除中断标志
 * isInterrupted()是实例方法,不会清除中断标志
 */
public class T18InterruptedVsIsInterrupted
{
    public static void main(String[] args)
    {
        Thread.currentThread().interrupt();
        System.out.println("===== interrupted() ====");
        System.out.println("是否终止1: " + Thread.interrupted());// true 
        System.out.println("是否终止2: " + Thread.interrupted());// false
        System.out.println("是否存活: " + Thread.currentThread().isAlive());
        
        System.out.println("===== isInterrupted() ====");
        Thread.currentThread().interrupt();
        System.out.println("是否终止1: " + Thread.currentThread().isInterrupted());// true
        System.out.println("是否终止2: " + Thread.currentThread().isInterrupted());// true
        System.out.println("是否存活: " + Thread.currentThread().isAlive());
        System.out.println("----------end-----------");
        System.out.println("是否存活: " + Thread.currentThread().isAlive());
    }
}
