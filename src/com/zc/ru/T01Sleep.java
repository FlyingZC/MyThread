package com.zc.ru;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;

/**
 * @author zc
 *
 */
public class T01Sleep extends JFrame{
	private Thread t;
	private static Color[] colors=new Color[]{
		Color.black,Color.gray,Color.blue,Color.red,Color.yellow};
	private static Random random=new Random();
	public static Color getColor(){
		return colors[random.nextInt(colors.length)];
	}
	
	//构造方法
	public T01Sleep(){
		t=new Thread(new Runnable(){
			int x=30;
			int y=50;
			@Override
			public void run() {
				while(true){
					try {
						t.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//
					Graphics g=getGraphics();
					g.setColor(getColor());
					g.drawLine(x, y, 100,y++);
					if(y<=80){
						y=50;
					}
				}
				
			}
			
		});
		t.start();
	}
	
	public static void main(String[] args) {
		init(new T01Sleep(),300,300);
		
	}
	
	/**
	 * JFrame对象(传本类对象.JFrame子类)
	 * */
	private static void init(JFrame jf, int width, int height) {
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(width,height);
		jf.setVisible(true);
		
	}
	
}
