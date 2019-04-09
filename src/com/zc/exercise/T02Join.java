package com.zc.exercise;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class T02Join extends JFrame{
	JProgressBar progressBar1=new JProgressBar();
	JProgressBar progressBar2=new JProgressBar();
	private Thread A;
	private Thread B;
	
	public static void main(String[] args) {
		T02Join join=new T02Join();
	}
	
	public T02Join(){
		int count=0;
		
		
		A=new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					for(int i=0;i<100;i++){
						
							try {
								A.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(i==20){
								try {
									B.join();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						
						progressBar1.setValue(i);
					}
				}
				
			}
			
		});
		
		B=new Thread(new Runnable(){
			@Override
			public void run() {
				for(int i=0;i<=100;i++){
					try {
						B.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					progressBar2.setValue(i);
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
