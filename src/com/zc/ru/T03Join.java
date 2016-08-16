package com.zc.ru;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class T03Join extends JFrame{
	JProgressBar progressBar1=new JProgressBar();
	JProgressBar progressBar2=new JProgressBar();
	private Thread A;
	private Thread B;
	static int count=0;
	
	public static void main(String[] args) {
		T03Join join=new T03Join();
	}
	
	public T03Join(){
		
		
		A=new Thread(new Runnable(){

			@Override
			public void run() {
				synchronized (this) {
			
						try {
							A.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(count==20){
							try {
								B.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						progressBar1.setValue(count++);
						if(count==100){
							count=0;
						}
					}
				}
		});
		
		B=new Thread(new Runnable(){
			@Override
			public void run() {
				synchronized(this){
				
						try {
							B.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						progressBar2.setValue(count++);
						if(count==100){
							count=0;
						}					
					}
			
			}
	
			
		});
		A.start();
		B.start();
		
		Container c=this.getContentPane();
		this.setLayout(new BorderLayout());
		c.add(progressBar1,BorderLayout.NORTH);
		c.add(progressBar2,BorderLayout.SOUTH);
		this.setSize(500,100);
		this.setVisible(true);
	}
}
